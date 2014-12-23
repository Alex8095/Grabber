package com.frogorf.realty.dao;

import com.frogorf.kendo.data.source.DataSourceRequest;
import com.frogorf.kendo.data.source.DataSourceResult;
import com.frogorf.realty.domain.Realty;
import com.frogorf.realty.domain.Seller;

public interface SellerDao {

    public Seller findById(int id);

    public Seller findByPhoneNumber(String phoneNumber);

    Seller findBySiteCode(String siteCode);

    public void save(Seller seller);

    public void delete(int id);

    DataSourceResult getDataSourceList(DataSourceRequest request);

}