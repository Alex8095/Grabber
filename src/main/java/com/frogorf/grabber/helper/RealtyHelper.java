package com.frogorf.grabber.helper;

import com.frogorf.realty.domain.Realty;

/**
 * Created by Alex on 23.12.14.
 */
public interface RealtyHelper {
    void init(Realty realty);

    void clean();

    int setParserStatus(int newStatus);

    int getParserStatus();

    Realty getRealty();
}
