package com.app.vetsystemweb.controller;

import com.app.vetsystemweb.entities.Company;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CompanyController {

    final static Logger logger = Logger.getLogger(CompanyController.class.getName());
    final static String baseuri = "http://localhost:9090/VetSystem";

    Company myCompany;

    public void init() {
        logger.info("Loading CompanyController");
    }

    @RequestMapping("companies/index.htm")
    public ModelAndView MenuHandler() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("company/listCompanies");
        return mav;
    }

    @RequestMapping(value = "companies/listCompanies.htm")
    public ModelAndView CompanyListHandler() {
        ModelAndView mav = new ModelAndView();
        try {
            //System.out.println("ejecuta metodo CompanyListHandler - CompanyController");
            String uri = baseuri + "/company/";
            RestTemplate restTemplate = new RestTemplate();
            //System.out.println("Ejecutando servicio");
            ResponseEntity<List<Company>> response = restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<List<Company>>() {
            });
            //System.out.println("Extrayendo codigo de respuesta");
            HttpStatus status = response.getStatusCode();
            //System.out.println(status);
            if (status == HttpStatus.OK) {
                List<Company> listResponse = response.getBody();
                //System.out.println(listResponse);
                for (Company c : listResponse) {

                    if (c.getLogo() != null) {
                        c.setBase64Image(Base64.getEncoder().encodeToString(c.getLogo()));
                    }
                    //System.out.println("name -> " + c.getName());
                    //System.out.println("base64Image -> " + c.getBase64Image());
                    //System.out.println("logo -> " + c.getLogo());
                }

                if (listResponse.size() > 1) {
                    Comparator<Company> compareByLastUpdate = (Company o1, Company o2) -> o1.getCompanyid().compareTo(o2.getCompanyid());
                    Collections.sort(listResponse, compareByLastUpdate);
                }

                mav.addObject("listResponse", listResponse);
            } else {
                mav.addObject("errorDescription", "We know that an unexpected error has occurred, please try again later");
            }

            mav.setViewName("company/listCompanies");
            return mav;

        } catch (RestClientException ex) {
            mav.addObject("errorDescription", "We know that an unexpected error has occurred, please try again later");
            logger.warning(ex.getMessage());
            mav.setViewName("companies/newCompany.htm");
            return mav;
        }
    }

    @RequestMapping(value = "companies/newCompany.htm", method = RequestMethod.GET)
    public ModelAndView NewCompanyHandler() {
        ModelAndView mav = new ModelAndView();
        Company temp = new Company();
        temp.setCompanyid(-1);
        mav.addObject("Company", temp);
        mav.setViewName("company/newCompany");
        return mav;
    }

    @RequestMapping(value = "companies/editCompany", method = RequestMethod.GET)
    public ModelAndView NewCompanyHandler(@RequestParam String companyid) {
        //System.out.println("ejecuta metodo NewCompanyHandler - CompanyController, company id " + companyid);
        ModelAndView mav = new ModelAndView();
        if (!companyid.isEmpty()) {
            //http://localhost:9090/VetSystem/company/byId?id=1
            String uri = baseuri + "/company/byId?id={id}";
            Map<String, String> params = new HashMap<String, String>();
            params.put("id", companyid);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Company> response = restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<Company>() {
            }, params);

            HttpStatus status = response.getStatusCode();
            System.out.println(status);
            if (status == HttpStatus.OK) {
                Company Response = response.getBody();
                if (Response.getLogo() != null) {
                    System.out.println("company logo -> " + Response.getLogo());
                    Response.setBase64Image(Base64.getEncoder().encodeToString(Response.getLogo()));
                }
                myCompany = Response;
                mav.addObject("Company", Response);
            } else {
                mav.addObject("errorDescription", "We know that an unexpected error has occurred, please try again later");
            }

        } else {
            mav.addObject("Company", new Company());
        }
        mav.setViewName("company/newCompany");
        return mav;
    }

    @RequestMapping(value = "companies/newCompany.htm", method = RequestMethod.POST)
    public ModelAndView NewCompanyHandler(@RequestParam CommonsMultipartFile file,
            HttpSession session,
            Company company) {
        ModelAndView mav = new ModelAndView();
        System.out.println("ejecuta metodo NewCompanyHandler - CompanyController");

        try {
            //System.out.println("ejecuta metodo NewCompanyHandler - CompanyController");
            //System.out.println("company id -> " + company.getCompanyid());
            //System.out.println("company name -> " + company.getName());
            //System.out.println("company addr -> " + company.getAddress());
            //System.out.println("company logo -> " + company.getLogo());
            //System.out.println("company logo -> " + Arrays.toString(company.getLogo()));
            if (!file.isEmpty()) {
                String filename = file.getOriginalFilename();
                //System.out.println("file name -> " + filename);
                byte[] bytes = file.getBytes();
                company.setLogo(bytes);
            }

            //System.out.println("company logo 2 -> " + Arrays.toString(company.getLogo()));
            if (company.getCompanyid() == -1) {
                String uri = baseuri + "/company";

                //System.out.println("company logo 2 -> " + Arrays.toString(company.getLogo()));
                RestTemplate restTemplate = new RestTemplate();
                ResponseEntity<String> response = restTemplate.postForEntity(uri, company, String.class);
                HttpStatus status = response.getStatusCode();
                System.out.println(status);
                String restCall = response.getBody();
                System.out.println(restCall);
                if (status == HttpStatus.CREATED) {
                    return new ModelAndView("redirect:/companies/listCompanies.htm");
                }

                mav.addObject("errorDescription", "We know that an unexpected error has occurred, please try again later");
                mav.setViewName("companies/newCompany.htm");
            } else {
                logger.log(Level.FINE, "Actualizando empresa -> {0}", company.getCompanyid());
                String uri = baseuri + "/company";

                if (!file.isEmpty()) {
                    myCompany.setLogo(company.getLogo());
                }

                myCompany.setName(company.getName());
                myCompany.setAddress(company.getAddress());

                RestTemplate restTemplate = new RestTemplate();

                HttpEntity<Company> entity = new HttpEntity<Company>(myCompany);
                ResponseEntity<Company> response = restTemplate.exchange(uri, HttpMethod.PUT, entity, Company.class);

                //ResponseEntity<Company> response = restTemplate.exchange(uri, HttpMethod.PUT, company, new ParameterizedTypeReference<Company>() {});
                //ResponseEntity<String> response = restTemplate.put(uri, company, String.class);
                HttpStatus status = response.getStatusCode();
                System.out.println(status);
                Company restCall = response.getBody();
                //System.out.println(restCall);
                if (status == HttpStatus.OK) {
                    return new ModelAndView("redirect:/companies/listCompanies.htm");
                }

                mav.addObject("errorDescription", "We know that an unexpected error has occurred, please try again later");
                mav.setViewName("companies/newCompany.htm");
            }
            return mav;
        } catch (RestClientException ex) {
            mav.addObject("errorDescription", "We know that an unexpected error has occurred, please try again later");
            logger.warning(ex.getMessage());
            mav.setViewName("companies/newCompany.htm");
            return mav;
        }
    }

    @RequestMapping(value = "companies/ChangeStatusCompany", method = RequestMethod.GET)
    public ModelAndView ChangeStatusCompanyHandler(@RequestParam String companyid) {
        //System.out.println("ejecuta metodo NewCompanyHandler - CompanyController, company id " + companyid);
        ModelAndView mav = new ModelAndView();
        if (!companyid.isEmpty()) {
            //http://localhost:9090/VetSystem/company/byId?id=1
            String uri = baseuri + "/company/byId?id={id}";
            Map<String, String> params = new HashMap<String, String>();
            params.put("id", companyid);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Company> response = restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<Company>() {
            }, params);

            HttpStatus status = response.getStatusCode();
            System.out.println(status);
            if (status == HttpStatus.OK) {
                myCompany = response.getBody();

                if (myCompany.getIsactive().equalsIgnoreCase("A")) {
                    myCompany.setIsactive("I");
                } else {
                    myCompany.setIsactive("A");
                }

                uri = baseuri + "/company";
                HttpEntity<Company> entity = new HttpEntity<Company>(myCompany);
                response = restTemplate.exchange(uri, HttpMethod.PUT, entity, Company.class);

                status = response.getStatusCode();
                //System.out.println(status);
                Company restCall = response.getBody();

                if (status == HttpStatus.OK) {
                    return new ModelAndView("redirect:/companies/listCompanies.htm");
                }

            } else {
                mav.addObject("errorDescription", "We know that an unexpected error has occurred, please try again later");
            }

        } else {
            mav.addObject("Company", new Company());
        }
        mav.setViewName("company/newCompany");
        return mav;
    }

}
