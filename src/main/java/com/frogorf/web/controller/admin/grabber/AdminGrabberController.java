package com.frogorf.web.controller.admin.grabber;

import com.frogorf.dictionary.service.DictionaryService;
import com.frogorf.grabber.domain.Task;
import com.frogorf.grabber.domain.TaskHistory;
import com.frogorf.grabber.parser.CategoryParser;
import com.frogorf.grabber.parser.impl.OlxRealtyCategoryParserImpl;
import com.frogorf.grabber.service.GrabberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

/**
 * Created by Alex on 06.11.14.
 */
@SessionAttributes(types = {TaskHistory.class})
@Controller
@RequestMapping("/admin/grabber/")
public class AdminGrabberController {
    private static final Logger logger = LoggerFactory.getLogger(AdminGrabberController.class);

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

    @RequestMapping(value = "/start", method = {RequestMethod.GET})
    public
    @ResponseBody
    TaskHistory listValueByCodeJson(@RequestParam(value = "task_id", defaultValue = "") Integer task_id) {
        categoryParser.initTask(task_id);
        categoryParser.getTaskHistory().setState(dictionaryService.findDictionaryValueById(TaskHistory.START));
        try {
            categoryParser.getTaskHistory().setState(dictionaryService.findDictionaryValueById(TaskHistory.IN_PROCESS));
            categoryParser.start();
            categoryParser.getTaskHistory().setState(dictionaryService.findDictionaryValueById(TaskHistory.COMPLETE));
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            categoryParser.getTaskHistory().setState(dictionaryService.findDictionaryValueById(TaskHistory.FAILED));
            categoryParser.getTaskHistory().setMessage(String.format("message: %s, stacktrace: %s", e.getMessage(), sw.toString()));
        } finally {
            categoryParser.getTaskHistory().setEndDate(new Date());
            grabberService.saveTaskHistory(categoryParser.getTaskHistory());
        }
        return categoryParser.getTaskHistory();
    }

}
