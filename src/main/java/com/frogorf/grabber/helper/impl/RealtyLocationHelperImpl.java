package com.frogorf.grabber.helper.impl;

import com.frogorf.core.location.domain.Location;
import com.frogorf.dictionary.domain.Dictionary;
import com.frogorf.dictionary.domain.DictionaryValue;
import com.frogorf.dictionary.service.DictionaryService;
import com.frogorf.grabber.helper.RealtyLocationHelper;
import com.frogorf.grabber.helper.selector.LocationSelector;
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

    private String sourceLocation;

    private Map<Integer, String> locationMap;
    private Location location;
    private DictionaryValue streetD;
    private DictionaryValue cityDictrictD;

    @Override
    public DictionaryValue getRegion() {
        if (locationMap.containsKey(LocationSelector.REGION)) {
            return getDictionaryValue(locationMap.get(LocationSelector.REGION), LocationSelector.REGION_D_ID);
        }
        return null;
    }

    @Override
    public DictionaryValue getDistrict() {
        if (locationMap.containsKey(LocationSelector.DISTRICT)) {
            return getDictionaryValue(locationMap.get(LocationSelector.DISTRICT), LocationSelector.DISTRICT_D_ID);
        }
        return null;
    }

    @Override
    public DictionaryValue getCity() {
        if (locationMap.containsKey(LocationSelector.CITY)) {
            return getDictionaryValue(locationMap.get(LocationSelector.CITY), LocationSelector.CITY_D_ID);
        }
        return null;
    }

    @Override
    public DictionaryValue getCityArea() {
        if (locationMap.containsKey(LocationSelector.CITY_AREA)) {
            return getDictionaryValue(locationMap.get(LocationSelector.CITY_AREA), LocationSelector.CITY_AREA_D_ID);
        }
        return null;
    }

    @Override
    public DictionaryValue getCityDistrict() {
        if (cityDictrictD == null && locationMap.containsKey(LocationSelector.CITY_DISTRICT)) {
            String cd = locationMap.get(LocationSelector.CITY_DISTRICT);
            cityDictrictD = getDictionaryValue(cd, LocationSelector.CITY_DISTRICT_D_ID);
        }
        return cityDictrictD;
    }

    @Override
    public DictionaryValue getStreet() {
        if (cityDictrictD == null && streetD == null && locationMap.containsKey(LocationSelector.STREET)) {
            String s = locationMap.get(LocationSelector.STREET);
            streetD = getDictionaryValue(s, LocationSelector.STREET_D_ID);
        }
        return streetD;
    }

    @Override
    public String getHouseNumber() {
        if (locationMap.containsKey(LocationSelector.HOUSE_NUMBER)) {
            String hn = locationMap.get(LocationSelector.HOUSE_NUMBER);
            return hn;
        }
        return null;
    }

    @Override
    public Location getLocation() {
        return location;
    }

    @Override
    public Map<Integer, String> getLocationMap() {
        return locationMap;
    }

    @Override
    public void createLocationMap() {
        locationMap = new HashMap<>();
        String[] words = sourceLocation.replace(" , ", ",").split(",");
        switch (words.length) {
            case 1: {
                locationMap.put(LocationSelector.CITY, words[0]);
                break;
            }
            case 2: {
                locationMap.put(LocationSelector.CITY, words[0]);
                locationMap.put(LocationSelector.REGION, words[1]);
                break;
            }
            case 3: {
                locationMap.put(LocationSelector.CITY, words[0]);
                locationMap.put(LocationSelector.REGION, words[1]);
                locationMap.put(LocationSelector.CITY_AREA, words[2]);
                break;
            }
            case 4:
            case 5: {
                locationMap.put(LocationSelector.CITY, words[0]);
                locationMap.put(LocationSelector.REGION, words[1]);
                locationMap.put(LocationSelector.CITY_AREA, words[2]);
                locationMap.put(LocationSelector.STREET, words[4]);
                locationMap.put(LocationSelector.CITY_DISTRICT, words[4]);
                locationMap.put(LocationSelector.HOUSE_NUMBER, words[4]);
                break;
            }
        }
    }

    @Override
    public Location get(String sourceLocation) {
        this.sourceLocation = sourceLocation;
        location = new Location();
        if (sourceLocation != null && !sourceLocation.equals("")) {
            location.setRegion(getRegion());
            location.setDistrict(getDistrict());
            location.setCity(getCity());
            location.setAreaCity(getCityArea());
            location.setDistrictCity(getCityDistrict());
            location.setStreet(getStreet());
            location.setSiteLocation(sourceLocation);
        }
        return location;
    }

    @Override
    public DictionaryValue getDictionaryValue(String code, Integer dictionaryId) {
        Map<String, String> params = new HashMap<>();
        params.put("code", code);
        params.put("dictionary_id", Integer.toString(dictionaryId));
        return dictionaryService.findDictionaryValue(params);
    }

    @Override
    public void clean() {
        location = null;
        locationMap = null;
        streetD = null;
        cityDictrictD = null;
    }
}
