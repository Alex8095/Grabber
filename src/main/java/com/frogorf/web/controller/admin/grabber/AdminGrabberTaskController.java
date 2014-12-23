package com.frogorf.web.controller.admin.grabber;

import com.frogorf.dictionary.service.DictionaryService;
import com.frogorf.grabber.domain.Task;
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
@SessionAttributes(types = {Task.class})
@Controller
@RequestMapping("/admin/grabber/")
public class AdminGrabberTaskController {
    private static final Logger logger = LoggerFactory.getLogger(AdminGrabberTaskController.class);
    private final String DV_CODE_OWNER = "OWNER";
    private final String DV_CODE_IMPLEMENTATION = "IMPLEMENTATION";

    @Autowired
    private GrabberService grabberService;

    @Autowired
    private DictionaryService dictionaryService;


    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @RequestMapping(value = "/task/grid", method = {RequestMethod.GET, RequestMethod.POST})
    public
    @ResponseBody
    DataSourceResult grid(DataSourceRequest request) {
        logger.info("grid call");
        return grabberService.getListTask(request);
    }

    @RequestMapping(value = {"/task/list", "/task"}, method = RequestMethod.GET)
    public String listTask(Model model) {
        logger.info("listTask call");
        return "admin/grabber/task/list";
    }

    @RequestMapping(value = "/task/{id}", method = RequestMethod.GET)
    public String detailTask(@PathVariable("id") int id, Model model) {
        logger.info("detailTask call");
        model.addAttribute("task", grabberService.findTaskById(id));
        return "admin/grabber/task/detail";
    }

    @RequestMapping(value = "/task/new", method = RequestMethod.GET)
    public String newTask(Model model) {
        logger.info("newTask call");
        Task task = new Task();
        model.addAttribute("task", task);
        model.addAttribute("owners", dictionaryService.findDictionaryValuesByCode(DV_CODE_OWNER));
        model.addAttribute("implementations", dictionaryService.findDictionaryValuesByCode(DV_CODE_IMPLEMENTATION));
        return "admin/grabber/task/edit";
    }

    @RequestMapping(value = "/task/new", method = RequestMethod.POST)
    public String createTask(@ModelAttribute @Valid Task task, BindingResult result, SessionStatus status) {
        logger.info("createTask call");
        if (result.hasErrors()) {
            return "admin/grabber/task/edit";
        } else {
            grabberService.saveTask(task);
            status.setComplete();
            return "redirect:/admin/grabber/task/" + task.getId();
        }
    }

    @RequestMapping(value = "/task/{id}/edit", method = RequestMethod.GET)
    public String editTask(@PathVariable("id") int id, Model model) {
        logger.info("editTask call");
        model.addAttribute("owners", dictionaryService.findDictionaryValuesByCode(DV_CODE_OWNER));
        model.addAttribute("implementations", dictionaryService.findDictionaryValuesByCode(DV_CODE_IMPLEMENTATION));
        model.addAttribute("task", grabberService.findTaskById(id));
        return "admin/grabber/task/edit";
    }

    @RequestMapping(value = "/task/{id}/edit", method = RequestMethod.POST)
    public String updateTask(@ModelAttribute @Valid Task task, BindingResult result, SessionStatus status) {
        logger.info("updateTask call");
        if (result.hasErrors()) {
            return "admin/task/edit";
        } else {
            grabberService.saveTask(task);
            status.setComplete();
            return "redirect:/admin/grabber/task/" + task.getId();
        }
    }
}
