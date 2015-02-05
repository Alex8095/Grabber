package com.frogorf.web.controller.rest;

import com.frogorf.kendo.data.source.DataSourceRequest;
import com.frogorf.kendo.data.source.DataSourceResult;
import com.frogorf.realty.domain.Seller;
import com.frogorf.realty.service.SellerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Alex on 06.11.14.
 */
@SessionAttributes(types = {Seller.class})
@Controller
@RequestMapping("/sellerRest/")
public class SellerRestController {

    private static final Logger logger = LoggerFactory.getLogger(SellerRestController.class);

    @Autowired
    private SellerService sellerService;

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @RequestMapping(value = "grid", method = {RequestMethod.GET, RequestMethod.POST})
    public
    @ResponseBody
    DataSourceResult getSellerList(@RequestBody DataSourceRequest request) {
        logger.info("getSellerList {}", request);
        return sellerService.getDataSourceList(request);
    }

    @RequestMapping(value = "item/{id}", method = RequestMethod.GET)
    public
    @ResponseBody
    Seller getRealty(@PathVariable("id") int id) {
        logger.info("getRealty {}", id);
        return sellerService.findById(id);
    }

    @RequestMapping(value = "item/{id}", method = RequestMethod.DELETE)
    public void deleteSeller(@PathVariable("id") int id) {
        sellerService.delete(id);
    }

    @RequestMapping(value = "item/{id}", method = RequestMethod.POST)
    public
    @ResponseBody
    Seller updateSeller(@PathVariable("id") int id, @RequestBody Seller seller) {
        logger.info("updateSeller {} {}", id, seller);
        sellerService.save(seller);
        return seller;
    }
}
