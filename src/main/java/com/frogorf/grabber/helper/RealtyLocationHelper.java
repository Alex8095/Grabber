package com.frogorf.grabber.helper;

import com.frogorf.core.location.domain.Location;
import com.frogorf.dictionary.domain.Dictionary;
import com.frogorf.dictionary.domain.DictionaryValue;

import java.util.Map;

/**
 * Created by Alex on 16.12.14.
 */
public interface RealtyLocationHelper {

    DictionaryValue getRegion();

    DictionaryValue getDistrict();

    DictionaryValue getCity();

    DictionaryValue getCityArea();

    DictionaryValue getCityDistrict();

    DictionaryValue getStreet();

    String getHouseNumber();

    Location getLocation();

    Map<Integer, String> getLocationMap();

    void createLocationMap();

    Location get(String sourceLocation);

    DictionaryValue getDictionaryValue(String value, Integer dictionaryId);

    void clean();
}
