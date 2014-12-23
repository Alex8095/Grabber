package com.frogorf.grabber.helper;

import com.frogorf.core.location.domain.Location;
import com.frogorf.dictionary.domain.DictionaryValue;
import com.frogorf.realty.domain.Realty;

import java.util.Map;

/**
 * Created by Alex on 16.12.14.
 */
public interface RealtyLocationHelper {

    Location getLocation();

    Map<Integer, String> getLocationMap();

    String formatRegion(String value);

    String formatDistrict(String value);

    String formatCity(String value);

    String formatCityArea(String value);

    String formatCityDistrict(String value);

    String formatStreet(String value);

    String formatHouseNumber(String value);

    void putLocationMapEntry(int LocationSelectorId, String value);

    DictionaryValue findLocationDictionaryValue(String value, Integer dictionaryId);

    String getHouseNumber();

    void createLocationMap();

    void init(String locationSource, Realty realty);

    void clean();
}
