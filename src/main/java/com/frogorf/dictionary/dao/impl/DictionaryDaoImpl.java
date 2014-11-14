/**
 *
 */
package com.frogorf.dictionary.dao.impl;

import com.frogorf.dictionary.dao.DictionaryDao;
import com.frogorf.dictionary.domain.Dictionary;
import com.frogorf.dictionary.domain.DictionaryValue;
import com.frogorf.kendo.data.source.DataSourceRequest;
import com.frogorf.kendo.data.source.DataSourceResult;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Tsurkin Alex
 */
@Repository("dictionaryDao")
public class DictionaryDaoImpl implements DictionaryDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Dictionary> findDictionaryAll() {
        return sessionFactory.getCurrentSession().createQuery("from Dictionary").list();
    }

    @Override
    public Dictionary findDictionaryById(int id) {
        return (Dictionary) sessionFactory.getCurrentSession().get(Dictionary.class, id);
    }

    @Override
    public void saveDictionary(Dictionary dictionary) {
        sessionFactory.getCurrentSession().saveOrUpdate(dictionary);

    }

    @Override
    public void deleteDictionary(int id) {
        sessionFactory.getCurrentSession().delete(findDictionaryById(id));
    }

    @Override
    public DataSourceResult getDictionaryList(DataSourceRequest request) {
        return request.toDataSourceResult(sessionFactory.getCurrentSession(), Dictionary.class);
    }

    @Override
    public List<DictionaryValue> findDictionaryValueAll() {
        return sessionFactory.getCurrentSession().createQuery("from DictionaryValue").list();
    }

    @Override
    public List<DictionaryValue> findDictionaryValueAllByDictionary(Dictionary dictionary) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(DictionaryValue.class);
        if (!dictionary.getCode().isEmpty())
            criteria.add(Restrictions.like("code", dictionary.getCode()));
        return criteria.list();
    }

    @Override
    public List<DictionaryValue> findDictionaryValueAllByDictionaryId(int dictionaryId) {
        return sessionFactory.getCurrentSession().createQuery("from DictionaryValue").list();
    }

    @Override
    public DictionaryValue findDictionaryValueById(int id) {
        return (DictionaryValue) sessionFactory.getCurrentSession().get(DictionaryValue.class, id);
    }

    @Override
    public void saveDictionaryValue(DictionaryValue dictionaryValue) {
        sessionFactory.getCurrentSession().saveOrUpdate(dictionaryValue);
    }

    @Override
    public void deleteDictionaryValue(int id) {
        sessionFactory.getCurrentSession().delete(findDictionaryValueById(id));
    }

    @Override
    public DataSourceResult getDictionaryValueList(DataSourceRequest request) {
        return request.toDataSourceResult(sessionFactory.getCurrentSession(), DictionaryValue.class);
    }
}
