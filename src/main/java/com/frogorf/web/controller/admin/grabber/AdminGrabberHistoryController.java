package com.frogorf.web.controller.admin.grabber;

import com.frogorf.grabber.domain.TaskHistory;
import com.frogorf.grabber.service.GrabberService;
import com.frogorf.kendo.data.source.DataSourceRequest;
import com.frogorf.kendo.data.source.DataSourceResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Alex on 06.11.14.
 */
@SessionAttributes(types = {TaskHistory.class})
@Controller
@RequestMapping("/admin/grabber/")
public class AdminGrabberHistoryController {
    private static final Logger logger = LoggerFactory.getLogger(AdminGrabberHistoryController.class);

    @Autowired
    private GrabberService grabberService;

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @RequestMapping(value = "/history/grid", method = {RequestMethod.GET, RequestMethod.POST})
    public
    @ResponseBody
    DataSourceResult grid(DataSourceRequest request) {
        logger.info("grid call");
        return grabberService.getListTaskHistory(request);
    }

    @RequestMapping(value = {"/history/list", "/history"}, method = RequestMethod.GET)
    public String listTaskHistory(Model model) {
        logger.info("listTaskHistory call");
        return "admin/grabber/history/list";
    }

    @RequestMapping(value = "/history/{id}", method = RequestMethod.GET)
    public String detailTaskHistory(@PathVariable("id") int id, Model model) {
        logger.info("detailTaskHistory call");
        model.addAttribute("history", grabberService.findTaskHistoryById(id));
        return "admin/grabber/history/detail";
    }
}
