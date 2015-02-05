/**
 *
 */
package com.frogorf.dictionary.service.impl;

import com.frogorf.dictionary.dao.DictionaryDao;
import com.frogorf.dictionary.domain.Dictionary;
import com.frogorf.dictionary.domain.DictionaryValue;
import com.frogorf.dictionary.service.DictionaryService;
import com.frogorf.kendo.data.source.DataSourceRequest;
import com.frogorf.kendo.data.source.DataSourceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author Tsurkin Alex
 */
@Service("dictionaryService")
public class DictionaryServiceImpl implements DictionaryService {

    @Autowired
    private DictionaryDao dictionaryDao;

    @Override
    @Transactional
    public List<Dictionary> findDictionaryAll() {
        return dictionaryDao.findDictionaryAll();
    }

    @Override
    @Transactional
    public Dictionary findDictionaryById(int id) {
        return dictionaryDao.findDictionaryById(id);
    }

    @Override
    public Dictionary findDictionaryByCode(String code) {
        return dictionaryDao.findDictionaryByCode(code);
    }

    @Override
    @Transactional
    public void saveDictionary(Dictionary dictionary) {
        dictionaryDao.saveDictionary(dictionary);
    }

    @Override
    @Transactional
    public void deleteDictionary(int id) {
        dictionaryDao.deleteDictionary(id);
    }

    @Override
    @Transactional
    public DataSourceResult getDictionaryList(DataSourceRequest request) {
        return dictionaryDao.getDictionaryList(request);
    }

    @Override
    @Transactional
    public List<DictionaryValue> findDictionaryValueAll() {
        return dictionaryDao.findDictionaryValueAll();
    }

    @Override
    @Transactional
    public List<DictionaryValue> findDictionaryValues(Map<String, String> params) {
        return dictionaryDao.findDictionaryValues(params);
    }

    @Override
    @Transactional
    public List<DictionaryValue> findDictionaryValuesByCode(String code) {
        return dictionaryDao.findDictionaryValuesByCode(code);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DictionaryValue> findDictionaryValueAllByDictionary(Dictionary dictionary) {
        return dictionaryDao.findDictionaryValueAllByDictionary(dictionary);
    }

    @Override
    @Transactional
    public List<DictionaryValue> findDictionaryValueAllByDictionaryId(int dictionaryId) {
        return dictionaryDao.findDictionaryValueAllByDictionaryId(dictionaryId);
    }

    @Override
    @Transactional
    public DictionaryValue findDictionaryValueById(int id) {
        return dictionaryDao.findDictionaryValueById(id);
    }

    @Override
    @Transactional
    public void saveDictionaryValue(DictionaryValue dictionaryValue) {
        dictionaryDao.saveDictionaryValue(dictionaryValue);
    }

    @Override
    @Transactional(readOnly = true)
    public void deleteDictionaryValue(int id) {
        dictionaryDao.deleteDictionary(id);
    }

    @Override
    @Transactional
    public DataSourceResult getDictionaryValueList(DataSourceRequest request) {
        return dictionaryDao.getDictionaryValueList(request);
    }

    @Override
    @Transactional
    public DictionaryValue findDictionaryValue(Map<String, String> params) {
        return dictionaryDao.findDictionaryValue(params);
    }

    @Override
    @Transactional
    public void saveDictionaryValueList(List<DictionaryValue> list) {
        dictionaryDao.saveDictionaryValueList(list);
    }

}
