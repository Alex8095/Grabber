package com.frogorf.web.controller.admin.grabber;

import com.frogorf.grabber.helper.RealtyOptionCodeReader;
import com.frogorf.grabber.helper.selector.RealtySelector;
import com.frogorf.grabber.service.GrabberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Alex on 06.11.14.
 */
@Controller
@RequestMapping("/admin/grabber/tests")
public class AdminGrabberTestsController {
    private static final Logger logger = LoggerFactory.getLogger(AdminGrabberTestsController.class);

    @Autowired
    private GrabberService grabberService;
    @Autowired
    private RealtyOptionCodeReader realtyOptionCodeReader;

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public String listSourceSetting(Model model) {
        model.addAttribute("realtyOptionCodes", realtyOptionCodeReader.getCodes());
        model.addAttribute("ownerWordsArrayItem", RealtySelector.OWNER_WORDS_ARRAY[0]);
        return "admin/grabber/tests/list";
    }
}
