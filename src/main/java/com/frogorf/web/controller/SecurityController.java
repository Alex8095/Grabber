package com.frogorf.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by alex on 04.11.14.
 */
@Controller
@RequestMapping("/security")
public class SecurityController {

    private static final Logger logger = LoggerFactory.getLogger(SecurityController.class);

    @RequestMapping("/loginfail")
    public String loginFail(Model uiModel) {
        logger.info("Login failed detected");
        return "/security/login";
    }

    @RequestMapping("/login")
    public String login(Model uiModel) {
        return "/security/login";
    }
}
