/**
 *
 */
package com.frogorf.dictionary.dao;

import com.frogorf.dictionary.domain.DictionarySync;
import com.frogorf.kendo.data.source.DataSourceRequest;
import com.frogorf.kendo.data.source.DataSourceResult;

import java.util.List;

/**
 * @author Tsurkin Alex
 */
public interface DictionarySyncDao {

    public List<DictionarySync> findDictionarySyncAll();

    public DictionarySync findDictionarySyncById(int id);

    public void saveDictionarySync(DictionarySync dictionarySync);

    public void deleteDictionarySync(int id);

    DataSourceResult getDictionarySyncList(DataSourceRequest request);

}
