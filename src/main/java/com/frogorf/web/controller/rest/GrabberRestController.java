package com.frogorf.web.controller.rest;

import com.frogorf.dictionary.service.DictionaryService;
import com.frogorf.grabber.domain.Task;
import com.frogorf.grabber.domain.TaskHistory;
import com.frogorf.grabber.parser.CategoryParser;
import com.frogorf.grabber.service.GrabberService;
import com.frogorf.utils.exception.GrabberException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Alex on 06.11.14.
 */
@Controller
@RequestMapping("/grabberRest/")
public class GrabberRestController {
    private static final Logger logger = LoggerFactory.getLogger(GrabberRestController.class);

    @Autowired
    private GrabberService grabberService;
    @Autowired
    private DictionaryService dictionaryService;

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @Autowired
    private CategoryParser categoryParser;

    @RequestMapping(value = {"tasks"}, method = RequestMethod.GET)
    public
    @ResponseBody
    List<TaskHistory> executeTasts() {
        List<TaskHistory> res = new ArrayList<>();
        for (Task task : grabberService.findTasks(null)) {
            res.add(parseCategoryTask(null, task));
        }
        return res;
    }

    @RequestMapping(value = "task/{id}", method = {RequestMethod.GET})
    public
    @ResponseBody
    TaskHistory executeTask(@PathVariable("id") int task_id) {
        return parseCategoryTask(task_id, null);
    }

    private TaskHistory parseCategoryTask(Integer id, Task task) {
        categoryParser.initTask(id, task);
        if (categoryParser.getTask() != null) {
            try {
                categoryParser.getTaskHistory().setState(dictionaryService.findDictionaryValueById(TaskHistory.START));
                categoryParser.getTaskHistory().setState(dictionaryService.findDictionaryValueById(TaskHistory.IN_PROCESS));
                categoryParser.start();
                categoryParser.getTaskHistory().setState(dictionaryService.findDictionaryValueById(TaskHistory.COMPLETE));
            } catch (Exception e) {
                String ex = GrabberException.format(e);
                logger.error(ex);
                categoryParser.getTaskHistory().setState(dictionaryService.findDictionaryValueById(TaskHistory.FAILED));
                categoryParser.getTaskHistory().setMessage(ex);
            } finally {
                categoryParser.getTaskHistory().setEndDate(new Date());
                categoryParser.finish();
                grabberService.saveTaskHistory(categoryParser.getTaskHistory());
            }
        } else {
            logger.error("no isset task {}", id);
            return null;
        }
        return categoryParser.getTaskHistory();
    }
}
