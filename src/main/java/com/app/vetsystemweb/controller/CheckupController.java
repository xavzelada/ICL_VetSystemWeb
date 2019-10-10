package com.app.vetsystemweb.controller;

import com.app.vetsystemweb.config.Util;
import com.app.vetsystemweb.entities.Checkup;
import com.app.vetsystemweb.entities.Employee;
import com.app.vetsystemweb.entities.Vet;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
public class CheckupController {

    final static Logger LOG = Logger.getLogger(CheckupController.class.getName());

    @Autowired
    VetSystemRestClient restClient;

    @Autowired
    Util utilService;

    Checkup myCheckup;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        LOG.info("Loading CheckupController");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @RequestMapping("checkups/index.htm")
    public ModelAndView MenuHandler() {
        return CheckupListHandler();
    }

    private ModelAndView getCheckupList(String vetId, String source) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("checkup/listCheckups");
        try {
            LOG.info("Executing method getCheckupList - CheckupController");
            LOG.info("vetId -> " + vetId);
            Vet parent = new Vet();

            if (vetId != null && !vetId.isEmpty()) {
                parent.setVetid(utilService.castInteger(vetId));
                parent = restClient.getVet(parent);
            }
            if (parent != null) {
                List<Checkup> myResponseList = restClient.ListCheckups(parent);
                if (myResponseList != null) {
                    if (myResponseList.size() > 1) {
                        Comparator<Checkup> compareById = (Checkup o1, Checkup o2) -> o1.getCheckupid().compareTo(o2.getCheckupid());
                        Collections.sort(myResponseList, compareById);
                    }
                }

                Map<Integer, String> myList = getVetList();
                mav.addObject("VetList", myList);

                mav.addObject("myVet", parent);
                mav.addObject("listResponse", myResponseList);
                mav.addObject("source", source);
            } else {
                mav.addObject("errorDescription", "The vet selected doesn't exist");
            }
            return mav;

        } catch (RestClientException ex) {
            mav.addObject("errorDescription", "We know that an unexpected error has occurred, please try again later");
            LOG.warning(ex.getMessage());
            return mav;
        }
    }

    @RequestMapping(value = "checkups/listCheckups.htm")
    public ModelAndView CheckupListHandler() {
        return getCheckupList(null, "listCheckups.htm");
    }

    @RequestMapping(value = "checkups/adminVet.htm", method = RequestMethod.GET)
    public ModelAndView CheckupListHandler(String vetid) {
        return getCheckupList(vetid, "adminVet.htm?vetid=" + vetid);
    }

    private Map<Integer, String> getVetList() {

        List<Employee> myResponseList = restClient.ListEmployees(null);
        Map<Integer, String> TempList = new LinkedHashMap<>();
        if (myResponseList != null) {
            if (myResponseList.size() > 1) {
                Comparator<Employee> compareByid = (Employee o1, Employee o2) -> o1.getEmployeeid().compareTo(o2.getEmployeeid());
                Collections.sort(myResponseList, compareByid);
            }

            for (Employee c : myResponseList) {
                if (c.getIsactive().equalsIgnoreCase("A") && c.getRole().equalsIgnoreCase("4")) {

                    Vet vetTmp = new Vet();
                    vetTmp.setEmployeeid(c.getEmployeeid());
                    vetTmp = restClient.getVet(vetTmp);
                    if (vetTmp != null) {
                        TempList.put(vetTmp.getVetid(), c.getName() + " " + c.getSurname());
                    }
                }
            }
        }

        return TempList;
    }

    @RequestMapping(value = "checkups/newCheckup.htm", method = RequestMethod.GET)
    public ModelAndView NewCheckupHandler(String ownerid) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("checkup/checkupDetail");

        Map<Integer, String> myList = getVetList();
        mav.addObject("VetList", myList);

        Checkup temp = new Checkup();
        temp.setCheckupid(-1);
        mav.addObject("Checkup", temp);

        return mav;
    }

    @RequestMapping(value = "checkups/editCheckup", method = RequestMethod.GET)
    public ModelAndView EditCheckupHandler(@RequestParam String checkupid, String vetid) {
        //LOG.info("ejecuta metodo NewCheckupHandler - CheckupController, checkup id " + checkupid);
        ModelAndView mav = new ModelAndView();
        mav.setViewName("checkup/checkupDetail");

        if (checkupid != null) {

            Checkup myCheckupTmp = new Checkup();

            myCheckupTmp.setCheckupid(utilService.castInteger(checkupid));

            myCheckup = restClient.getCheckup(myCheckupTmp);

            mav.addObject("Checkup", myCheckup);
        } else {
            mav.addObject("Checkup", new Checkup());
        }

        Map<Integer, String> myList = getVetList();
        mav.addObject("VetList", myList);

        return mav;
    }

    @RequestMapping(value = "checkups/newCheckup.htm", method = RequestMethod.POST)
    public ModelAndView NewCheckupHandler(HttpSession session, Checkup checkup) {

        LOG.info("Executing methor NewCheckupHandler - CheckupController");
        LOG.info("checkup -> " + ReflectionToStringBuilder.toString(checkup));

        ModelAndView mav = new ModelAndView();
        mav.setViewName("checkup/checkupDetail");

        try {
            if (myCheckup == null) {
                myCheckup = new Checkup();
            }

            if (checkup.getCheckupid() == -1) {
                Vet tempVet = new Vet();

                tempVet.setVetid(checkup.getVetid());
                checkup.setVet(tempVet);
                checkup.setIsactive("A");
                LOG.info("checkup 2 -> " + ReflectionToStringBuilder.toString(checkup));
                if (restClient.createCheckup(checkup)) {
                    return new ModelAndView("redirect:/checkups/listCheckups.htm");
                }
                mav.addObject("errorDescription", "We know that an unexpected error has occurred, please try again later");
            } else {
                LOG.info("checkup 3 -> " + ReflectionToStringBuilder.toString(checkup));
                if (restClient.UpdateCheckup(checkup)) {
                    return new ModelAndView("redirect:/checkups/listCheckups.htm");
                }
                mav.addObject("errorDescription", "We know that an unexpected error has occurred, please try again later");
            }
            return mav;
        } catch (RestClientException ex) {
            mav.addObject("Checkup", checkup);
            mav.addObject("errorDescription", "We know that an unexpected error has occurred, please try again later");
            LOG.warning(ex.getMessage());
            return mav;
        }
    }

    @RequestMapping(value = "checkups/ChangeStatusCheckup", method = RequestMethod.GET)
    public ModelAndView ChangeStatusCheckupHandler(@RequestParam String checkupid, @RequestParam String source) {

        ModelAndView mav = new ModelAndView();
        mav.setViewName("checkup/listCheckups");
        LOG.info("checkupid -> " + checkupid);
        LOG.info("source -> " + source);
        if (checkupid != null) {

            Checkup tempCheckup = new Checkup();
            tempCheckup.setCheckupid(utilService.castInteger(checkupid));
            myCheckup = restClient.getCheckup(tempCheckup);

            if (myCheckup.getIsactive().equalsIgnoreCase("A")) {
                myCheckup.setIsactive("I");
            } else {
                myCheckup.setIsactive("A");
            }

            if (restClient.UpdateCheckup(myCheckup)) {
                return new ModelAndView("redirect:/checkups/" + source);
            }
        }

        return mav;
    }
}
