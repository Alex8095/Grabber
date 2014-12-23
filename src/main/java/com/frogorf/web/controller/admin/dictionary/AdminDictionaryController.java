package com.frogorf.web.controller.admin.dictionary;

import com.frogorf.dictionary.domain.Dictionary;
import com.frogorf.dictionary.domain.DictionaryValue;
import com.frogorf.dictionary.domain.DictionaryValueLocale;
import com.frogorf.dictionary.service.DictionaryService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Alex on 06.11.14.
 */
@SessionAttributes(types = {Dictionary.class, DictionaryValue.class})
@Controller
@RequestMapping("/admin")
public class AdminDictionaryController {
    private static final Logger logger = LoggerFactory.getLogger(AdminDictionaryController.class);

    @Autowired
    private DictionaryService dictionaryService;

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    //Dictionary

    @RequestMapping(value = "/dictionary/grid", method = {RequestMethod.GET, RequestMethod.POST})
    public
    @ResponseBody
    DataSourceResult grid(DataSourceRequest request) {
        logger.info("grid call");
        return dictionaryService.getDictionaryList(request);
    }

    @RequestMapping(value = "/dictionary/list/json", method = {RequestMethod.GET})
    public
    @ResponseBody
    List<Dictionary> listJson() {
        logger.info("listJson call");
        return dictionaryService.findDictionaryAll();
    }

    @RequestMapping(value = {"/dictionary/list", "/dictionary"}, method = RequestMethod.GET)
    public String listDictionary(Model model) {
        logger.info("listDictionary call");
        return "admin/dictionary/list";
    }

    @RequestMapping(value = "/dictionary/{id}", method = RequestMethod.GET)
    public String detailDictionary(@PathVariable("id") int id, Model model) {
        logger.info("detailDictionary call");
        model.addAttribute("dictionary", dictionaryService.findDictionaryById(id));
        return "admin/dictionary/detail";
    }

    @RequestMapping(value = "/dictionary/new", method = RequestMethod.GET)
    public String newDictionary(Model model) {
        logger.info("newDictionary call");
        Dictionary dictionary = new Dictionary();
        model.addAttribute("dictionary", dictionary);
        return "admin/dictionary/edit";
    }

    @RequestMapping(value = "/dictionary/new", method = RequestMethod.POST)
    public String createDictionary(@ModelAttribute @Valid Dictionary dictionary, BindingResult result, SessionStatus status) {
        logger.info("createDictionary call");
        if (result.hasErrors()) {
            logger.info(dictionary.getName());
            return "admin/dictionary/edit";
        } else {
            dictionaryService.saveDictionary(dictionary);
            status.setComplete();
            return "redirect:/admin/dictionary/" + dictionary.getId();
        }
    }

    @RequestMapping(value = "/dictionary/{id}/edit", method = RequestMethod.GET)
    public String editDictionary(@PathVariable("id") int id, Model model) {
        logger.info("editDictionary call");
        model.addAttribute("dictionary", dictionaryService.findDictionaryById(id));
        return "admin/dictionary/edit";
    }

    @RequestMapping(value = "/dictionary/{id}/edit", method = RequestMethod.POST)
    public String updateDictionary(@ModelAttribute @Valid Dictionary dictionary, BindingResult result, SessionStatus status) {
        logger.info("updateDictionary call");
        if (result.hasErrors()) {
            return "admin/dictionary/edit";
        } else {
            dictionaryService.saveDictionary(dictionary);
            status.setComplete();
            return "redirect:/admin/dictionary/" + dictionary.getId();
        }
    }

    //Dictionary value

    @RequestMapping(value = "/dictionary/value/grid", method = {RequestMethod.GET, RequestMethod.POST})
    public
    @ResponseBody
    DataSourceResult gridValue(DataSourceRequest request) {
        logger.info("gridValue call");
        return dictionaryService.getDictionaryValueList(request);
    }

