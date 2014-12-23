package com.frogorf.grabber.helper;

import com.frogorf.dictionary.domain.DictionaryValue;
import com.frogorf.realty.domain.Realty;
import com.frogorf.realty.domain.RealtyHistoryPrice;

import java.util.List;

/**
 * Created by Alex on 16.12.14.
 */
public interface RealtyPriceHelper {
    Realty getRealty();

    void init(String priceSource, Realty realty);

    Long getPrice();

    RealtyHistoryPrice getRealtyHistoryPrice();

    List<RealtyHistoryPrice> getRealtyHistoryPrices();

    DictionaryValue getCurrencyDictionaryValue();

    int getParserStatus();

    void clean();

    Long getPriceValue();

    String getCurrencyValue();
}
