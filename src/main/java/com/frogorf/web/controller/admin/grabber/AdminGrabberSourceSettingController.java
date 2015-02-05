package com.frogorf.web.controller.admin.grabber;

import com.frogorf.grabber.domain.SourceSetting;
import com.frogorf.grabber.service.GrabberService;
import com.frogorf.kendo.data.source.DataSourceRequest;
import com.frogorf.kendo.data.source.DataSourceResult;
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
@SessionAttributes(types = {SourceSetting.class})
@Controller
@RequestMapping("/admin/grabber/")
public class AdminGrabberSourceSettingController {
    private static final Logger logger = LoggerFactory.getLogger(AdminGrabberSourceSettingController.class);

    @Autowired
    private GrabberService grabberService;

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @RequestMapping(value = "/sourceSetting/grid", method = {RequestMethod.GET, RequestMethod.POST})
    public
    @ResponseBody
    DataSourceResult grid(DataSourceRequest request) {
        return grabberService.findSourceSettings(request);
    }

    @RequestMapping(value = {"/sourceSetting/list", "/sourceSetting"}, method = RequestMethod.GET)
    public String listSourceSetting(Model model) {
        return "admin/grabber/sourceSetting/list";
    }

    @RequestMapping(value = "/sourceSetting/{id}", method = RequestMethod.GET)
    public String detailSourceSetting(@PathVariable("id") int id, Model model) {
        model.addAttribute("sourceSetting", grabberService.findSourceSettingById(id));
        return "admin/grabber/sourceSetting/detail";
    }

    @RequestMapping(value = "/sourceSetting/new", method = RequestMethod.GET)
    public String newSourceSetting(Model model) {
        SourceSetting setting = new SourceSetting();
        model.addAttribute("sourceSetting", setting);
        return "admin/grabber/sourceSetting/edit";
    }

    @RequestMapping(value = "/sourceSetting/new", method = RequestMethod.POST)
    public String createSourceSetting(@ModelAttribute @Valid SourceSetting sourceSetting, BindingResult result, SessionStatus status) {
        if (result.hasErrors()) {
            return "admin/grabber/sourceSetting/edit";
        } else {
            grabberService.saveSourceSetting(sourceSetting);
            status.setComplete();
            return "redirect:/admin/grabber/sourceSetting/" + sourceSetting.getId();
        }
    }

    @RequestMapping(value = "/sourceSetting/{id}/edit", method = RequestMethod.GET)
    public String editSourceSetting(@PathVariable("id") int id, Model model) {
        model.addAttribute("setting", grabberService.findSourceSettingById(id));
        return "admin/grabber/sourceSetting/edit";
    }

    @RequestMapping(value = "/sourceSetting/{id}/edit", method = RequestMethod.POST)
    public String updateSourceSetting(@ModelAttribute @Valid SourceSetting sourceSetting, BindingResult result, SessionStatus status) {
        if (result.hasErrors()) {
            return "admin/sourceSetting/edit";
        } else {
            grabberService.saveSourceSetting(sourceSetting);
            status.setComplete();
            return "redirect:/admin/grabber/sourceSetting/" + sourceSetting.getId();
        }
    }
}
