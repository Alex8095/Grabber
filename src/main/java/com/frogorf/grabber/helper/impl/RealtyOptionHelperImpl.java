package com.frogorf.grabber.helper.impl;

import com.frogorf.dictionary.domain.Dictionary;
import com.frogorf.dictionary.domain.DictionaryValue;
import com.frogorf.dictionary.service.DictionaryService;
import com.frogorf.grabber.helper.RealtyOptionCodeReader;
import com.frogorf.grabber.helper.RealtyOptionHelper;
import com.frogorf.grabber.helper.selector.OptionSelector;
import com.frogorf.grabber.parser.ItemParser;
import com.frogorf.realty.domain.Realty;
import com.frogorf.realty.domain.RealtyOption;
import com.frogorf.realty.domain.RealtyOptionValue;
import com.frogorf.realty.service.RealtyService;
import com.frogorf.utils.Translit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Alex on 16.12.14.
 */
@Service("realtyOptionHelperService")
public class RealtyOptionHelperImpl implements RealtyOptionHelper {

    @Autowired
    private RealtyService realtyService;
    @Autowired
    private DictionaryService dictionaryService;
    @Autowired
    private RealtyOptionCodeReader realtyOptionCodeReader;

    private Map<String, String> optionSource;
    private Realty realty;
    private List<RealtyOptionValue> realtyOptionValues;
    private int status = ItemParser.NEW;

    public Realty getRealty() {
        return realty;
    }

    public Map<String, String> getOptionSource() {
        return optionSource;
    }

    @Override
    public int getParserStatus() {
        return status;
    }

    @Override
    public Double getRealtyTotalSpace() {
        String v = getRealtyOptionValueByCodeSelector(realtyOptionCodeReader.getItem(OptionSelector.TOTAL_SPACE));
        return v != null ? Double.valueOf(v) : null;
    }

    @Override
    public Double getRealtyLivingSpace() {
        String v = getRealtyOptionValueByCodeSelector(realtyOptionCodeReader.getItem(OptionSelector.LIVING_SPACE));
        return v != null ? Double.valueOf(v) : null;
    }

    @Override
    public Double getRealtyKitchenSpace() {
        String v = getRealtyOptionValueByCodeSelector(realtyOptionCodeReader.getItem(OptionSelector.KITCHEN_SPACE));
        return v != null ? Double.valueOf(v) : null;
    }

    @Override
    public Integer getRealtyCountRooms() {
        String v = getRealtyOptionValueByCodeSelector(realtyOptionCodeReader.getItem(OptionSelector.COUNT_ROOMS));
        return v != null ? Integer.valueOf(v) : null;
    }

    @Override
    public Integer getRealtyFloor() {
        String v = getRealtyOptionValueByCodeSelector(realtyOptionCodeReader.getItem(OptionSelector.FLOOR));
        return v != null ? Integer.valueOf(v) : null;
    }

    @Override
    public Integer getRealtyFloors() {
        String v = getRealtyOptionValueByCodeSelector(realtyOptionCodeReader.getItem(OptionSelector.FLOORS));
        return v != null ? Integer.valueOf(v) : null;
    }

    @Override
    public String getRealtyOptionValueByCodeSelector(String[] args) {
        if (realtyOptionValues != null) {
            for (String s : args) {
                RealtyOptionValue value = getRealtyOptionValue(s);
                if (value != null) {
                    return value.getValue();
                }
            }
        }
        return null;
    }

    @Override
    public RealtyOptionValue getRealtyOptionValue(String code) {
        if (realtyOptionValues != null) {
            for (RealtyOptionValue item : realtyOptionValues) {
                if (item.getRealtyOption() != null) {
                    if (item.getRealtyOption().getCode().equals(code.toUpperCase())) {
                        return item;
                    }
                }
            }
        }
        return null;
    }

    @Override
    public void init(Map<String, String> optionSource, Realty realty) {
        clean();
        this.optionSource = optionSource;
        this.realty = realty;
    }

    @Override
    public List<RealtyOptionValue> getResult() {
        realtyOptionValues = new ArrayList<>();
        if (realty.getRealtyOptionValues() == null) {
            for (Map.Entry<String, String> entry : optionSource.entrySet()) {
                realtyOptionValues.add(getRealtyOptionValue(entry.getKey(), entry.getValue()));
            }
        } else {
            status = ItemParser.UPDATE;
            for (Map.Entry<String, String> entry : optionSource.entrySet()) {
                RealtyOptionValue realtyOptionValue = getRealtyOptionValue(entry.getKey(), entry.getValue());
                if (isCanAddedRealtyOptionValueToList(realtyOptionValue)) {
                    realtyOptionValues.add(realtyOptionValue);
                }
            }
        }
        return realtyOptionValues;
    }

