package com.frogorf.web.controller.admin.dictionary;

import com.frogorf.dictionary.data.State;
import com.frogorf.dictionary.domain.DictionarySync;
import com.frogorf.dictionary.domain.DictionarySyncResponse;
import com.frogorf.dictionary.domain.DictionaryValue;
import com.frogorf.dictionary.service.DictionaryService;
import com.frogorf.dictionary.service.DictionarySyncService;
import com.frogorf.dictionary.service.DictionaryValueSynchronize;
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
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 06.11.14.
 */
@SessionAttributes(types = {DictionarySync.class})
@Controller
@RequestMapping("/admin/dictionary/")
public class AdminDictionarySyncController {
    private static final Logger logger = LoggerFactory.getLogger(AdminDictionarySyncController.class);

    @Autowired
    private DictionarySyncService dictionarySyncService;
    @Autowired
    private DictionaryService dictionaryService;
    @Autowired
    private DictionaryValueSynchronize dictionaryValueSynchronize;

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @RequestMapping(value = "/sync/grid", method = {RequestMethod.GET, RequestMethod.POST})
    public
    @ResponseBody
    DataSourceResult grid(DataSourceRequest request) {
        logger.info("grid call");
        return dictionarySyncService.getDictionarySyncList(request);
    }

    @RequestMapping(value = "/sync/list/json", method = {RequestMethod.GET})
    public
    @ResponseBody
    List<DictionarySync> listJson() {
        logger.info("listJson call");
        return dictionarySyncService.findDictionarySyncAll();
    }

    @RequestMapping(value = {"/sync/list", "/sync"}, method = RequestMethod.GET)
    public String listDictionarySync(Model model) {
        logger.info("listDictionarySync call");
        return "admin/dictionary/sync/list";
    }

    @RequestMapping(value = "/sync/{id}", method = RequestMethod.GET)
    public String detail(@PathVariable("id") int id, Model model) {
        logger.info("detail call");
        model.addAttribute("dictionarySync", dictionarySyncService.findDictionarySyncById(id));
        return "admin/dictionary/sync/detail";
    }

    @RequestMapping(value = "/sync/new", method = RequestMethod.GET)
    public String create(Model model) {
        logger.info("create call");
        DictionarySync dictionarySync = new DictionarySync();
        model.addAttribute("dictionarySync", dictionarySync);
        model.addAttribute("dictionaries", dictionaryService.findDictionaryAll());
        return "admin/dictionary/sync/edit";
    }

    @RequestMapping(value = "/sync/new", method = RequestMethod.POST)
    public String create(@ModelAttribute @Valid DictionarySync dictionarySync, BindingResult result, SessionStatus status) {
        logger.info("create call");
        if (result.hasErrors()) {
            return "admin/dictionary/sync/edit";
        } else {
            dictionarySyncService.saveDictionarySync(dictionarySync);
            status.setComplete();
            return "redirect:/admin/dictionary/sync/" + dictionarySync.getId();
        }
    }

    @RequestMapping(value = "/sync/{id}/edit", method = RequestMethod.GET)
    public String edit(@PathVariable("id") int id, Model model) {
        logger.info("edit call");
        model.addAttribute("dictionarySync", dictionarySyncService.findDictionarySyncById(id));
        model.addAttribute("dictionaries", dictionaryService.findDictionaryAll());
        return "admin/dictionary/sync/edit";
    }

    @RequestMapping(value = "/sync/{id}/edit", method = RequestMethod.POST)
    public String update(@ModelAttribute @Valid DictionarySync dictionarySync, BindingResult result, SessionStatus status) {
        logger.info("update call");
        if (result.hasErrors()) {
            return "admin/dictionary/sync/edit";
        } else {
            dictionarySyncService.saveDictionarySync(dictionarySync);
            status.setComplete();
            return "redirect:/admin/dictionary/sync/" + dictionarySync.getId();
        }
    }

    @RequestMapping(value = "/sync/execute", method = {RequestMethod.GET})
    public
    @ResponseBody
    DictionarySync executeDictionarySync(@RequestParam(value = "id") Integer id) {
        return executeDictionarySyncItem(id);
    }

    @RequestMapping(value = "/sync/executes", method = {RequestMethod.GET})
    public
    @ResponseBody
    List<DictionarySync> executeDictionarySync() {
        List<DictionarySync> syncs = dictionarySyncService.findDictionarySyncAll();
        List<DictionarySync> response = new ArrayList<>();
        for (DictionarySync dictionarySync : syncs) {
            response.add(executeDictionarySyncItem(dictionarySync.getId()));
        }
        return response;
    }

    private DictionarySync executeDictionarySyncItem(Integer id) {
        DictionarySync dictionarySync = null;
        dictionaryValueSynchronize.getDictionarySync(id);
        dictionarySync = dictionaryValueSynchronize.getDictionarySync();
        try {
            DictionarySyncResponse response = dictionaryValueSynchronize.execute();
            List<DictionaryValue> list = dictionaryValueSynchronize.parseList(response);
            for (DictionaryValue dv : list) {
                dictionaryService.saveDictionaryValue(dv);
            }
            dictionarySync.setState(dictionaryService.findDictionaryValueById(State.COMPLETE));
        } catch (Exception ex) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);
            dictionarySync.setState(dictionaryService.findDictionaryValueById(State.FAILED));
            dictionarySync.setMessage(String.format("message: %s, stacktrace: %s", ex.getMessage(), sw.toString()));
        } finally {
            dictionarySyncService.saveDictionarySync(dictionarySync);
        }
        return dictionarySync;
    }
}
