package com.app.vetsystemweb.controller;

import com.app.vetsystemweb.config.Util;
import com.app.vetsystemweb.entities.Employee;
import com.app.vetsystemweb.entities.Vet;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestClientException;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class VetController {

    final static Logger LOG = Logger.getLogger(VetController.class.getName());

    @Autowired
    VetSystemRestClient restClient;

    @Autowired
    Util utilService;

    Vet myVet;

    Employee myEmployee;

    @RequestMapping("vets/index.htm")
    public ModelAndView MenuHandler() {
        LOG.info("Executing method MenuHandler - VetController");
        return VetListHandler();
    }

    private ModelAndView getVetList(String source) {
        ModelAndView mav = new ModelAndView();
        try {
            LOG.info("Executing method getVetList - VetController");

            List<Vet> myResponseList = restClient.ListVets();

            if (myResponseList.size() > 1) {
                Comparator<Vet> compareById = (Vet o1, Vet o2) -> o1.getVetid().compareTo(o2.getVetid());
                Collections.sort(myResponseList, compareById);
            }

            mav.addObject("listResponse", myResponseList);
            mav.addObject("source", source);

            mav.setViewName("vet/listVets");
            return mav;

        } catch (RestClientException ex) {
            mav.addObject("errorDescription", "We know that an unexpected error has occurred, please try again later");
            LOG.warning(ex.getMessage());
            mav.setViewName("vets/VetDetail.htm");
            return mav;
        }
    }

    @RequestMapping(value = "vets/listVets.htm")
    public ModelAndView VetListHandler() {
        LOG.info("Executing method VetListHandler - VetController");
        return getVetList("listVets.htm");
    }

    @RequestMapping(value = "vets/VetDetail.htm", method = RequestMethod.GET)
    public ModelAndView NewVetHandler(String employeeid) {
        LOG.info("Executing method NewVetHandler - VetController(GET)");
        ModelAndView mav = new ModelAndView();
        mav.setViewName("vet/VetDetail");

        Vet temp = new Vet();
        temp.setVetid(-1);
        temp.setEmployeeid(utilService.castInteger(employeeid));

        mav.addObject("Vet", temp);

        return mav;
    }

    @RequestMapping(value = "vets/editVet", method = RequestMethod.GET)
    public ModelAndView EditVetHandler(@RequestParam String vetid, String employeeid) {
        LOG.info("Executing method EditVetHandler - VetController");
        ModelAndView mav = new ModelAndView();
        mav.setViewName("vet/VetDetail");

        Employee temp = new Employee();
        Vet myVetTmp = new Vet();
        if (vetid != null || employeeid != null) {

            if (vetid != null) {
                myVetTmp.setVetid(utilService.castInteger(vetid));
            }
            if (employeeid != null) {
                myVetTmp.setEmployeeid(utilService.castInteger(employeeid));
                temp.setEmployeeid(myVetTmp.getEmployeeid());
                temp = restClient.getEmployee(temp);
            }

            myVet = restClient.getVet(myVetTmp);
            if (myVet != null) {
                if (myVet.getEmployeeid() != null) {
                    temp.setEmployeeid(myVet.getEmployeeid());
                    temp = restClient.getEmployee(temp);
                }
            }else{
                myVet = new Vet();
                myVet.setVetid(-1);
            }
            
            LOG.info("vet -> " + ReflectionToStringBuilder.toString(myVet));
            mav.addObject("Vet", myVet);
        } else {

            mav.addObject("Vet", new Vet());
        }
        mav.addObject("Employee", temp);
        return mav;
    }

    @RequestMapping(value = "vets/VetDetail.htm", method = RequestMethod.POST)
    public ModelAndView NewVetHandler(HttpSession session, Vet vet, String _licensetype) {
        LOG.info("Executing method NewVetHandler - VetController(POST)");
        LOG.info("vet -> " + ReflectionToStringBuilder.toString(vet));
        LOG.info("_licensetype -> " + _licensetype);

        ModelAndView mav = new ModelAndView();
        mav.setViewName("vets/VetDetail.htm");

        try {
            if (myVet == null) {
                myVet = new Vet();
            }

            Employee tempParent = new Employee();
            if (vet.getEmployeeid() != null) {
                tempParent.setEmployeeid(vet.getEmployeeid());
            }

            vet.setEmployee(tempParent);
            vet.setLicencetype(_licensetype);
            LOG.info("vet 2 -> " + ReflectionToStringBuilder.toString(vet));
            if (vet.getVetid() == -1) {
                vet.setIsactive("A");
                if (restClient.createVet(vet)) {
                    return new ModelAndView("redirect:/vets/listVets.htm");
                }
                mav.addObject("errorDescription", "We know that an unexpected error has occurred, please try again later");

            } else {
                if (restClient.UpdateVet(vet)) {
                    return new ModelAndView("redirect:/vets/listVets.htm");
                }
                mav.addObject("errorDescription", "We know that an unexpected error has occurred, please try again later");
            }
            return mav;
        } catch (RestClientException ex) {
            mav.addObject("errorDescription", "We know that an unexpected error has occurred, please try again later");
            LOG.warning(ex.getMessage());
            mav.setViewName("vets/VetDetail.htm");
            return mav;
        }
    }

    @RequestMapping(value = "vets/ChangeStatusVet", method = RequestMethod.GET)
    public ModelAndView ChangeStatusVetHandler(@RequestParam String vetid, @RequestParam String source) {
        LOG.info("Executing method ChangeStatusVetHandler - VetController");
        LOG.info("vetid -> " + vetid);
        LOG.info("source -> " + source);

        ModelAndView mav = new ModelAndView();
        mav.setViewName("vet/listVets.htm");

        if (vetid != null) {

            Vet tempEmp = new Vet();
            tempEmp.setVetid(utilService.castInteger(vetid));
            myVet = restClient.getVet(tempEmp);

            if (myVet.getIsactive().equalsIgnoreCase("A")) {
                myVet.setIsactive("I");
            } else {
                myVet.setIsactive("A");
            }

            if (restClient.UpdateVet(myVet)) {
                return new ModelAndView("redirect:/vets/" + source);
            }
        }
        return mav;
    }
}
