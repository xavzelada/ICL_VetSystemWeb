package com.app.vetsystemweb.controller;

import com.app.vetsystemweb.config.Util;
import com.app.vetsystemweb.entities.Company;
import java.util.Base64;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestClientException;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CompanyController {

    final static Logger LOG = Logger.getLogger(CompanyController.class.getName());
    //final static String baseuri = "http://localhost:9090/VetSystem";

    @Autowired
    VetSystemRestClient restClient;

    @Autowired
    Util utilService;

    Company myCompany;

    public void init() {
        LOG.info("Loading CompanyController");
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
            LOG.info("executing method CompanyListHandler - CompanyController");

            List<Company> myResponseList = restClient.ListCompanies();
            if (myResponseList != null) {

                if (myResponseList.size() > 1) {
                    Comparator<Company> compareByLastUpdate = (Company o1, Company o2) -> o1.getCompanyid().compareTo(o2.getCompanyid());
                    Collections.sort(myResponseList, compareByLastUpdate);
                }
                mav.addObject("listResponse", myResponseList);
            } else {
                mav.addObject("errorDescription", "We know that an unexpected error has occurred, please try again later");
            }
            mav.setViewName("company/listCompanies");
            return mav;

        } catch (RestClientException ex) {
            mav.addObject("errorDescription", "We know that an unexpected error has occurred, please try again later");
            LOG.warning(ex.getMessage());
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
        //LOG.info("ejecuta metodo NewCompanyHandler - CompanyController, company id " + companyid);
        ModelAndView mav = new ModelAndView();
        mav.setViewName("company/newCompany");
        if (!companyid.isEmpty()) {

            Company myCompanyTmp = new Company();

            myCompanyTmp.setCompanyid(utilService.castInteger(companyid));

            myCompanyTmp = restClient.getCompany(myCompanyTmp);
            if (myCompanyTmp.getLogo() != null) {
                myCompanyTmp.setBase64Image(Base64.getEncoder().encodeToString(myCompanyTmp.getLogo()));
            }
            mav.addObject("Company", myCompanyTmp);

        } else {
            mav.addObject("Company", new Company());
        }

        return mav;
    }

    @RequestMapping(value = "companies/newCompany.htm", method = RequestMethod.POST)
    public ModelAndView NewCompanyHandler(@RequestParam CommonsMultipartFile file,
            HttpSession session,
            Company company) {
        ModelAndView mav = new ModelAndView();
        LOG.info("ejecuta metodo NewCompanyHandler - CompanyController");

        try {
            if (myCompany == null) {
                myCompany = new Company();
            }

            if (myCompany.getLogo()!= null) {
                company.setLogo(myCompany.getLogo());
            }

            if (!file.isEmpty()) {
                byte[] bytes = file.getBytes();
                company.setLogo(bytes);
            }

            if (company.getCompanyid() == -1) {

                company.setIsactive("A");
                if (restClient.createCompany(company)) {
                    return new ModelAndView("redirect:/companies/listCompanies.htm");
                }

                mav.addObject("errorDescription", "We know that an unexpected error has occurred, please try again later");
                mav.setViewName("companies/newCompany.htm");
            } else {
                if (restClient.UpdateCompany(company)) {
                    return new ModelAndView("redirect:/companies/listCompanies.htm");
                }

                mav.addObject("errorDescription", "We know that an unexpected error has occurred, please try again later");
                mav.setViewName("companies/newCompany.htm");
            }
            return mav;
        } catch (RestClientException ex) {
            mav.addObject("errorDescription", "We know that an unexpected error has occurred, please try again later");
            LOG.warning(ex.getMessage());
            mav.setViewName("companies/newCompany.htm");
            return mav;
        }
    }

    @RequestMapping(value = "companies/ChangeStatusCompany", method = RequestMethod.GET)
    public ModelAndView ChangeStatusCompanyHandler(@RequestParam String companyid) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("company/newCompany");
        LOG.info("companyid -> " + companyid);
        
        if (companyid != null) {

            Company tempComp = new Company();
            tempComp.setCompanyid(utilService.castInteger(companyid));
            myCompany = restClient.getCompany(tempComp);

            if (myCompany.getIsactive().equalsIgnoreCase("A")) {
                myCompany.setIsactive("I");
            } else {
                myCompany.setIsactive("A");
            }

            if (restClient.UpdateCompany(myCompany)) {
                return new ModelAndView("redirect:/companies/listCompanies.htm");
            }

        }

        return mav;
		
		
		
		
    }

}
