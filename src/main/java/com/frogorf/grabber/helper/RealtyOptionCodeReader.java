package com.frogorf.grabber.helper;

import java.util.Map;

/**
 * Created by Alex on 28.01.15.
 */
public interface RealtyOptionCodeReader {
    public void readFile();

    Map<String, String[]> getCodes();

    public String[] getItem(String realtyOptionCode);

}