    @Override
    public RealtyOptionValue getRealtyOptionValue(String optionSourceName, String optionSourceValue) {
        RealtyOptionValue realtyOptionValue = new RealtyOptionValue();
        realtyOptionValue.setRealty(realty);
        realtyOptionValue.setRealtyOption(findRealtyOption(optionSourceName, optionSourceValue));
        DictionaryValue dictionaryValue = null;
        if (!isIntegerRealtyOptionValue(optionSourceValue)) {
            dictionaryValue = findDictionaryValue(optionSourceValue);
            if (dictionaryValue == null && realtyOptionValue.getRealtyOption().getDictionary() != null) {
                dictionaryValue = createDictionaryValue(realtyOptionValue.getRealtyOption().getDictionary(), optionSourceValue);
            }
            realtyOptionValue.setDictionaryValue(dictionaryValue);
        }
        if (realtyOptionValue.getDictionaryValue() == null) {
            realtyOptionValue.setValue(getRealtyOptionValueValue(optionSourceValue));
        }
        return realtyOptionValue;
    }

    private DictionaryValue createDictionaryValue(Dictionary dictionary, String optionSourceValue) {
        DictionaryValue dictionaryValue = new DictionaryValue();
        dictionaryValue.setCode(Translit.toTranslit(optionSourceValue));
        dictionaryValue.setDictionary(dictionary);
        dictionaryValue.setName(optionSourceValue);
        dictionaryService.saveDictionaryValue(dictionaryValue);
        return dictionaryValue;
    }

    @Override
    public Boolean isIntegerRealtyOptionValue(String optionSourceValue) {
        return (getIntegerValueFromString(optionSourceValue) != null);
    }

    @Override
    public String getRealtyOptionValueValue(String optionSourceValue) {
        if (isIntegerRealtyOptionValue(optionSourceValue)) {
            int spacePos = optionSourceValue.indexOf(" ");
            if (spacePos != -1) {
                return optionSourceValue.substring(0, spacePos);
            }
        }
        return optionSourceValue;
    }

    @Override
    public Integer getIntegerValueFromString(String optionSourceValue) {
        Integer res = null;
        int spacePos = optionSourceValue.indexOf(" ");
        String testValue = optionSourceValue;
        if (spacePos != -1) {
            testValue = optionSourceValue.substring(0, spacePos);
        }
        try {
            res = Integer.parseInt(testValue);
        } catch (NumberFormatException e) {
            return null;
        }
        return res;
    }

    @Override
    public String getRealtyOptionAfterValue(String optionSourceValue) {
        if (isIntegerRealtyOptionValue(optionSourceValue)) {
            int spacePos = optionSourceValue.indexOf(" ");
            if (spacePos != -1) {
                return optionSourceValue.substring(spacePos + 1, optionSourceValue.length());
            }
        }
        return null;
    }

    @Override
    public DictionaryValue findDictionaryValue(String optionSourceValue) {
        Map<String, String> params = new HashMap<>();
        params.put(DictionaryValue.PARAM_CODE, Translit.toTranslit(optionSourceValue));
        return dictionaryService.findDictionaryValue(params);
    }

    @Override
    public Boolean isCanAddedRealtyOptionValueToList(RealtyOptionValue realtyOptionValue) {
        for (RealtyOptionValue optionValue : realty.getRealtyOptionValues()) {
            if (optionValue.getValue().equals(realtyOptionValue.getValue()) && optionValue.getRealtyOption().getCode().equals(realtyOptionValue.getRealtyOption().getCode())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public RealtyOption findRealtyOption(String optionSourceName, String optionSourceValue) {
        optionSourceName = optionSourceName.replace(":", "");
        Map<String, String> params = new HashMap<>();
        params.put(RealtyOption.PARAM_CODE, Translit.toTranslit(optionSourceName));
        RealtyOption realtyOption = realtyService.findRealtyOption(params);
        if (realtyOption == null) {
            realtyOption = new RealtyOption();
            realtyOption.setCode(params.get(RealtyOption.PARAM_CODE));
            realtyOption.setName(optionSourceName);
            Dictionary dictionary = dictionaryService.findDictionaryByCode(Translit.toTranslit(optionSourceName));
            if (!isIntegerRealtyOptionValue(optionSourceValue)) {
                dictionary = createDictionary(optionSourceName);
            }
            realtyOption.setDictionary(dictionary);
            realtyOption.setImplementationType(realty.getImplementationType());
            realtyOption.setAfterValue(getRealtyOptionAfterValue(optionSourceValue));
            saveRealtyOption(realtyOption);
        }
        return realtyOption;
    }

    @Transactional
    private Dictionary createDictionary(String optionSourceName) {
        Dictionary dictionary = new Dictionary();
        dictionary.setName(optionSourceName);
        dictionary.setCode(Translit.toTranslit(optionSourceName));
        dictionary.setDictionary(dictionaryService.findDictionaryById(OptionSelector.PARENT_DICTIONARY_ID));
        dictionaryService.saveDictionary(dictionary);
        return dictionary;
    }

    @Override
    public Integer getRealtyOptionTypeDesign(Boolean isIntegerValue, DictionaryValue dictionaryValue) {
        return null;
    }

    @Override
    @Transactional
    public void saveRealtyOption(RealtyOption realtyOption) {
        realtyService.saveRealtyOption(realtyOption);
    }

    @Override
    public void clean() {
        realty = null;
        optionSource = null;
        realtyOptionValues = null;
        status = ItemParser.NEW;
    }
}
