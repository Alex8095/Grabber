package com.frogorf.grabber.helper;

import com.frogorf.config.HibernateConfigTest;
import com.frogorf.dictionary.service.DictionaryService;
import com.frogorf.realty.domain.Realty;
import com.frogorf.realty.service.RealtyService;
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

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;

/**
 * Created by alex on 23.12.14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {HibernateConfigTest.class})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@Transactional()
public class RealtyLocationHelperTest {

    private static final Logger logger = LoggerFactory.getLogger(RealtyLocationHelperTest.class);

    @Autowired
    private RealtyLocationHelper realtyLocationHelper;
    @Autowired
    private RealtyService realtyService;
    @Autowired
    private DictionaryService dictionaryService;

    private Realty realty;
    private static final String LOCATION_1 = "Киев, Киевская область, Святошинский, ул.Семашко 12";
    private static final String LOCATION_1_CITY = "Киев";
    private static final String LOCATION_1_AREA = "Киевская область";
    private static final String LOCATION_1_CITY_AREA = "Святошинский";
    private static final String LOCATION_1_CITY_DISTRICT = null;
    private static final String LOCATION_1_STREET = "ул.Семашко";
    private static final String LOCATION_1_HOUSE_NUMBER = "12";

    private static final String LOCATION_1_CITY_NO_FORMATED = "Киев";
    private static final String LOCATION_1_AREA_NO_FORMATED = "Киевская область";
    private static final String LOCATION_1_CITY_AREA_NO_FORMATED = "Святошинский";
    private static final String LOCATION_1_STREET_NO_FORMATED = "ул.Семашко 12";
    private static final String LOCATION_1_CITY_DISTRICT_NO_FORMATED = "ул.Семашко 12";
    private static final String LOCATION_1_HOUSE_NUMBER_NO_FORMATED = "ул.Семашко 12";

    @Before
    public void setUp() throws Exception {
        realty = new Realty();
        realtyLocationHelper.init(LOCATION_1, realty);
    }

    @Test
    public void testGetLocation() throws Exception {
        assertNull(realtyLocationHelper.getLocation());
    }

    @Test
    public void testGetLocationMap() throws Exception {
        assertNull(realtyLocationHelper.getLocationMap());
    }

    @Test
    public void testFormatRegion() throws Exception {
    }

    @Test
    public void testFormatDistrict() throws Exception {
    }

    @Test
    public void testFormatCity() throws Exception {
        assertEquals(realtyLocationHelper.formatCity(LOCATION_1_CITY_NO_FORMATED), LOCATION_1_CITY);
    }

    @Test
    public void testFormatCityArea() throws Exception {
        assertEquals(realtyLocationHelper.formatCityArea(LOCATION_1_CITY_AREA_NO_FORMATED), LOCATION_1_CITY_AREA);
    }

    @Test
    public void testFormatCityDistrict() throws Exception {
        assertEquals(realtyLocationHelper.formatCityDistrict(LOCATION_1_CITY_DISTRICT_NO_FORMATED), LOCATION_1_CITY_DISTRICT);
    }

    @Test
    public void testFormatStreet() throws Exception {
        assertEquals(realtyLocationHelper.formatStreet(LOCATION_1_STREET_NO_FORMATED), LOCATION_1_STREET);
    }

    @Test
    public void testFormatHouseNumber() throws Exception {
        assertEquals(realtyLocationHelper.formatHouseNumber(LOCATION_1_HOUSE_NUMBER_NO_FORMATED), LOCATION_1_HOUSE_NUMBER);
    }

    @Test
    public void testPutLocationMapEntry() throws Exception {

    }

    @Test
    public void testFindLocationDictionaryValue() throws Exception {

    }

    @Test
    public void testCreateDictionaryValue() throws Exception {

    }

    @Test
    public void testGetHouseNumber() throws Exception {
    }

    @Test
    public void testCreateLocationMap() throws Exception {
        realtyLocationHelper.createLocationMap();
    }

    @Test
    public void testInit() throws Exception {

    }

    @Test
    public void testClean() throws Exception {
        realtyLocationHelper.clean();
        assertNull(realtyLocationHelper.getHouseNumber());
        assertNull(realtyLocationHelper.getLocationMap());
        assertNull(realtyLocationHelper.getLocation());
    }
}
