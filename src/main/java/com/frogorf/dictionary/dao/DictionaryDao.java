/**
 *
 */
package com.frogorf.dictionary.dao;

import com.frogorf.dictionary.domain.Dictionary;
import com.frogorf.dictionary.domain.DictionaryValue;
import com.frogorf.kendo.data.source.DataSourceRequest;
import com.frogorf.kendo.data.source.DataSourceResult;

import java.util.List;

/**
 * @author Tsurkin Alex
 */
public interface DictionaryDao {

    /* Dictionary */
    public List<Dictionary> findDictionaryAll();

    public Dictionary findDictionaryById(int id);

    public void saveDictionary(Dictionary dictionary);

    public void deleteDictionary(int id);

    DataSourceResult getDictionaryList(DataSourceRequest request);

    /* DictionaryValue */
    public List<DictionaryValue> findDictionaryValueAll();

    public List<DictionaryValue> findDictionaryValueAllByDictionary(Dictionary dictionary);

    public List<DictionaryValue> findDictionaryValueAllByDictionaryId(int dictionaryId);

    public DictionaryValue findDictionaryValueById(int id);

    public void saveDictionaryValue(DictionaryValue dictionaryValue);

    public void deleteDictionaryValue(int id);

    DataSourceResult getDictionaryValueList(DataSourceRequest request);

}
