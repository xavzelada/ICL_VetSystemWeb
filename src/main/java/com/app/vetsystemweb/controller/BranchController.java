/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.vetsystemweb.controller;

import com.app.vetsystemweb.config.Util;
import com.app.vetsystemweb.entities.Company;
import com.app.vetsystemweb.entities.Branch;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestClientException;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author DELL
 */
@Controller
public class BranchController {

    final static Logger LOG = Logger.getLogger(BranchController.class.getName());
    //final static String baseuri = "http://localhost:9090/VetSystem";

    @Autowired
    VetSystemRestClient restClient;

    @Autowired
    Util utilService;

    Branch myBranch;

    public void init() {
        LOG.info("Loading BranchController");
    }

    @RequestMapping("branches/index.htm")
    public ModelAndView MenuHandler() {
        return BranchListHandler();
    }

    private ModelAndView getBranchList(Integer companyId, String source) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("branch/listBranches");
        try {
            LOG.info("Executing method getBranchList - BranchController");
            LOG.info("companyId -> " + companyId);
            Company myCompany = new Company();

            if (companyId != null) {
                myCompany.setCompanyid(utilService.castInteger(companyId));
                myCompany = restClient.getCompany(myCompany);
            }

            if (myCompany != null) {
                List<Branch> myResponseList = restClient.ListBranches(myCompany);
                if (myResponseList != null) {
                    if (myResponseList.size() > 1) {
                        Comparator<Branch> compareById = (Branch o1, Branch o2) -> o1.getBranchid().compareTo(o2.getBranchid());
                        Collections.sort(myResponseList, compareById);
                    }
                }

                mav.addObject("myCompany", myCompany);
                mav.addObject("listResponse", myResponseList);
                mav.addObject("source", source);
            } else {
                mav.addObject("errorDescription", "The company selected doesn't exist");
            }
            return mav;

        } catch (RestClientException ex) {
            mav.addObject("errorDescription", "We know that an unexpected error has occurred, please try again later");
            LOG.warning(ex.getMessage());
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
        myResponseList = restClient.ListCompanies();

        if (myResponseList.size() > 1) {
            Comparator<Company> compareByid = (Company o1, Company o2) -> o1.getCompanyid().compareTo(o2.getCompanyid());
            Collections.sort(myResponseList, compareByid);
        }

        for (Company c : myResponseList) {
            if (c.getIsactive().equalsIgnoreCase("A")) {
                companyList.put(c.getCompanyid(), c.getName());
            }
        }
        return companyList;
    }

    @RequestMapping(value = "branches/newBranch.htm", method = RequestMethod.GET)
    public ModelAndView NewBranchHandler() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("branch/newBranch");

        Map<Integer, String> companyList = getCompanyList();

        mav.addObject("CompanyList", companyList);

        Branch temp = new Branch();
        temp.setBranchid(-1);
        mav.addObject("Branch", temp);

        return mav;
    }

    @RequestMapping(value = "branches/editBranch", method = RequestMethod.GET)
    public ModelAndView NewBranchHandler(@RequestParam String branchid) {
        //LOG.info("ejecuta metodo NewBranchHandler - BranchController, branch id " + branchid);
        ModelAndView mav = new ModelAndView();
        mav.setViewName("branch/newBranch");
        if (!branchid.isEmpty()) {
            Branch Response = new Branch();
            Response.setBranchid(utilService.castInteger(branchid));
            Response = restClient.getBranch(Response);
            if (Response != null) {
                mav.addObject("Branch", Response);
            } else {
                mav.addObject("errorDescription", "We know that an unexpected error has occurred, please try again later");
            }

            Map<Integer, String> myList = getCompanyList();
            mav.addObject("CompanyList", myList);

        } else {
            mav.addObject("Branch", new Branch());
        }

        return mav;
    }

    @RequestMapping(value = "branches/newBranch.htm", method = RequestMethod.POST)
    public ModelAndView NewBranchHandler(HttpSession session, Branch branch) {
        ModelAndView mav = new ModelAndView();
        LOG.info("Executing methor NewBranchHandler - BranchController");
        LOG.info("branch -> " + ReflectionToStringBuilder.toString(branch));

        try {
            if (myBranch == null) {
                myBranch = new Branch();
            }

            if (branch.getBranchid() == -1) {
                branch.setBranchid(0);
                branch.setIsactive("A");

                if (restClient.createBranch(branch)) {
                    return new ModelAndView("redirect:/branches/listBranches.htm");
                }

                mav.addObject("errorDescription", "We know that an unexpected error has occurred, please try again later");
                mav.setViewName("branches/newBranch.htm");
            } else {
                if (restClient.UpdateBranch(branch)) {
                    return new ModelAndView("redirect:/branches/listBranches.htm");
                }

                mav.addObject("errorDescription", "We know that an unexpected error has occurred, please try again later");
                mav.setViewName("branches/newBranch.htm");
            }
            return mav;
        } catch (RestClientException ex) {
            mav.addObject("errorDescription", "We know that an unexpected error has occurred, please try again later");
            LOG.warning(ex.getMessage());
            mav.setViewName("branches/newBranch.htm");
            return mav;
        }
    }

    @RequestMapping(value = "branches/ChangeStatusBranch", method = RequestMethod.GET)
    public ModelAndView ChangeStatusBranchHandler(@RequestParam String branchid,
            @RequestParam String source
    ) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("branch/index.htm");
        LOG.info("branchid -> " + branchid);

        if (branchid != null) {

            Branch tempBranch = new Branch();
            tempBranch.setBranchid(utilService.castInteger(branchid));
            myBranch = restClient.getBranch(tempBranch);

            if (myBranch.getIsactive().equalsIgnoreCase("A")) {
                myBranch.setIsactive("I");
            } else {
                myBranch.setIsactive("A");
            }

            if (restClient.UpdateBranch(myBranch)) {
                return new ModelAndView("redirect:/branches/" + source);
            }
        }

        return mav;
    }
}
