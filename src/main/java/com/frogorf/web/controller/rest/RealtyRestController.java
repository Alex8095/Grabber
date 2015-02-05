package com.frogorf.web.controller.rest;

import com.frogorf.kendo.data.source.DataSourceRequest;
import com.frogorf.kendo.data.source.DataSourceResult;
import com.frogorf.realty.domain.Realty;
import com.frogorf.realty.domain.RealtyOption;
import com.frogorf.realty.service.RealtyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * Created by Alex on 06.11.14.
 */
@SessionAttributes(types = {Realty.class})
@Controller
@RequestMapping("/realtyRest/")
public class RealtyRestController {

    private static final Logger logger = LoggerFactory.getLogger(RealtyRestController.class);

    @Autowired
    private RealtyService realtyService;

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @RequestMapping(value = "grid", method = {RequestMethod.GET, RequestMethod.POST})
    public
    @ResponseBody
    DataSourceResult getRealtyList(@RequestBody DataSourceRequest request) {
        logger.info("grid call");
        return realtyService.getListRealty(request);
    }

    @RequestMapping(value = "item/{id}", method = RequestMethod.GET)
    public
    @ResponseBody
    Realty getRealty(@PathVariable("id") int id) {
        logger.info("detail call");
        Realty realty = realtyService.findRealtyById(id);
        return realty;
    }

    @RequestMapping(value = "realty/{id}", method = RequestMethod.DELETE)
    public void deleteRealty(@PathVariable("id") int id) {
        realtyService.deleteRealty(id);
    }

    @RequestMapping(value = "realty/{id}/{code}", method = RequestMethod.GET)
    public
    @ResponseBody
    Realty updateRealtyCode(@PathVariable("id") int id, @PathVariable("code") String code) {
        logger.info("updateCode call");
        Realty realty = realtyService.findRealtyById(id);
        realty.setMainSiteCode(code);
        realty.setDateUpToMainSite(new Date());
        realtyService.saveRealty(realty);
        return realty;
    }

    @RequestMapping(value = "realtyOptions", method = RequestMethod.GET)
    public
    @ResponseBody
    List<RealtyOption> getRealtyOptions() {
        logger.info("options call");
        return realtyService.findRealtyOptionAll();
    }
}
