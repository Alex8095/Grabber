package com.frogorf.web.controller.admin.realty;

import com.frogorf.kendo.data.source.DataSourceRequest;
import com.frogorf.kendo.data.source.DataSourceResult;
import com.frogorf.realty.domain.RealtyOption;
import com.frogorf.realty.service.RealtyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;

/**
 * Created by Alex on 06.11.14.
 */
@SessionAttributes(types = {RealtyOption.class})
@Controller
@RequestMapping("/admin/realty/")
public class AdminRealtyOptionController {
    private static final Logger logger = LoggerFactory.getLogger(AdminRealtyOptionController.class);

    @Autowired
    private RealtyService realtyService;

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @RequestMapping(value = "/option/grid", method = {RequestMethod.GET, RequestMethod.POST})
    public
    @ResponseBody
    DataSourceResult grid(DataSourceRequest request) {
        logger.info("grid call");
        return realtyService.getListRealtyOption(request);
    }

    @RequestMapping(value = {"/option/list", "/option"}, method = RequestMethod.GET)
    public String list(Model model) {
        logger.info("list call");
        return "admin/realty/option/list";
    }

    @RequestMapping(value = "/option/{id}", method = RequestMethod.GET)
    public String detail(@PathVariable("id") int id, Model model) {
        logger.info("detail call");
        model.addAttribute("realtyOption", realtyService.findRealtyOptionById(id));
        return "admin/realty/option/detail";
    }

    @RequestMapping(value = "/option/new", method = RequestMethod.GET)
    public String newItem(Model model) {
        logger.info("newRealty call");
        RealtyOption realtyOption = new RealtyOption();
        model.addAttribute("realtyOption", realtyOption);
        return "admin/realty/option/edit";
    }

    @RequestMapping(value = "/option/new", method = RequestMethod.POST)
    public String create(@ModelAttribute @Valid RealtyOption realtyOption, BindingResult result, SessionStatus status) {
        logger.info("create call");
        if (result.hasErrors()) {
            return "admin/realty/option/edit";
        } else {
            realtyService.saveRealtyOption(realtyOption);
            status.setComplete();
            return "redirect:/admin/realty/option/" + realtyOption.getId();
        }
    }

    @RequestMapping(value = "/option/{id}/edit", method = RequestMethod.GET)
    public String edit(@PathVariable("id") int id, Model model) {
        logger.info("edit call");
        model.addAttribute("realtyOption", realtyService.findRealtyOptionById(id));
        return "admin/grabber/option/realty";
    }

    @RequestMapping(value = "/option/{id}/edit", method = RequestMethod.POST)
    public String update(@ModelAttribute @Valid RealtyOption realtyOption, BindingResult result, SessionStatus status) {
        logger.info("update call");
        if (result.hasErrors()) {
            return "admin/realty/option/edit";
        } else {
            realtyService.saveRealtyOption(realtyOption);
            status.setComplete();
            return "redirect:/admin/realty/option/" + realtyOption.getId();
        }
    }
}
