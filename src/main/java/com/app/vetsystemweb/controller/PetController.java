package com.app.vetsystemweb.controller;

import com.app.vetsystemweb.config.Util;
import com.app.vetsystemweb.entities.Owner;
import com.app.vetsystemweb.entities.Pet;
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
public class PetController {

    final static Logger LOG = Logger.getLogger(PetController.class.getName());

    @Autowired
    VetSystemRestClient restClient;

    @Autowired
    Util utilService;

    Pet myPet;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        LOG.info("Loading PetController");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @RequestMapping("pets/index.htm")
    public ModelAndView MenuHandler() {
        return PetListHandler();
    }

    private ModelAndView getPetList(String ownerId, String source) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("pet/listPets");
        try {
            LOG.info("Executing method getPetList - PetController");
            LOG.info("ownerId -> " + ownerId);
            Owner myOwner = new Owner();

            if (ownerId != null && !ownerId.isEmpty()) {
                myOwner.setOwnerid(utilService.castInteger(ownerId));
                myOwner = restClient.getOwner(myOwner);
            }
            if (myOwner != null) {
                List<Pet> myResponseList = restClient.ListPets(myOwner);
                if (myResponseList != null) {
                    if (myResponseList.size() > 1) {
                        Comparator<Pet> compareById = (Pet o1, Pet o2) -> o1.getPetid().compareTo(o2.getPetid());
                        Collections.sort(myResponseList, compareById);
                    }
                }

                mav.addObject("myOwner", myOwner);
                mav.addObject("listResponse", myResponseList);
                mav.addObject("source", source);
            } else {
                mav.addObject("errorDescription", "The owner selected doesn't exist");
            }
            return mav;

        } catch (RestClientException ex) {
            mav.addObject("errorDescription", "We know that an unexpected error has occurred, please try again later");
            LOG.warning(ex.getMessage());
            return mav;
        }
    }

    @RequestMapping(value = "pets/listPets.htm")
    public ModelAndView PetListHandler() {
        return getPetList(null, "listPets.htm");
    }

    @RequestMapping(value = "pets/adminOwner.htm", method = RequestMethod.GET)
    public ModelAndView PetListHandler(String ownerid) {
        return getPetList(ownerid, "adminOwner.htm?ownerid=" + ownerid);
    }

    /// change to get branch list
    private Map<Integer, String> getOwnerList() {

        List<Owner> myResponseList = restClient.ListOwners();
        Map<Integer, String> TempList = new LinkedHashMap<>();
        if (myResponseList != null) {
            if (myResponseList.size() > 1) {
                Comparator<Owner> compareByid = (Owner o1, Owner o2) -> o1.getOwnerid().compareTo(o2.getOwnerid());
                Collections.sort(myResponseList, compareByid);
            }

            for (Owner c : myResponseList) {
                if (c.getIsactive().equalsIgnoreCase("A")) {
                    TempList.put(c.getOwnerid(), c.getName());
                }
            }
        }

        return TempList;
    }

    @RequestMapping(value = "pets/newPet.htm", method = RequestMethod.GET)
    public ModelAndView NewPetHandler(String ownerid) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("pet/petDetail");

        Map<Integer, String> myList = getOwnerList();
        mav.addObject("OwnerList", myList);

        Pet temp = new Pet();
        temp.setPetid(-1);
        mav.addObject("Pet", temp);

        return mav;
    }

    @RequestMapping(value = "pets/editPet", method = RequestMethod.GET)
    public ModelAndView EditPetHandler(@RequestParam String petid, String ownerid) {
        //LOG.info("ejecuta metodo NewPetHandler - PetController, pet id " + petid);
        ModelAndView mav = new ModelAndView();
        mav.setViewName("pet/petDetail");
        
        if (petid != null) {

            Pet myPetTmp = new Pet();

            myPetTmp.setPetid(utilService.castInteger(petid));

            myPet = restClient.getPet(myPetTmp);
            if (myPet.getPhoto() != null) {
                myPet.setBase64Image(Base64.getEncoder().encodeToString(myPet.getPhoto()));
            }
            mav.addObject("Pet", myPet);
        } else {
            mav.addObject("Pet", new Pet());
        }

        Map<Integer, String> myList = getOwnerList();
        mav.addObject("OwnerList", myList);

        
        return mav;
    }

    @RequestMapping(value = "pets/newPet.htm", method = RequestMethod.POST)
    public ModelAndView NewPetHandler(@RequestParam CommonsMultipartFile file, HttpSession session, Pet pet) {
        
        LOG.info("Executing methor NewPetHandler - PetController");
        LOG.info("pet -> " + ReflectionToStringBuilder.toString(pet));
        
        ModelAndView mav = new ModelAndView();
        mav.setViewName("pet/petDetail");
        
        //LOG.info("file -> " + file);

        try {
            if (myPet == null) {
                myPet = new Pet();
            }

            if (myPet.getPhoto() != null) {
                pet.setPhoto(myPet.getPhoto());
            }

            if (!file.isEmpty()) {
                byte[] bytes = file.getBytes();
                pet.setPhoto(bytes);
            }

            if (pet.getPetid() == -1) {
                Owner tempOwner = new Owner();

                tempOwner.setOwnerid(pet.getOwnerid());
                pet.setOwner(tempOwner);
                pet.setIsactive("A");
                if (restClient.createPet(pet)) {
                    return new ModelAndView("redirect:/pets/listPets.htm");
                }

                mav.addObject("errorDescription", "We know that an unexpected error has occurred, please try again later");
                //mav.setViewName("pets/petDetail");
            } else {
                if (restClient.UpdatePet(pet)) {
                    return new ModelAndView("redirect:/pets/listPets.htm");
                }

                mav.addObject("errorDescription", "We know that an unexpected error has occurred, please try again later");
                //mav.setViewName("pets/petDetail");
            }
            return mav;
        } catch (RestClientException ex) {
            mav.addObject("Pet", pet);
            mav.addObject("errorDescription", "We know that an unexpected error has occurred, please try again later");
            LOG.warning(ex.getMessage());
            //mav.setViewName("pets/petDetail");
            return mav;
        }
    }

    @RequestMapping(value = "pets/ChangeStatusPet", method = RequestMethod.GET)
    public ModelAndView ChangeStatusPetHandler(@RequestParam String petid, @RequestParam String source) {

        ModelAndView mav = new ModelAndView();
        mav.setViewName("pet/listPets");
        LOG.info("petid -> " + petid);
        LOG.info("source -> " + source);
        if (petid != null) {

            Pet tempPet = new Pet();
            tempPet.setPetid(utilService.castInteger(petid));
            myPet = restClient.getPet(tempPet);

            if (myPet.getIsactive().equalsIgnoreCase("A")) {
                myPet.setIsactive("I");
            } else {
                myPet.setIsactive("A");
            }

            if (restClient.UpdatePet(myPet)) {
                return new ModelAndView("redirect:/pets/" + source);
            }
        }

        return mav;
    }
}
