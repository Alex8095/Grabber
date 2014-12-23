package com.frogorf.grabber.helper;

import com.frogorf.dictionary.domain.DictionaryValue;
import com.frogorf.realty.domain.Realty;

import java.util.Date;
import java.util.Map;

/**
 * Created by Alex on 23.12.14.
 */
public interface RealtyHelper {
    void init(Realty realty);

    void clean();

    int setParserStatus(int newStatus);

    int getParserStatus();

    DictionaryValue getParseOwner(String... collocation);

    Boolean parseOwner(String... collocation);

    Date getParseDatePublishing(String dateSource);

    Realty getRealty();

    Realty findRealtyOr(Map<String, String> params);
}
