package com.frogorf.realty.service.impl;

import com.frogorf.kendo.data.source.DataSourceRequest;
import com.frogorf.kendo.data.source.DataSourceResult;
import com.frogorf.realty.dao.SellerDao;
import com.frogorf.realty.domain.Seller;
import com.frogorf.realty.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by alex on 15.12.14.
 */
@Service("sellerService")
public class SellerServiceImpl implements SellerService {

    @Autowired
    private SellerDao sellerDao;

    @Override
    public Seller findById(int id) {
        return sellerDao.findById(id);
    }

    @Override
    public Seller findByPhoneNumber(String phoneNumber) {
        return sellerDao.findByPhoneNumber(phoneNumber);
    }

    @Override
    public Seller findBySiteCode(String siteCode) {
        return sellerDao.findBySiteCode(siteCode);
    }

    @Override
    public void save(Seller seller) {
        sellerDao.save(seller);
    }

    @Override
    public void delete(int id) {
        sellerDao.delete(id);

    }

    @Override
    public DataSourceResult getDataSourceList(DataSourceRequest request) {
        return sellerDao.getDataSourceList(request);
    }
}
