/**
 *
 */
package com.frogorf.realty.service.impl;

import com.frogorf.kendo.data.source.DataSourceRequest;
import com.frogorf.kendo.data.source.DataSourceResult;
import com.frogorf.realty.dao.RealtyDao;
import com.frogorf.realty.domain.*;
import com.frogorf.realty.service.RealtyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author Tsurkin Alex
 */
@Service("realtyService")
public class RealtyServiceImpl implements RealtyService {

    @Autowired
    private RealtyDao realtyDao;

    @Override
    @Transactional
    public Realty findRealtyById(int id) {
        return realtyDao.findRealtyById(id);
    }

    @Override
    @Transactional
    public void saveRealty(Realty realty) {
        realtyDao.saveRealty(realty);
    }

    @Override
    @Transactional
    public void deleteRealty(int id) {
        realtyDao.deleteRealty(id);
    }

    @Override
    @Transactional
    public DataSourceResult getListRealty(DataSourceRequest request) {
        return realtyDao.getListRealty(request);
    }

    @Override
    @Transactional
    public RealtyOption findRealtyOption(Map<String, String> params) {
        return realtyDao.findRealtyOption(params);
    }

    @Override
    @Transactional
    public RealtyOption findRealtyOptionById(int id) {
        return realtyDao.findRealtyOptionById(id);
    }

    @Override
    @Transactional
    public void saveRealtyOption(RealtyOption realtyOption) {
        realtyDao.saveRealtyOption(realtyOption);
    }

    @Override
    @Transactional
    public void deleteRealtyOption(int id) {
        realtyDao.deleteRealtyOption(id);
    }

    @Override
    @Transactional
    public DataSourceResult getListRealtyOption(DataSourceRequest request) {
        return realtyDao.getListRealtyOption(request);
    }

    @Override
    @Transactional
    public Realty findRealtyHistoryById(int id) {
        return findRealtyHistoryById(id);
    }

    @Override
    @Transactional
    public void saveRealtyHistory(RealtyHistory realtyHistory) {
        realtyDao.saveRealtyHistory(realtyHistory);
    }

    @Override
    @Transactional
    public void deleteRealtyHistory(int id) {
        realtyDao.deleteRealtyHistory(id);
    }

    @Override
    @Transactional
    public DataSourceResult getListRealtyHistory(DataSourceRequest request) {
        return realtyDao.getListRealtyHistory(request);
    }

    @Override
    @Transactional
    public void saveRealtyOptionList(List<RealtyOptionValue> realtyOptionValues) {
        realtyDao.saveRealtyOptionList(realtyOptionValues);
    }

    @Override
    public void saveRealtyHistoryPrices(List<RealtyHistoryPrice> realtyHistoryPrices) {
        realtyDao.saveRealtyHistoryPrices(realtyHistoryPrices);
    }
}
