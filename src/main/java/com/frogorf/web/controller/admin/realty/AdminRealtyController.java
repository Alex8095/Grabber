package com.frogorf.web.controller.admin.realty;

import com.frogorf.grabber.domain.Task;
import com.frogorf.kendo.data.source.DataSourceRequest;
import com.frogorf.kendo.data.source.DataSourceResult;
import com.frogorf.realty.domain.Realty;
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
@SessionAttributes(types = {Realty.class})
@Controller
@RequestMapping("/admin/")
public class AdminRealtyController {
    private static final Logger logger = LoggerFactory.getLogger(AdminRealtyController.class);

    @Autowired
    private RealtyService realtyService;

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @RequestMapping(value = "/realty/grid", method = {RequestMethod.GET, RequestMethod.POST})
    public
    @ResponseBody
    DataSourceResult grid(DataSourceRequest request) {
        logger.info("grid call");
        return realtyService.getListRealty(request);
    }

    @RequestMapping(value = {"/realty/list", "/realty"}, method = RequestMethod.GET)
    public String list(Model model) {
        logger.info("list call");
        return "admin/realty/list";
    }

    @RequestMapping(value = "/realty/{id}", method = RequestMethod.GET)
    public String detail(@PathVariable("id") int id, Model model) {
        logger.info("detail call");
        model.addAttribute("realty", realtyService.findRealtyById(id));
        return "admin/realty/detail";
    }

    @RequestMapping(value = "/realty/new", method = RequestMethod.GET)
    public String newItem(Model model) {
        logger.info("newRealty call");
        Realty realty = new Realty();
        model.addAttribute("realty", realty);
        return "admin/realty/edit";
    }

    @RequestMapping(value = "/realty/new", method = RequestMethod.POST)
    public String create(@ModelAttribute @Valid Realty realty, BindingResult result, SessionStatus status) {
        logger.info("create call");
        if (result.hasErrors()) {
            return "admin/realty/edit";
        } else {
            realtyService.saveRealty(realty);
            status.setComplete();
            return "redirect:/admin/realty/" + realty.getId();
        }
    }

    @RequestMapping(value = "/realty/{id}/edit", method = RequestMethod.GET)
    public String edit(@PathVariable("id") int id, Model model) {
        logger.info("edit call");
        model.addAttribute("task", realtyService.findRealtyById(id));
        return "admin/grabber/realty";
    }

    @RequestMapping(value = "/realty/{id}/edit", method = RequestMethod.POST)
    public String update(@ModelAttribute @Valid Realty realty, BindingResult result, SessionStatus status) {
        logger.info("update call");
        if (result.hasErrors()) {
            return "admin/realty/edit";
        } else {
            realtyService.saveRealty(realty);
            status.setComplete();
            return "redirect:/admin/realty/" + realty.getId();
        }
    }
}
