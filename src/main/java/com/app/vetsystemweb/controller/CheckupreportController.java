package com.app.vetsystemweb.controller;

import com.app.vetsystemweb.config.Util;
import com.app.vetsystemweb.entities.Checkup;
import com.app.vetsystemweb.entities.Checkupreport;
import com.app.vetsystemweb.entities.Employee;
import com.app.vetsystemweb.entities.Pet;
import com.app.vetsystemweb.entities.Vet;
import java.text.SimpleDateFormat;
import java.util.Base64;
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
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CheckupreportController {

    final static Logger LOG = Logger.getLogger(CheckupreportController.class.getName());

    @Autowired
    VetSystemRestClient restClient;

    @Autowired
    Util utilService;

    Checkupreport myCheckupreport;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        LOG.info("Loading CheckupreportController");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @RequestMapping("checkupreports/index.htm")
    public ModelAndView MenuHandler() {
        return CheckupreportListHandler();
    }

    private ModelAndView getCheckupreportList(String checkupId, String petId, String source) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("checkupreport/listCheckupreports");
        try {
            LOG.info("Executing method getCheckupreportList - CheckupreportController");
            LOG.info("checkupId -> " + checkupId);
            LOG.info("petId -> " + petId);
            Checkup parent1 = null;
            Pet parent2 = null;

            if (checkupId != null && !checkupId.isEmpty()) {
                parent1 = new Checkup();
                parent1.setCheckupid(utilService.castInteger(checkupId));
                parent1 = restClient.getCheckup(parent1);
                if (parent1 == null) {
                    mav.addObject("errorDescription", "The Checkup selected doesn't exist");
                    return mav;
                }
            }

            if (petId != null && !petId.isEmpty()) {
                parent2 = new Pet();
                parent2.setPetid(utilService.castInteger(petId));
                parent2 = restClient.getPet(parent2);
                if (parent2 != null) {
                    if (parent2.getPhoto() != null) {
                        parent2.setBase64Image(Base64.getEncoder().encodeToString(parent2.getPhoto()));
                    }
                } else {
                    mav.addObject("errorDescription", "The pet selected doesn't exist");
                    return mav;
                }
            }

            List<Checkupreport> myResponseList = restClient.ListCheckupreports(parent1, parent2);
            if (myResponseList != null) {
                if (myResponseList.size() > 1) {
                    Comparator<Checkupreport> compareById = (Checkupreport o1, Checkupreport o2) -> o1.getCheckupreportid().compareTo(o2.getCheckupreportid());
                    Collections.sort(myResponseList, compareById);
                }

                for (Checkupreport c : myResponseList) {
                    if (c.getReportnotes() != null) {
                        try {
                            c.setReportnotestext(new String(Base64.getDecoder().decode(c.getReportnotes())));
                        } catch (Exception ex) {
                        }
                    }
                }
            }
            Map<Integer, String> myList = getVetList();
            mav.addObject("VetList", myList);

            Map<Integer, String> myList2 = getPetList();
            mav.addObject("PetList", myList2);

            mav.addObject("myCheckup", parent1);
            mav.addObject("myPet", parent2);
            mav.addObject("listResponse", myResponseList);
            mav.addObject("source", source);

            //else {
            //    mav.addObject("errorDescription", "The criteria selected doesn't fit with the data");
            //}
            return mav;

        } catch (RestClientException ex) {
            mav.addObject("errorDescription", "We know that an unexpected error has occurred, please try again later");
            LOG.warning(ex.getMessage());
            return mav;
        }
    }

    @RequestMapping(value = "checkupreports/listCheckupreports.htm")
    public ModelAndView CheckupreportListHandler() {
        return getCheckupreportList(null, null, "listCheckupreports.htm");
    }

    @RequestMapping(value = "checkupreports/adminCheckup.htm", method = RequestMethod.GET)
    public ModelAndView CheckupreportListByVetHandler(String checkupid) {
        return getCheckupreportList(checkupid, null, "adminVet.htm?checkupid=" + checkupid);
    }

    @RequestMapping(value = "checkupreports/adminPet.htm", method = RequestMethod.GET)
    public ModelAndView CheckupreportListByPetHandler(String petid) {
        return getCheckupreportList(null, petid, "adminPet.htm?petid=" + petid);
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

    private Map<Integer, String> getPetList() {
        List<Pet> myResponseList = restClient.ListPets(null);
        Map<Integer, String> TempList = new LinkedHashMap<>();
        if (myResponseList != null) {
            if (myResponseList.size() > 1) {
                Comparator<Pet> compareByid = (Pet o1, Pet o2) -> o1.getPetid().compareTo(o2.getPetid());
                Collections.sort(myResponseList, compareByid);
            }

            for (Pet c : myResponseList) {
                if (c.getIsactive().equalsIgnoreCase("A")) {
                    TempList.put(c.getPetid(), c.getName());
                }
            }
        }
        return TempList;
    }

    @RequestMapping(value = "checkupreports/newCheckupreport.htm", method = RequestMethod.GET)
    public ModelAndView NewCheckupreportHandler(@RequestParam String checkupid) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("checkupreport/checkupreportDetail");

        Checkup parent1 = null;

        if (checkupid != null && !checkupid.isEmpty()) {
            parent1 = new Checkup();
            parent1.setCheckupid(utilService.castInteger(checkupid));
            parent1 = restClient.getCheckup(parent1);
            if (parent1 == null) {
                mav.addObject("errorDescription", "The Checkup selected doesn't exist");
                return mav;
            }
        }

        mav.addObject("myCheckup", parent1);

        Map<Integer, String> myList = getVetList();
        mav.addObject("VetList", myList);

        Map<Integer, String> myList2 = getPetList();
        mav.addObject("PetList", myList2);

        Checkupreport temp = new Checkupreport();
        temp.setCheckupreportid(-1);
        mav.addObject("Checkupreport", temp);

        return mav;
    }

    @RequestMapping(value = "checkupreports/editCheckupreport", method = RequestMethod.GET)
    public ModelAndView EditCheckupreportHandler(@RequestParam String checkupreportid) {
        //LOG.info("ejecuta metodo NewCheckupreportHandler - CheckupreportController, checkupreport id " + checkupreportid);
        ModelAndView mav = new ModelAndView();
        mav.setViewName("checkupreport/checkupreportDetail");

        if (checkupreportid != null) {

            Checkupreport myCheckupreportTmp = new Checkupreport();

            myCheckupreportTmp.setCheckupreportid(utilService.castInteger(checkupreportid));

            myCheckupreport = restClient.getCheckupreport(myCheckupreportTmp);

            mav.addObject("Checkupreport", myCheckupreport);
        } else {
            Checkupreport temp = new Checkupreport();
            temp.setCheckupreportid(-1);
            mav.addObject("Checkupreport", temp);
        }

        Map<Integer, String> myList = getVetList();
        mav.addObject("VetList", myList);

        Map<Integer, String> myList2 = getPetList();
        mav.addObject("PetList", myList2);

        return mav;
    }

    @RequestMapping(value = "checkupreports/newCheckupreport.htm", method = RequestMethod.POST)
    public ModelAndView NewCheckupreportHandler(HttpSession session, Checkupreport checkupreport) {

        LOG.info("Executing methor NewCheckupreportHandler - CheckupreportController");
        LOG.info("checkupreport -> " + ReflectionToStringBuilder.toString(checkupreport));

        ModelAndView mav = new ModelAndView();
        mav.setViewName("checkupreport/checkupreportDetail");

        try {
            if (myCheckupreport == null) {
                myCheckupreport = new Checkupreport();
            }

            if (myCheckupreport.getReportattachment() != null) {
                checkupreport.setReportattachment(myCheckupreport.getReportattachment());
            }

            if (checkupreport.getReportnotestext() != null) {

                byte[] encoded;
                encoded = Base64.getEncoder().encode(checkupreport.getReportnotestext().getBytes());

                checkupreport.setReportnotes(encoded);
            }

            /*
            if (!file.isEmpty()) {
                byte[] bytes = file.getBytes();
                checkupreport.setReportattachment(bytes);
            }
             */
            if (checkupreport.getCheckupreportid() == -1) {
                Checkup tempCheckup = new Checkup();
                tempCheckup.setVetid(checkupreport.getCheckupid());

                Pet tempPet = new Pet();
                tempPet.setPetid(checkupreport.getPetid());

                checkupreport.setCheckup(tempCheckup);
                checkupreport.setPet(tempPet);
                checkupreport.setIsactive("A");
                LOG.info("checkupreport 2 -> " + ReflectionToStringBuilder.toString(checkupreport));
                if (restClient.createCheckupreport(checkupreport)) {
                    return new ModelAndView("redirect:/checkupreports/adminCheckup.htm?checkupid=" + checkupreport.getCheckupid());
                }
                mav.addObject("errorDescription", "We know that an unexpected error has occurred, please try again later");
            } else {
                LOG.info("checkupreport 3 -> " + ReflectionToStringBuilder.toString(checkupreport));
                if (restClient.UpdateCheckupreport(checkupreport)) {
                    return new ModelAndView("redirect:/checkupreports/listCheckupreports.htm");
                }
                mav.addObject("errorDescription", "We know that an unexpected error has occurred, please try again later");
            }
            return mav;
        } catch (RestClientException ex) {
            mav.addObject("Checkupreport", checkupreport);
            mav.addObject("errorDescription", "We know that an unexpected error has occurred, please try again later");
            LOG.warning(ex.getMessage());
            return mav;
        }
    }

    @RequestMapping(value = "checkupreports/ChangeStatusCheckupreport", method = RequestMethod.GET)
    public ModelAndView ChangeStatusCheckupreportHandler(@RequestParam String checkupreportid, @RequestParam String source) {

        ModelAndView mav = new ModelAndView();
        mav.setViewName("checkupreport/listCheckupreports");
        LOG.info("checkupreportid -> " + checkupreportid);
        LOG.info("source -> " + source);
        if (checkupreportid != null) {

            Checkupreport tempCheckupreport = new Checkupreport();
            tempCheckupreport.setCheckupreportid(utilService.castInteger(checkupreportid));
            myCheckupreport = restClient.getCheckupreport(tempCheckupreport);

            if (myCheckupreport.getIsactive().equalsIgnoreCase("A")) {
                myCheckupreport.setIsactive("I");
            } else {
                myCheckupreport.setIsactive("A");
            }

            if (restClient.UpdateCheckupreport(myCheckupreport)) {
                return new ModelAndView("redirect:/checkupreports/" + source);
            }
        }

        return mav;
    }
}
