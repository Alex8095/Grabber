package com.frogorf.grabber.helper.impl;

import com.frogorf.dictionary.domain.DictionaryValue;
import com.frogorf.dictionary.service.DictionaryService;
import com.frogorf.grabber.helper.RealtyHelper;
import com.frogorf.grabber.helper.selector.RealtySelector;
import com.frogorf.grabber.parser.ItemParser;
import com.frogorf.realty.domain.Realty;
import com.frogorf.realty.service.RealtyService;
import com.frogorf.utils.text.DateFormatSymbolsRu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Created by Alex on 23.12.14.
 */
@Service("realtyHelperService")
public class RealtyHelperImpl implements RealtyHelper {
    private int parserStatus = ItemParser.NEW;
    private Realty realty;

    @Autowired
    private DictionaryService dictionaryService;
    @Autowired
    private RealtyService realtyService;

    @Override
    public void init(Realty realty) {
        clean();
        this.realty = realty;
    }

    @Override
    public void clean() {
        parserStatus = ItemParser.NEW;
        realty = null;
    }

    @Override
    public int setParserStatus(int newStatus) {
        if (newStatus != ItemParser.NEW) {
            parserStatus = newStatus;
        }
        return parserStatus;
    }

    @Override
    public int getParserStatus() {
        return parserStatus;
    }

    @Override
    public DictionaryValue getParseOwner(String... collocations) {
        if (parseOwner(collocations)) {
            return dictionaryService.findDictionaryValueById(RealtySelector.OWNER_DV_ID);
        }
        return null;
    }

    @Override
    public Boolean parseOwner(String... collocations) {
        for (String collocation : collocations) {
            for (String o : RealtySelector.OWNER_WORDS_ARRAY) {
                if (collocation.toUpperCase().indexOf(o) != -1) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Date getParseDatePublishing(String dateSource) {
        if (dateSource != null) {
            String ds = dateSource.substring(dateSource.indexOf(",") + 2, dateSource.lastIndexOf(","));
            SimpleDateFormat formatter = new SimpleDateFormat(RealtySelector.DATE_FORMAT, new DateFormatSymbolsRu());
            try {
                return formatter.parse(ds);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public Realty getRealty() {
        return realty;
    }

    @Override
    public Realty findRealtyOr(Map<String, String> params) {
        Realty searchRealty = realtyService.findRealtyOr(params);
        if (searchRealty != null) {
            realty = searchRealty;
            parserStatus = ItemParser.UPDATE;
        }
        return realty;
    }
}
