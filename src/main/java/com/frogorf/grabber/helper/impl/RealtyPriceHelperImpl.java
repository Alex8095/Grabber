package com.frogorf.grabber.helper.impl;

import com.frogorf.dictionary.domain.DictionaryValue;
import com.frogorf.dictionary.service.DictionaryService;
import com.frogorf.grabber.helper.RealtyPriceHelper;
import com.frogorf.grabber.parser.ItemParser;
import com.frogorf.realty.domain.Realty;
import com.frogorf.realty.domain.RealtyHistoryPrice;
import com.frogorf.utils.Translit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by Alex on 16.12.14.
 */
@Service("realtyPriceHelperService")
public class RealtyPriceHelperImpl implements RealtyPriceHelper {

    @Autowired
    private DictionaryService dictionaryService;

    private Long price;
    private Realty realty;
    private RealtyHistoryPrice realtyHistoryPrice;
    private DictionaryValue currencyDictionaryValue;
    private int status = ItemParser.NEW;

    private String priceSource;
    private List<RealtyHistoryPrice> realtyHistoryPrices;

    @Override
    public Realty getRealty() {
        return realty;
    }

    @Override
    public void init(String priceSource, Realty realty) {
        clean();
        this.priceSource = priceSource;
        this.realty = realty;
    }

    @Override
    public Long getPrice() {
        if (price == null) {
            price = getPriceValue();
        }
        if (realty.getPrice() != null) {
            if (!realty.getPrice().equals(price)) {
                createRealtyHistoryPrice();
                status = ItemParser.UPDATE;
            }
        } else {
            createRealtyHistoryPrice();
        }
        return price;
    }

    private void createRealtyHistoryPrice() {
        realtyHistoryPrice = new RealtyHistoryPrice();
        realtyHistoryPrice.setPrice(price);
        realtyHistoryPrice.setCurrency(getCurrencyDictionaryValue());
        realtyHistoryPrice.setRealty(realty);
        realtyHistoryPrice.setDateAction(new Date());
    }

    @Override
    public RealtyHistoryPrice getRealtyHistoryPrice() {
        return realtyHistoryPrice;
    }

    @Override
    public List<RealtyHistoryPrice> getRealtyHistoryPrices() {
        if (realty.getRealtyHistoryPrices() == null) {
            realtyHistoryPrices = new ArrayList<>();
        } else {
            realtyHistoryPrices = realty.getRealtyHistoryPrices();
        }
        realtyHistoryPrices.add(getRealtyHistoryPrice());
        return realtyHistoryPrices;
    }

    @Override
    public DictionaryValue getCurrencyDictionaryValue() {
        String currencyValue = getCurrencyValue();
        if (currencyDictionaryValue == null && currencyValue != null && !currencyValue.equals("")) {
            Map<String, String> params = new HashMap<>();
            params.put(DictionaryValue.PARAM_CODE, Translit.toTranslit(currencyValue));
            currencyDictionaryValue = dictionaryService.findDictionaryValue(params);
        }
        return currencyDictionaryValue;
    }

    @Override
    public int getParserStatus() {
        return status;
    }

    @Override
    public Long getPriceValue() {
        if (priceSource != null) {
            return Long.valueOf(priceSource.substring(0, priceSource.lastIndexOf(" ")).replace(" ", ""));
        }
        return null;
    }

    @Override
    public String getCurrencyValue() {
        if (priceSource != null) {
            return priceSource.substring(priceSource.lastIndexOf(" ") + 1, priceSource.length());
        }
        return null;
    }

    @Override
    public void clean() {
        price = null;
        priceSource = null;
        realty = null;
        status = ItemParser.NEW;
        currencyDictionaryValue = null;
        realtyHistoryPrice = null;
        realtyHistoryPrices = null;
    }
}
