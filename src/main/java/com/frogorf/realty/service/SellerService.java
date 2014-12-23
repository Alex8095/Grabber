package com.frogorf.realty.service;

import com.frogorf.kendo.data.source.DataSourceRequest;
import com.frogorf.kendo.data.source.DataSourceResult;
import com.frogorf.realty.domain.Seller;

public interface SellerService {

    public Seller findById(int id);

    public Seller findByPhoneNumber(String phoneNumber);

    public Seller findBySiteCode(String siteCode);

    public void save(Seller seller);

    public void delete(int id);

    DataSourceResult getDataSourceList(DataSourceRequest request);
}