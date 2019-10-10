package com.app.vetsystemweb.controller;

import com.app.vetsystemweb.config.Util;
import com.app.vetsystemweb.entities.Owner;
import java.text.SimpleDateFormat;
import java.util.Base64;
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
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class OwnerController {

    final static Logger LOG = Logger.getLogger(OwnerController.class.getName());

    @Autowired
    VetSystemRestClient restClient;

    @Autowired
    Util utilService;

    Owner myOwner;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        LOG.info("Loading OwnerController");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @RequestMapping("owners/index.htm")
    public ModelAndView MenuHandler() {
        return OwnerListHandler();
    }

    private ModelAndView getOwnerList(String source) {
        ModelAndView mav = new ModelAndView();
        try {
            LOG.info("executing method getOwnerList - OwnerController");

            List<Owner> myResponseList = restClient.ListOwners();
            if (myResponseList != null) {
                if (myResponseList.size() > 1) {
                    Comparator<Owner> compareById = (Owner o1, Owner o2) -> o1.getOwnerid().compareTo(o2.getOwnerid());
                    Collections.sort(myResponseList, compareById);
                }

                mav.addObject("listResponse", myResponseList);
                mav.addObject("source", source);
            } 
            mav.setViewName("owner/listOwners");
            return mav;

        } catch (RestClientException ex) {
            mav.addObject("errorDescription", "We know that an unexpected error has occurred, please try again later");
            LOG.warning(ex.getMessage());
            mav.setViewName("owners/newOwner.htm");
            return mav;
        }
    }

    @RequestMapping(value = "owners/listOwners.htm")
    public ModelAndView OwnerListHandler() {
        return getOwnerList("listOwners.htm");
    }

    @RequestMapping(value = "owners/newOwner.htm", method = RequestMethod.GET)
    public ModelAndView NewOwnerHandler() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("owner/ownerDetail");

        Owner temp = new Owner();
        temp.setOwnerid(-1);
        mav.addObject("Owner", temp);

        return mav;
    }

    @RequestMapping(value = "owners/editOwner", method = RequestMethod.GET)
    public ModelAndView EditOwnerHandler(@RequestParam String ownerid) {

        ModelAndView mav = new ModelAndView();
        mav.setViewName("owner/ownerDetail");
        
        if (ownerid != null) {

            Owner myOwnerTmp = new Owner();

            myOwnerTmp.setOwnerid(utilService.castInteger(ownerid));

            myOwner = restClient.getOwner(myOwnerTmp);
            if (myOwner.getPhoto() != null) {
                myOwner.setBase64Image(Base64.getEncoder().encodeToString(myOwner.getPhoto()));
            }
            mav.addObject("Owner", myOwner);
        } else {
            mav.addObject("Owner", new Owner());
        }
      
        return mav;
    }

    @RequestMapping(value = "owners/newOwner.htm", method = RequestMethod.POST)
    public ModelAndView NewOwnerHandler(@RequestParam CommonsMultipartFile file, HttpSession session, Owner owner) {
        ModelAndView mav = new ModelAndView();
        LOG.info("Executing methor NewOwnerHandler - OwnerController");
        LOG.info("owner -> " + ReflectionToStringBuilder.toString(owner));
        //LOG.info("file -> " + file);

        try {
            if (myOwner == null) {
                myOwner = new Owner();
            }

            if (myOwner.getPhoto() != null) {
                owner.setPhoto(myOwner.getPhoto());
            }

            if (!file.isEmpty()) {
                byte[] bytes = file.getBytes();
                owner.setPhoto(bytes);
            }

            if (owner.getOwnerid() == -1) {

                owner.setIsactive("A");
                if (restClient.createOwner(owner)) {
                    return new ModelAndView("redirect:/owners/listOwners.htm");
                }

                mav.addObject("errorDescription", "We know that an unexpected error has occurred, please try again later");
                mav.setViewName("owners/newOwner.htm");
            } else {
                if (restClient.UpdateOwner(owner)) {
                    return new ModelAndView("redirect:/owners/listOwners.htm");
                }

                mav.addObject("errorDescription", "We know that an unexpected error has occurred, please try again later");
                mav.setViewName("owners/newOwner.htm");
            }
            return mav;
        } catch (RestClientException ex) {
            mav.addObject("errorDescription", "We know that an unexpected error has occurred, please try again later");
            LOG.warning(ex.getMessage());
            mav.setViewName("owners/newOwner.htm");
            return mav;
        }
    }

    @RequestMapping(value = "owners/ChangeStatusOwner", method = RequestMethod.GET)
    public ModelAndView ChangeStatusOwnerHandler(@RequestParam String ownerid, @RequestParam String source) {

        ModelAndView mav = new ModelAndView();
        mav.setViewName("owner/listOwners.htm");
        LOG.info("ownerid -> " + ownerid);
        LOG.info("source -> " + source);
        
        if (ownerid != null) {

            Owner tempEmp = new Owner();
            tempEmp.setOwnerid(utilService.castInteger(ownerid));
            myOwner = restClient.getOwner(tempEmp);

            if (myOwner.getIsactive().equalsIgnoreCase("A")) {
                myOwner.setIsactive("I");
            } else {
                myOwner.setIsactive("A");
            }

            if (restClient.UpdateOwner(myOwner)) {
                return new ModelAndView("redirect:/owners/" + source);
            }

        }

        return mav;
    }
}
