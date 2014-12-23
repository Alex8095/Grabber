package com.frogorf.grabber.helper.impl;

import com.frogorf.core.location.domain.Location;
import com.frogorf.dictionary.domain.DictionaryValue;
import com.frogorf.dictionary.service.DictionaryService;
import com.frogorf.grabber.helper.RealtyLocationHelper;
import com.frogorf.grabber.helper.selector.LocationSelector;
import com.frogorf.realty.domain.Realty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Alex on 16.12.14.
 */
@Service("realtyLocationHelperService")
public class RealtyLocationHelperImpl implements RealtyLocationHelper {

    @Autowired
    private DictionaryService dictionaryService;

    private String locationSource;

    private Map<Integer, String> locationMap;
    private Location location;
    private Realty realty;
    private String houseNumber;

    @Override
    public Location getLocation() {
        return location;
    }

    @Override
    public Map<Integer, String> getLocationMap() {
        return locationMap;
    }

    @Override
    public String formatRegion(String value) {
        return value;
    }

    @Override
    public String formatDistrict(String value) {
        return value;
    }

    @Override
    public String formatCity(String value) {
        return value;
    }

    @Override
    public String formatCityArea(String value) {
        return value;
    }

    @Override
    public String formatCityDistrict(String value) {
        if (formatHouseNumber(value) != null) {
            return null;
        }
        return value;
    }

    @Override
    public String formatStreet(String value) {
        if (formatHouseNumber(value) != null) {
            return value.substring(0, value.lastIndexOf(" "));
        }
        return value;
    }

    @Override
    public String formatHouseNumber(String value) {
        if (houseNumber != null) {
            return houseNumber;
        }
        if (value != null) {
            Integer res = null;
            int spaceLastPos = value.lastIndexOf(" ");
            if (spaceLastPos != -1) {
                try {
                    res = Integer.parseInt(value.substring(spaceLastPos + 1, value.length()));
                } catch (NumberFormatException e) {
                    houseNumber = null;
                }
                houseNumber = res.toString();
            }
        }
        return houseNumber;
    }

    @Override
    public void putLocationMapEntry(int locationSelectorId, String value) {
        if (value != null && !locationMap.containsKey(locationSelectorId)) {
            locationMap.put(locationSelectorId, value);
        }
    }

    @Override
    public DictionaryValue findLocationDictionaryValue(String value, Integer dictionaryId) {
        return null;
    }

    @Override
    public DictionaryValue createDictionaryValue(String value, Integer dictionaryId) {
        return null;
    }

    @Override
    public String getHouseNumber() {
        return houseNumber;
    }

    @Override
    public void createLocationMap() {
        locationMap = new HashMap<>();
        String[] words = locationSource.replace(" , ", ",").split(",");
        switch (words.length) {
            case 1: {
                putLocationMapEntry(LocationSelector.CITY, formatCity(words[0]));
                break;
            }
            case 2: {
                putLocationMapEntry(LocationSelector.CITY, formatCity(words[0]));
                putLocationMapEntry(LocationSelector.REGION, formatRegion(words[1]));
                break;
            }
            case 3: {
                putLocationMapEntry(LocationSelector.CITY, formatCity(words[0]));
                putLocationMapEntry(LocationSelector.REGION, formatRegion(words[1]));
                putLocationMapEntry(LocationSelector.CITY_AREA, formatCityArea(words[2]));
                break;
            }
            case 4:
            case 5: {
                putLocationMapEntry(LocationSelector.CITY, formatCity(words[0]));
                putLocationMapEntry(LocationSelector.REGION, formatRegion(words[1]));
                putLocationMapEntry(LocationSelector.CITY_AREA, formatCityArea(words[2]));
                putLocationMapEntry(LocationSelector.STREET, formatStreet(words[3]));
                putLocationMapEntry(LocationSelector.CITY_DISTRICT, formatCityDistrict(words[3]));
                putLocationMapEntry(LocationSelector.HOUSE_NUMBER, formatHouseNumber(words[3]));
                break;
            }
        }
    }

    @Override
    public void init(String locationSource, Realty realty) {
        clean();
        this.locationSource = locationSource;
        this.realty = realty;
    }

    @Override
    public void clean() {
        locationSource = null;
        locationMap = null;
        location = null;
        realty = null;
        houseNumber = null;
    }
}