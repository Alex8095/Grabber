/**
 *
 */
package com.frogorf.dictionary.dao;

import com.frogorf.dictionary.domain.Dictionary;
import com.frogorf.dictionary.domain.DictionaryValue;
import com.frogorf.kendo.data.source.DataSourceRequest;
import com.frogorf.kendo.data.source.DataSourceResult;

import java.util.List;
import java.util.Map;

/**
 * @author Tsurkin Alex
 */
public interface DictionaryDao {

    /* Dictionary */
    public List<Dictionary> findDictionaryAll();

    public Dictionary findDictionaryById(int id);

    public Dictionary findDictionaryByCode(String code);

    public void saveDictionary(Dictionary dictionary);

    public void deleteDictionary(int id);

    DataSourceResult getDictionaryList(DataSourceRequest request);

    /* DictionaryValue */
    public List<DictionaryValue> findDictionaryValueAll();

    public List<DictionaryValue> findDictionaryValueAllByDictionary(Dictionary dictionary);

    public List<DictionaryValue> findDictionaryValueAllByDictionaryId(int dictionaryId);

    public List<DictionaryValue> findDictionaryValues(Map<String, String> params);

    public List<DictionaryValue> findDictionaryValuesByCode(String code);

    public DictionaryValue findDictionaryValueById(int id);

    public void saveDictionaryValue(DictionaryValue dictionaryValue);

    public void deleteDictionaryValue(int id);

    DataSourceResult getDictionaryValueList(DataSourceRequest request);

    DictionaryValue findDictionaryValue(Map<String, String> params);

    void saveDictionaryValueList(List<DictionaryValue> list);
}
