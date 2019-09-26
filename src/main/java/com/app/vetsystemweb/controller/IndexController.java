package com.app.vetsystemweb.controller;

import java.util.logging.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class IndexController {
    ModelAndView mav = new ModelAndView();
    final static Logger logger = Logger.getLogger(IndexController.class.getName());
    
    @RequestMapping("/index.htm")
    public ModelAndView listReg(){
        mav.setViewName("index");
        return mav;
    }
}
