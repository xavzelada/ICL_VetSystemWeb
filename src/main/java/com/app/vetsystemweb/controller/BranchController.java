/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.vetsystemweb.controller;

import com.app.vetsystemweb.entities.Company;
import com.app.vetsystemweb.entities.Branch;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author DELL
 */
@Controller
public class BranchController {

    final static Logger logger = Logger.getLogger(BranchController.class.getName());
    final static String baseuri = "http://localhost:9090/VetSystem";

    @Autowired
    VetSystemRestClient restClient;
    Branch myBranch;

    public void init() {
        logger.info("Loading BranchController");
    }

    @RequestMapping("branches/index.htm")
    public ModelAndView MenuHandler() {
        return BranchListHandler();
    }

    private ModelAndView getBranchList(Integer companyId, String source) {
        ModelAndView mav = new ModelAndView();
        try {
            System.out.println("ejecuta metodo getBranchList - BranchController");
            System.out.println("companyId -> " + companyId);

            String uri = baseuri;
            Map<String, String> params = new HashMap<String, String>();
            RestTemplate restTemplate = new RestTemplate();
            Company myCompany = new Company();
            ResponseEntity<List<Branch>> response;
            if (companyId != null) {
                myCompany.setCompanyid(companyId);
                myCompany =  (Company) restClient.getCompany(myCompany).getBody();
            }

            List<Branch> myResponseList = null;

            response = restClient.ListBranches(myCompany);

            HttpStatus status = response.getStatusCode();
            System.out.println("retrieving branches(Status) " + status);
            if (status == HttpStatus.OK) {
                //List<Branch> 
                myResponseList = response.getBody();

                if (myResponseList.size() > 1) {
                    Comparator<Branch> compareByLastUpdate = (Branch o1, Branch o2) -> o1.getBranchid().compareTo(o2.getBranchid());
                    Collections.sort(myResponseList, compareByLastUpdate);
                }

                mav.addObject("myCompany", myCompany);
                mav.addObject("listResponse", myResponseList);
            } else {
                mav.addObject("errorDescription", "We know that an unexpected error has occurred, please try again later");
            }
            mav.addObject("source", source);
            mav.setViewName("branch/listBranches");
            return mav;

        } catch (RestClientException ex) {
            mav.addObject("errorDescription", "We know that an unexpected error has occurred, please try again later");
            logger.warning(ex.getMessage());
            mav.setViewName("branches/newBranch.htm");
            return mav;
        }
    }

    @RequestMapping(value = "branches/listBranches.htm")
    public ModelAndView BranchListHandler() {
        return getBranchList(null, "listBranches.htm");
    }

    @RequestMapping(value = "branches/adminCompany.htm", method = RequestMethod.GET)
    public ModelAndView BranchListHandler(Integer companyid) {
        return getBranchList(companyid, "adminCompany.htm?companyid=" + companyid);
    }

    private Map<Integer, String> getCompanyList() {
        ResponseEntity<List<Company>> response;
        List<Company> myResponseList = null;
        Map<Integer, String> companyList = new LinkedHashMap<>();
        response = restClient.ListCompanies();

        HttpStatus status = response.getStatusCode();
        System.out.println("retrieving companies(Status) " + status);
        if (status == HttpStatus.OK) {
            myResponseList = response.getBody();

            if (myResponseList.size() > 1) {
                Comparator<Company> compareByid = (Company o1, Company o2) -> o1.getCompanyid().compareTo(o2.getCompanyid());
                Collections.sort(myResponseList, compareByid);
            }

            
            for (Company c : myResponseList) {
                if (c.getIsactive().equalsIgnoreCase("A")) {
                    companyList.put(c.getCompanyid(), c.getName());
                }
            }
        }
        return companyList;
    }

    @RequestMapping(value = "branches/newBranch.htm", method = RequestMethod.GET)
    public ModelAndView NewBranchHandler() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("branch/newBranch");
        ResponseEntity<List<Company>> response;
        List<Company> myResponseList = null;

        response = restClient.ListCompanies();

        HttpStatus status = response.getStatusCode();
        System.out.println("retrieving companies(Status) " + status);
        if (status == HttpStatus.OK) {
            myResponseList = response.getBody();

            if (myResponseList.size() > 1) {
                Comparator<Company> compareByid = (Company o1, Company o2) -> o1.getCompanyid().compareTo(o2.getCompanyid());
                Collections.sort(myResponseList, compareByid);
            }

            Map<Integer, String> companyList = new LinkedHashMap<>();
            for (Company c : myResponseList) {
                if (c.getIsactive().equalsIgnoreCase("A")) {
                    companyList.put(c.getCompanyid(), c.getName());
                }
                mav.addObject("CompanyList", companyList);
            }
        } else {
            mav.addObject("errorDescription", "We know that an unexpected error has occurred, please try again later");
        }

        Branch temp = new Branch();
        temp.setBranchid(-1);
        mav.addObject("Branch", temp);