    @RequestMapping(value = "/dictionary/value/json", method = {RequestMethod.GET})
    public
    @ResponseBody
    List<DictionaryValue> listValueByCodeJson(@RequestParam(value="code", defaultValue = "") String code, @RequestParam(value="parent_id", defaultValue = "") String parent_id) {
        Map<String, String> params = new HashMap<>();
        if(!code.equals("")) {
            params.put("code", code);
        }
        if(!parent_id.equals("")) {
            params.put("parent_id", parent_id);
        }
        logger.info("listValueByCodeJson call");
        return dictionaryService.findDictionaryValues(params);
    }

    @RequestMapping(value = {"/dictionary/value"}, method = RequestMethod.GET)
    public String listDictionaryValue(Model model) {
        logger.info("listDictionaryValue call");
        return "admin/dictionary/value/list";
    }

    @RequestMapping(value = "/dictionary/value/{id}", method = RequestMethod.GET)
    public String detailDictionaryValue(@PathVariable("id") int id, Model model) {
        logger.info("detailDictionaryValue call");
        model.addAttribute("dictionaryValue", dictionaryService.findDictionaryValueById(id));
        return "admin/dictionary/value/detail";
    }

    @RequestMapping(value = "/dictionary/value/new", method = RequestMethod.GET)
    public String newDictionaryValue(Model model) {
        logger.info("newDictionaryValue call");
        DictionaryValue dictionaryValue = new DictionaryValue();
        Map<String, DictionaryValueLocale> locales = new HashMap<String, DictionaryValueLocale>();
        locales.put("ru", new DictionaryValueLocale());
        locales.put("en", new DictionaryValueLocale());
        dictionaryValue.setLocales(locales);
        model.addAttribute(dictionaryValue);
        model.addAttribute("dictionaries", dictionaryService.findDictionaryAll());
        model.addAttribute("dictionaryValues", dictionaryService.findDictionaryValueAll());
        return "admin/dictionary/value/edit";
    }

    @RequestMapping(value = "/dictionary/value/new", method = RequestMethod.POST)
    public String createDictionaryValue(@ModelAttribute @Valid DictionaryValue dictionaryValue, BindingResult result, SessionStatus status) {
        logger.info("createDictionaryValue call");
        if (result.hasErrors()) {
            logger.info("hasErrors");
            return "admin/dictionary/value/edit";
        } else {
            if (dictionaryValue.getParentDictionaryValue().getId() == null) {
                dictionaryValue.setParentDictionaryValue(null);
            }
            dictionaryService.saveDictionaryValue(dictionaryValue);
            status.setComplete();
            return "redirect:/admin/dictionary/value/" + dictionaryValue.getId();
        }
    }

    @RequestMapping(value = "/dictionary/value/{id}/edit", method = RequestMethod.GET)
    public String editDictionaryValue(@PathVariable("id") int id, Model model) {
        logger.info("editDictionaryValue call");
        model.addAttribute("dictionaryValue", this.dictionaryService.findDictionaryValueById(id));
        model.addAttribute("dictionaryValues", dictionaryService.findDictionaryValueAll());
        model.addAttribute("dictionaries", dictionaryService.findDictionaryAll());
        return "admin/dictionary/value/edit";
    }

    @RequestMapping(value = "/dictionary/value/{id}/edit", method = RequestMethod.POST)
    public String updateDictionaryValue(@ModelAttribute @Valid DictionaryValue dictionaryValue, BindingResult result, SessionStatus status) {
        logger.info("updateDictionaryValue call");
        if (result.hasErrors()) {
            if (dictionaryValue.getParentDictionaryValue().getId() == null) {
                dictionaryValue.setParentDictionaryValue(null);
            }
            return "admin/dictionary/value/edit";
        } else {
            dictionaryService.saveDictionaryValue(dictionaryValue);
            status.setComplete();
            return "redirect:/admin/dictionary/value/" + dictionaryValue.getId();
        }
    }
}
