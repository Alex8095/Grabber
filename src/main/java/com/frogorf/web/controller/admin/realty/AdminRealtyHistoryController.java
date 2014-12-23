package com.frogorf.web.controller.admin.realty;

import com.frogorf.kendo.data.source.DataSourceRequest;
import com.frogorf.kendo.data.source.DataSourceResult;
import com.frogorf.realty.domain.RealtyHistory;
import com.frogorf.realty.service.RealtyService;
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
@SessionAttributes(types = {RealtyHistory.class})
@Controller
@RequestMapping("/admin/realty/")
public class AdminRealtyHistoryController {
    private static final Logger logger = LoggerFactory.getLogger(AdminRealtyHistoryController.class);

    @Autowired
    private RealtyService realtyService;

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @RequestMapping(value = "/history/grid", method = {RequestMethod.GET, RequestMethod.POST})
    public
    @ResponseBody
    DataSourceResult grid(DataSourceRequest request) {
        logger.info("grid call");
        return realtyService.getListRealtyHistory(request);
    }

    @RequestMapping(value = {"/history/list", "/history"}, method = RequestMethod.GET)
    public String list(Model model) {
        logger.info("list call");
        return "admin/realty/history/list";
    }

    @RequestMapping(value = "/history/{id}", method = RequestMethod.GET)
    public String detail(@PathVariable("id") int id, Model model) {
        logger.info("detail call");
        model.addAttribute("realtyHistory", realtyService.findRealtyHistoryById(id));
        return "admin/realty/history/detail";
    }
}
