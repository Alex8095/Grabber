/**
 *
 */
package com.frogorf.dictionary.service.impl;

import com.frogorf.dictionary.dao.DictionarySyncDao;
import com.frogorf.dictionary.domain.DictionarySync;
import com.frogorf.dictionary.service.DictionarySyncService;
import com.frogorf.kendo.data.source.DataSourceRequest;
import com.frogorf.kendo.data.source.DataSourceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Tsurkin Alex
 */
@Service("dictionarySyncService")
public class DictionarySuncServiceImpl implements DictionarySyncService {

    @Autowired
    private DictionarySyncDao dictionarySyncDao;

    @Override
    @Transactional
    public List<DictionarySync> findDictionarySyncAll() {
        return dictionarySyncDao.findDictionarySyncAll();
    }

    @Override
    @Transactional
    public DictionarySync findDictionarySyncById(int id) {
        return dictionarySyncDao.findDictionarySyncById(id);
    }

    @Override
    @Transactional
    public void saveDictionarySync(DictionarySync dictionarySync) {
        dictionarySyncDao.saveDictionarySync(dictionarySync);
    }

    @Override
    @Transactional
    public void deleteDictionarySync(int id) {
        dictionarySyncDao.deleteDictionarySync(id);
    }

    @Override
    @Transactional
    public DataSourceResult getDictionarySyncList(DataSourceRequest request) {
        return dictionarySyncDao.getDictionarySyncList(request);
    }
}

