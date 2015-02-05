package com.frogorf.grabber.helper;

import com.frogorf.dictionary.domain.DictionaryValue;
import com.frogorf.realty.domain.Realty;
import com.frogorf.realty.domain.RealtyOption;
import com.frogorf.realty.domain.RealtyOptionValue;

import java.util.List;
import java.util.Map;

/**
 * Created by Alex on 16.12.14.
 */
public interface RealtyOptionHelper {

    Realty getRealty();

    Map<String, String> getOptionSource();

    int getParserStatus();

    Double getRealtyTotalSpace();

    Double getRealtyLivingSpace();

    Double getRealtyKitchenSpace();

    Integer getRealtyCountRooms();

    Integer getRealtyFloor();

    Integer getRealtyFloors();

    String getRealtyOptionValueByCodeSelector(String[] args);

    RealtyOptionValue getRealtyOptionValue(String code);

    void init(Map<String, String> optionSource, Realty realty);

    List<RealtyOptionValue> getResult();

    RealtyOptionValue getRealtyOptionValue(String optionSourceName, String optionSourceValue);

    Boolean isIntegerRealtyOptionValue(String optionSourceValue);

    String getRealtyOptionValueValue(String optionSourceValue);

    Integer getIntegerValueFromString(String optionSourceValue);

    String getRealtyOptionAfterValue(String optionSourceValue);

    DictionaryValue findDictionaryValue(String optionSourceValue);

    Boolean isCanAddedRealtyOptionValueToList(RealtyOptionValue realtyOptionValue);

    //RealtyOptionMethods
    RealtyOption findRealtyOption(String optionSourceName, String optionSourceValue);

    Integer getRealtyOptionTypeDesign(Boolean isIntegerValue, DictionaryValue dictionaryValue);

    void saveRealtyOption(RealtyOption realtyOption);

    void clean();
}
