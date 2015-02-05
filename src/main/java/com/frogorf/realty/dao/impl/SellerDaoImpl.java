package com.frogorf.realty.dao.impl;

import com.frogorf.kendo.data.source.DataSourceRequest;
import com.frogorf.kendo.data.source.DataSourceResult;
import com.frogorf.realty.dao.SellerDao;
import com.frogorf.realty.domain.Seller;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository("sellerDao")
public class SellerDaoImpl implements SellerDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Seller findById(int id) {
        return (Seller) sessionFactory.getCurrentSession().get(Seller.class, id);
    }

    @Override
    public Seller findByPhoneNumber(String phoneNumber) {
        Integer sellerId = (Integer) sessionFactory.getCurrentSession()
                .createSQLQuery("SELECT sp.seller_id from Seller_phone_number sp where sp.phone_number = :phone limit 1")
                .setParameter("phone", phoneNumber)
                .uniqueResult();
        if (sellerId != null) {
            return findById(sellerId);
        }
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public Seller findBySiteCode(String siteCode) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Seller.class);
        if (siteCode != null && !siteCode.equals(""))
            criteria.add( Restrictions.like("siteCode", siteCode + "%") );
        return (Seller) criteria.uniqueResult();
    }

    @Override
    public void save(Seller seller) {
        sessionFactory.getCurrentSession().saveOrUpdate(seller);
    }

    @Override
    public void delete(int id) {
        sessionFactory.getCurrentSession().delete(findById(id));
    }

    @Override
    public DataSourceResult getDataSourceList(DataSourceRequest request) {
        return request.toDataSourceResult(sessionFactory.getCurrentSession(), Seller.class);
    }
}