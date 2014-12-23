package com.frogorf.web.controller.admin.grabber;

import com.frogorf.grabber.domain.TaskSetting;
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
@SessionAttributes(types = {TaskSetting.class})
@Controller
@RequestMapping("/admin/grabber/")
public class AdminGrabberSettingController {
    private static final Logger logger = LoggerFactory.getLogger(AdminGrabberSettingController.class);

    @Autowired
    private GrabberService grabberService;

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @RequestMapping(value = "/setting/grid", method = {RequestMethod.GET, RequestMethod.POST})
    public
    @ResponseBody
    DataSourceResult grid(DataSourceRequest request) {
        logger.info("grid call");
        return grabberService.getListTaskSetting(request);
    }

    @RequestMapping(value = {"/setting/list", "/setting"}, method = RequestMethod.GET)
    public String listTaskSetting(Model model) {
        logger.info("listTaskSettingModel call");
        return "admin/grabber/setting/list";
    }

    @RequestMapping(value = "/setting/{id}", method = RequestMethod.GET)
    public String detailTaskSetting(@PathVariable("id") int id, Model model) {
        logger.info("detailTaskSetting call");
        model.addAttribute("setting", grabberService.findTaskSettingById(id));
        return "admin/grabber/setting/detail";
    }

    @RequestMapping(value = "/setting/new", method = RequestMethod.GET)
    public String newTaskSetting(Model model) {
        logger.info("newTask call");
        TaskSetting setting = new TaskSetting();
        model.addAttribute("setting", setting);
        return "admin/grabber/setting/edit";
    }

    @RequestMapping(value = "/setting/new", method = RequestMethod.POST)
    public String createTaskSetting(@ModelAttribute @Valid TaskSetting taskSetting, BindingResult result, SessionStatus status) {
        logger.info("createTask call");
        if (result.hasErrors()) {
            return "admin/grabber/setting/edit";
        } else {
            grabberService.saveTaskSetting(taskSetting);
            status.setComplete();
            return "redirect:/admin/grabber/setting/" + taskSetting.getId();
        }
    }

    @RequestMapping(value = "/setting/{id}/edit", method = RequestMethod.GET)
    public String editTaskSetting(@PathVariable("id") int id, Model model) {
        logger.info("editTask call");
        model.addAttribute("setting", grabberService.findTaskSettingById(id));
        return "admin/grabber/setting/edit";
    }

    @RequestMapping(value = "/setting/{id}/edit", method = RequestMethod.POST)
    public String updateTaskSetting(@ModelAttribute @Valid TaskSetting taskSetting, BindingResult result, SessionStatus status) {
        logger.info("updateTask call");
        if (result.hasErrors()) {
            return "admin/task/edit";
        } else {
            grabberService.saveTaskSetting(taskSetting);
            status.setComplete();
            return "redirect:/admin/grabber/setting/" + taskSetting.getId();
        }
    }
}
