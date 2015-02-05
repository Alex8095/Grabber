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
import java.util.Map;

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
    public Dictionary findDictionaryByCode(String code) {
        return (Dictionary) sessionFactory.getCurrentSession()
                .createCriteria(Dictionary.class)
                .add(Restrictions.eq("code", code.toUpperCase()))
                .uniqueResult();
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
        return sessionFactory.getCurrentSession()
                .createCriteria(DictionaryValue.class)
                .add(Restrictions.eq("dictionary.id", dictionaryId))
                .list();
    }

    @Override
    public List<DictionaryValue> findDictionaryValues(Map<String, String> params) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(DictionaryValue.class);
        if (params.containsKey("code")) {
            criteria.createAlias("dictionary", "dictionary");
            criteria.add(Restrictions.eq("dictionary.code", params.get("code").toUpperCase()));
        }
        if (params.containsKey("parent_id")) {
            criteria.add(Restrictions.eq("parent_id", params.get("parent_id")));
        }
        if (params.containsKey(DictionaryValue.PARAM_SITE_CODE)) {
            criteria.add(Restrictions.eq(DictionaryValue.PARAM_SITE_CODE, params.get(DictionaryValue.PARAM_SITE_CODE)));
        }
        return criteria.list();
    }

    @Override
    public List<DictionaryValue> findDictionaryValuesByCode(String code) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(DictionaryValue.class);
        criteria.createAlias("dictionary", "dictionary");
        criteria.add(Restrictions.eq("dictionary.code", code.toUpperCase()));
        return criteria.list();
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

    @Override
    public DictionaryValue findDictionaryValue(Map<String, String> params) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(DictionaryValue.class);
        if (params.containsKey("code")) {
            criteria.add(Restrictions.eq("code", params.get("code").toUpperCase()));
        }
        if (params.containsKey("parent_id")) {
            criteria.add(Restrictions.eq("parentDictionaryValue.id", Integer.valueOf(params.get("parent_id"))));
        }
        if (params.containsKey("dictionary_id")) {
            criteria.add(Restrictions.eq("dictionary.id", Integer.valueOf(params.get("dictionary_id"))));
        }
        return (DictionaryValue) criteria.uniqueResult();
    }

    @Override
    public void saveDictionaryValueList(List<DictionaryValue> list) {
        for (DictionaryValue dictionaryValue : list) {
            saveDictionaryValue(dictionaryValue);
        }
    }
}
