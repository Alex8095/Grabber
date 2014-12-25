package com.frogorf.realty.service;

import com.frogorf.kendo.data.source.DataSourceRequest;
import com.frogorf.kendo.data.source.DataSourceResult;
import com.frogorf.realty.domain.*;

import java.util.List;
import java.util.Map;

public interface RealtyService {

    Realty findRealty(Map<String, String> params);

    Realty findRealtyOr(Map<String, String> params);

    public Realty findRealtyById(int id);

    public void saveRealty(Realty realty);

    public void deleteRealty(int id);

    DataSourceResult getListRealty(DataSourceRequest request);


    /*RealtyOption*/
    public RealtyOption findRealtyOption(Map<String, String> params);

    public RealtyOption findRealtyOptionById(int id);

    public void saveRealtyOption(RealtyOption realtyOption);

    public void deleteRealtyOption(int id);

    DataSourceResult getListRealtyOption(DataSourceRequest request);

    /*RealtyHistory*/
    public Realty findRealtyHistoryById(int id);

    public void saveRealtyHistory(RealtyHistory realtyHistory);

    public void deleteRealtyHistory(int id);

    DataSourceResult getListRealtyHistory(DataSourceRequest request);

    void saveRealtyOptionList(List<RealtyOptionValue> realtyOptionValues);

    /*Price*/
    void saveRealtyHistoryPrices(List<RealtyHistoryPrice> realtyHistoryPrices);

    /*Price*/
    void saveRealtyImages(List<RealtyImage> realtyImages);

}
