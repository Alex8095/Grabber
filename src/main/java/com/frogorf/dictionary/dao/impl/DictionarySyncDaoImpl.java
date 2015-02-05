/**
 *
 */
package com.frogorf.dictionary.dao.impl;

import com.frogorf.dictionary.dao.DictionarySyncDao;
import com.frogorf.dictionary.domain.DictionarySync;
import com.frogorf.kendo.data.source.DataSourceRequest;
import com.frogorf.kendo.data.source.DataSourceResult;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Tsurkin Alex
 */
@Repository("dictionarySyncDao")
public class DictionarySyncDaoImpl implements DictionarySyncDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<DictionarySync> findDictionarySyncAll() {
        return sessionFactory.getCurrentSession().createQuery("from DictionarySync").list();
    }

    @Override
    public DictionarySync findDictionarySyncById(int id) {
        return (DictionarySync) sessionFactory.getCurrentSession().get(DictionarySync.class, id);
    }

    @Override
    public void saveDictionarySync(DictionarySync dictionarySync) {
        sessionFactory.getCurrentSession().saveOrUpdate(dictionarySync);
    }

    @Override
    public void deleteDictionarySync(int id) {
        sessionFactory.getCurrentSession().delete(findDictionarySyncById(id));
    }

    @Override
    public DataSourceResult getDictionarySyncList(DataSourceRequest request) {
        return request.toDataSourceResult(sessionFactory.getCurrentSession(), DictionarySync.class);
    }
}
