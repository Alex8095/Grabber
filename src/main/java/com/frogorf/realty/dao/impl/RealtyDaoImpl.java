package com.frogorf.realty.dao.impl;

import com.frogorf.kendo.data.source.DataSourceRequest;
import com.frogorf.kendo.data.source.DataSourceResult;
import com.frogorf.realty.dao.RealtyDao;
import com.frogorf.realty.domain.*;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository("realtyDao")
public class RealtyDaoImpl implements RealtyDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Realty findRealtyOr(Map<String, String> params) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Realty.class);
        Disjunction disjunction = Restrictions.disjunction();
        if (params.containsKey(Realty.TITLE_PARAM)) {
            disjunction.add(Restrictions.eq(Realty.TITLE_PARAM, params.get(Realty.TITLE_PARAM)));
        }
        if (params.containsKey(Realty.SITE_CODE_PARAM)) {
            disjunction.add(Restrictions.eq(Realty.SITE_CODE_PARAM, params.get(Realty.SITE_CODE_PARAM)));
        }
        if (params.containsKey(Realty.MAIN_SITE_CODE_PARAM)) {
            disjunction.add(Restrictions.eq(Realty.MAIN_SITE_CODE_PARAM, params.get(Realty.MAIN_SITE_CODE_PARAM)));
        }
        if (params.containsKey(Realty.SITE_URL_PARAM)) {
            disjunction.add(Restrictions.eq(Realty.SITE_URL_PARAM, params.get(Realty.SITE_URL_PARAM)));
        }
        criteria.add(disjunction);
        return (Realty) criteria.uniqueResult();
    }

    @Override
    public Realty findRealtyById(int id) {
        return (Realty) sessionFactory.getCurrentSession().get(Realty.class, id);
    }

    @Override
    public void saveRealty(Realty realty) {
        sessionFactory.getCurrentSession().saveOrUpdate(realty);
    }

    @Override
    public void deleteRealty(int id) {
        sessionFactory.getCurrentSession().delete(findRealtyById(id));
    }

    @Override
    public DataSourceResult getListRealty(DataSourceRequest request) {
        return request.toDataSourceResult(sessionFactory.getCurrentSession(), Realty.class);
    }

    @Override
    public RealtyOption findRealtyOptionById(int id) {
        return (RealtyOption) sessionFactory.getCurrentSession().get(RealtyOption.class, id);
    }

    @Override
    public RealtyOption findRealtyOption(Map<String, String> params) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(RealtyOption.class);
        if (params.containsKey(RealtyOption.PARAM_CODE)) {
            criteria.add(Restrictions.eq(RealtyOption.PARAM_CODE, params.get(RealtyOption.PARAM_CODE).toUpperCase()));
        }
        return (RealtyOption) criteria.uniqueResult();
    }

    @Override
    public void saveRealtyOption(RealtyOption realtyOption) {
        sessionFactory.getCurrentSession().saveOrUpdate(realtyOption);
    }

    @Override
    public void deleteRealtyOption(int id) {
        sessionFactory.getCurrentSession().delete(findRealtyOptionById(id));
    }

    @Override
    public DataSourceResult getListRealtyOption(DataSourceRequest request) {
        return request.toDataSourceResult(sessionFactory.getCurrentSession(), RealtyOption.class);
    }

    @Override
    public Realty findRealtyHistoryById(int id) {
        return (Realty) sessionFactory.getCurrentSession().get(RealtyHistory.class, id);
    }

    @Override
    public void saveRealtyHistory(RealtyHistory realtyHistory) {
        sessionFactory.getCurrentSession().saveOrUpdate(realtyHistory);
    }

    @Override
    public void deleteRealtyHistory(int id) {
        sessionFactory.getCurrentSession().delete(findRealtyHistoryById(id));
    }

    @Override
    public DataSourceResult getListRealtyHistory(DataSourceRequest request) {
        return request.toDataSourceResult(sessionFactory.getCurrentSession(), RealtyHistory.class);
    }

    @Override
    public void saveRealtyOptionList(List<RealtyOptionValue> realtyOptionValues) {
        for (RealtyOptionValue realtyOptionValue : realtyOptionValues) {
            sessionFactory.getCurrentSession().saveOrUpdate(realtyOptionValue);
        }
    }

    @Override
    public void saveRealtyHistoryPrices(List<RealtyHistoryPrice> realtyHistoryPrices) {
        for (RealtyHistoryPrice realtyHistoryPrice : realtyHistoryPrices) {
            sessionFactory.getCurrentSession().saveOrUpdate(realtyHistoryPrice);
        }
    }

    @Override
    public void saveRealtyImages(List<RealtyImage> realtyImages) {
        for (RealtyImage realtyImage : realtyImages) {
            sessionFactory.getCurrentSession().saveOrUpdate(realtyImage);
        }
    }
}
