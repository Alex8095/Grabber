package com.frogorf.realty.service;

import com.frogorf.config.HibernateConfigTest;
import com.frogorf.realty.domain.Realty;
import com.frogorf.realty.domain.RealtyLocale;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static org.junit.Assert.assertEquals;

/**
 * Created by alex on 15.12.14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {HibernateConfigTest.class})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@Transactional()
public class RealtyServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(SellerServiceTest.class);

    @Autowired
    private RealtyService realtyService;
    private Realty realty;

    private static final String TITLE = "realty_title_param";
    private final static Boolean IS_SALE = true;
    private final static Boolean IS_RENT = false;
    private final static Boolean IS_CHANGE = false;
    private final static Long PRICE = 10000L;
    private final static String SITE_CODE = "realty_site_code";
    private final static String SITE_URL = "realty_site_url";
    private final static String MAIN_SITE_CODE = "realty_main_site_code";

    @Before
    public void setUp() throws Exception {
        realty = new Realty();
        realty.setIsRent(IS_RENT);
        realty.setIsSale(IS_SALE);
        realty.setIsChange(IS_CHANGE);
//        realty.setOwnerType(OWNER_TYPE);
//        realty.setImplementationType(IMPLEMENTATION_TYPE);
        realty.setPrice(PRICE);
        realty.setSiteCode(SITE_CODE.toLowerCase());
        realty.setSiteUrl(SITE_URL);
        realty.setMainSiteCode(MAIN_SITE_CODE.toLowerCase());
        Map<String, RealtyLocale> locales = new HashMap<>();
        locales.put("ru", new RealtyLocale(TITLE, ""));
        realty.setLocales(locales);
    }

    @Test
    public void testFindRealty() {
        realtyService.saveRealty(realty);
        Map<String, String> params = new HashMap<>();
        params.put(Realty.MAIN_SITE_CODE_PARAM, MAIN_SITE_CODE);
        Realty r = realtyService.findRealty(params);
        assertEquals(realty.getSiteCode(), r.getSiteCode());
    }

    @Test
    public void testFindRealtyOr() throws Exception {
        realtyService.saveRealty(realty);
        Map<String, String> params = new HashMap<>();
//        params.put(Realty.TITLE_PARAM, TITLE);
        params.put(Realty.SITE_CODE_PARAM, SITE_CODE);
        params.put(Realty.MAIN_SITE_CODE_PARAM, MAIN_SITE_CODE);
        params.put(Realty.SITE_URL_PARAM, SITE_URL);
        Realty r = realtyService.findRealtyOr(params);
        assertEquals(realty.getPrice(), r.getPrice());
        assertEquals(realty.getIsRent(), r.getIsRent());
        assertEquals(realty.getTitle(), r.getTitle());
        assertEquals(realty.getSiteCode(), r.getSiteCode());
    }

    @Test
    public void testFindRealtyById() throws Exception {
        realtyService.saveRealty(realty);
        Realty r = realtyService.findRealtyById(realty.getId());
        assertEquals(realty.getPrice(), r.getPrice());
    }

    @Test
    public void testSaveRealty() throws Exception {
        realtyService.saveRealty(realty);
        assertNotNull(realty.getId());
    }

    @Test
    public void testDeleteRealty() throws Exception {
        realtyService.saveRealty(realty);
        realtyService.deleteRealty(realty.getId());
        assertNull(realtyService.findRealtyOptionById(realty.getId()));
    }

    @Test
    public void testGetListRealty() throws Exception {

    }

    @Test
    public void testFindRealtyOption() throws Exception {

    }

    @Test
    public void testFindRealtyOptionById() throws Exception {

    }

    @Test
    public void testSaveRealtyOption() throws Exception {

    }

    @Test
    public void testDeleteRealtyOption() throws Exception {

    }

    @Test
    public void testGetListRealtyOption() throws Exception {

    }

    @Test
    public void testFindRealtyHistoryById() throws Exception {

    }

    @Test
    public void testSaveRealtyHistory() throws Exception {

    }

    @Test
    public void testDeleteRealtyHistory() throws Exception {

    }

    @Test
    public void testGetListRealtyHistory() throws Exception {

    }

    @Test
    public void testSaveRealtyOptionList() throws Exception {

    }

    @Test
    public void testSaveRealtyHistoryPrices() throws Exception {

    }

    @Test
    public void testSaveRealtyImages() throws Exception {

    }
}