        return mav;
    }

    @RequestMapping(value = "branches/editBranch", method = RequestMethod.GET)
    public ModelAndView NewBranchHandler(@RequestParam String branchid) {
        //System.out.println("ejecuta metodo NewBranchHandler - BranchController, branch id " + branchid);
        ModelAndView mav = new ModelAndView();
        if (!branchid.isEmpty()) {
            String uri = baseuri + "/branch/byId?id={id}";
            Map<String, String> params = new HashMap<String, String>();
            params.put("id", branchid);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Branch> response = restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<Branch>() {
            }, params);

            HttpStatus status = response.getStatusCode();
            System.out.println(status);
            if (status == HttpStatus.OK) {
                Branch Response = response.getBody();
                myBranch = Response;
                mav.addObject("Branch", Response);
            } else {
                mav.addObject("errorDescription", "We know that an unexpected error has occurred, please try again later");
            }
            
            Map<Integer, String> myList = getCompanyList();
            mav.addObject("CompanyList", myList);

        } else {
            mav.addObject("Branch", new Branch());
        }
        mav.setViewName("branch/newBranch");
        return mav;
    }

    @RequestMapping(value = "branches/newBranch.htm", method = RequestMethod.POST)
    public ModelAndView NewBranchHandler(HttpSession session, Branch branch) {
        ModelAndView mav = new ModelAndView();
        System.out.println("ejecuta metodo NewBranchHandler - BranchController");

        try {
            if (branch.getBranchid() == -1) {
                String uri = baseuri + "/branch?ParentId={ParentId}";
                Company tempCompany = new Company();

                tempCompany.setCompanyid(branch.getCompanyid());
                branch.setCompany(tempCompany);
                RestTemplate restTemplate = new RestTemplate();

                branch.setIsactive("A");

                Map<String, Object> params = new HashMap<>();
                params.put("ParentId", branch.getCompanyid());

                ResponseEntity<String> response = restTemplate.postForEntity(uri, branch, String.class, params);

                HttpStatus status = response.getStatusCode();
                System.out.println(status);
                String restCall = response.getBody();
                System.out.println(restCall);
                if (status == HttpStatus.CREATED) {
                    return new ModelAndView("redirect:/branches/listBranches.htm");
                }

                mav.addObject("errorDescription", "We know that an unexpected error has occurred, please try again later");
                mav.setViewName("branches/newBranch.htm");
            } else {
                logger.log(Level.FINE, "Actualizando empresa -> {0}", branch.getBranchid());
                String uri = baseuri + "/branch";

                myBranch.setName(branch.getName());
                myBranch.setAddress(branch.getAddress());

                RestTemplate restTemplate = new RestTemplate();

                HttpEntity<Branch> entity = new HttpEntity<Branch>(branch);
                ResponseEntity<Branch> response = restTemplate.exchange(uri, HttpMethod.PUT, entity, Branch.class);

                HttpStatus status = response.getStatusCode();
                System.out.println(status);
                Branch restCall = response.getBody();

                if (status == HttpStatus.OK) {
                    return new ModelAndView("redirect:/branches/listBranches.htm");
                }

                mav.addObject("errorDescription", "We know that an unexpected error has occurred, please try again later");
                mav.setViewName("branches/newBranch.htm");
            }
            return mav;
        } catch (RestClientException ex) {
            mav.addObject("errorDescription", "We know that an unexpected error has occurred, please try again later");
            logger.warning(ex.getMessage());
            mav.setViewName("branches/newBranch.htm");
            return mav;
        }
    }

    @RequestMapping(value = "branches/ChangeStatusBranch", method = RequestMethod.GET)
    public ModelAndView ChangeStatusBranchHandler(@RequestParam String branchid, @RequestParam String source) {
        //System.out.println("ejecuta metodo NewBranchHandler - BranchController, branch id " + branchid);
        ModelAndView mav = new ModelAndView();
        if (!branchid.isEmpty()) {

            String uri = baseuri + "/branch/byId?id={id}";
            Map<String, String> params = new HashMap<String, String>();
            params.put("id", branchid);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Branch> response = restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<Branch>() {
            }, params);

            HttpStatus status = response.getStatusCode();
            System.out.println(status);
            if (status == HttpStatus.OK) {
                myBranch = response.getBody();

                if (myBranch.getIsactive().equalsIgnoreCase("A")) {
                    myBranch.setIsactive("I");
                } else {
                    myBranch.setIsactive("A");
                }

                uri = baseuri + "/branch";
                HttpEntity<Branch> entity = new HttpEntity<Branch>(myBranch);
                response = restTemplate.exchange(uri, HttpMethod.PUT, entity, Branch.class);

                status = response.getStatusCode();

                Branch restCall = response.getBody();

                if (status == HttpStatus.OK) {
                    return new ModelAndView("redirect:/branches/"+ source);
                }

            } else {
                mav.addObject("errorDescription", "We know that an unexpected error has occurred, please try again later");
            }

        } else {
            mav.addObject("Branch", new Branch());
        }
        mav.setViewName("branch/index.htm");
        return mav;
    }
}
