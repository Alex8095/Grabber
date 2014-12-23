package com.frogorf.grabber.helper.impl;

import com.frogorf.grabber.helper.RealtyHelper;
import com.frogorf.grabber.parser.ItemParser;
import com.frogorf.realty.domain.Realty;
import org.springframework.stereotype.Service;

/**
 * Created by Alex on 23.12.14.
 */
@Service("realtyHelperService")
public class RealtyHelperImpl implements RealtyHelper {
    private int parserStatus = ItemParser.NEW;
    private Realty realty;

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
    public Realty getRealty() {
        return realty;
    }
}
