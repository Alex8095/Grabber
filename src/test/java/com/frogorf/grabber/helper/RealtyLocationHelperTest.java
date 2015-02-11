package com.frogorf.grabber.helper;

import com.frogorf.config.HibernateConfigTest;
import com.frogorf.core.location.domain.Location;
import com.frogorf.dictionary.domain.Dictionary;
import com.frogorf.dictionary.domain.DictionaryValue;
import com.frogorf.dictionary.service.DictionaryService;
import com.frogorf.grabber.helper.selector.LocationSelector;
import com.frogorf.realty.domain.Realty;
import com.frogorf.realty.service.RealtyService;
import com.frogorf.utils.Transliterator;
import org.junit.After;
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

import static junit.framework.Assert.*;

/**
 * Created by alex on 23.12.14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {HibernateConfigTest.class})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
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
    private static final String LOCATION_1_REGION = "Киевская область";
    private static final String LOCATION_1_CITY_AREA = "Святошинский";
    private static final String LOCATION_1_CITY_DISTRICT = null;
    private static final String LOCATION_1_STREET = "ул.Семашко";
    private static final String LOCATION_1_HOUSE_NUMBER = "12";

    private static final String LOCATION_1_CITY_NO_FORMATED = "Киев";
    private static final String LOCATION_1_REGION_NO_FORMATED = "Киевская область";
    private static final String LOCATION_1_CITY_AREA_NO_FORMATED = "Святошинский";
    private static final String LOCATION_1_STREET_NO_FORMATED = "ул.Семашко 12";
    private static final String LOCATION_1_CITY_DISTRICT_NO_FORMATED = "ул.Семашко 12";
    private static final String LOCATION_1_HOUSE_NUMBER_NO_FORMATED = "ул.Семашко 12";

    private Dictionary countryD, regionD, districtD, cityD, cityAreaD, cityDistrictD, streetD;
    private DictionaryValue countryDV, regionDV, districtDV, cityDV, cityAreaDV, cityDistrictDV, streetDV;

    @Before
    public void setUp() throws Exception {
        realty = new Realty();
        realtyLocationHelper.init(LOCATION_1, realty);

        countryD = new Dictionary("counryD", Transliterator.transliterate("counryD"), null, "ru");
        regionD = new Dictionary("regionD", Transliterator.transliterate("regionD"), null, "ru");
        districtD = new Dictionary("districtD", Transliterator.transliterate("districtD"), null, "ru");
        cityD = new Dictionary("cityD", Transliterator.transliterate("cityD"), null, "ru");
        cityAreaD = new Dictionary("cityAreaD", Transliterator.transliterate("cityAreaD"), null, "ru");
        cityDistrictD = new Dictionary("cityDistrictD", Transliterator.transliterate("cityDistrictD"), null, "ru");
        streetD = new Dictionary("streetD", Transliterator.transliterate("streetD"), null, "ru");
        dictionaryService.saveDictionary(countryD);
        dictionaryService.saveDictionary(regionD);
        dictionaryService.saveDictionary(districtD);
        dictionaryService.saveDictionary(cityD);
        dictionaryService.saveDictionary(cityAreaD);
        dictionaryService.saveDictionary(cityDistrictD);
        dictionaryService.saveDictionary(streetD);

        LocationSelector.setCOUNTRY_D_ID(countryD.getId());
        LocationSelector.setREGION_D_ID(regionD.getId());
        LocationSelector.setDISTRICT_D_ID(districtD.getId());
        LocationSelector.setCITY_D_ID(cityD.getId());
        LocationSelector.setCITY_AREA_D_ID(cityAreaD.getId());
        LocationSelector.setCITY_DISTRICT_D_ID(cityDistrictD.getId());
        LocationSelector.setSTREET_D_ID(streetD.getId());

        countryDV = new DictionaryValue();
        countryDV.setDictionary(countryD);
        countryDV.setName("countryDV");
        countryDV.setCode("countryDV");
        regionDV = new DictionaryValue();
        regionDV.setDictionary(regionD);
        regionDV.setName(LOCATION_1_REGION);
        regionDV.setCode(LOCATION_1_REGION);
        districtDV = new DictionaryValue();
        districtDV.setDictionary(districtD);
        districtDV.setName("districtDV");
        districtDV.setCode("districtDV");
        cityDV = new DictionaryValue();
        cityDV.setDictionary(cityD);
        cityDV.setName(LOCATION_1_CITY);
        cityDV.setCode(LOCATION_1_CITY);
        cityAreaDV = new DictionaryValue();
        cityAreaDV.setDictionary(cityAreaD);
        cityAreaDV.setName(LOCATION_1_CITY_AREA);
        cityAreaDV.setCode(LOCATION_1_CITY_AREA);
        cityDistrictDV = new DictionaryValue();
        cityDistrictDV.setDictionary(cityDistrictD);
        cityDistrictDV.setName("cityDistrictDV");
        cityDistrictDV.setCode("cityDistrictDV");
        streetDV = new DictionaryValue();
        streetDV.setDictionary(streetD);
        streetDV.setName(LOCATION_1_STREET);
        streetDV.setCode(LOCATION_1_STREET);

        dictionaryService.saveDictionaryValue(countryDV);
        dictionaryService.saveDictionaryValue(regionDV);
        dictionaryService.saveDictionaryValue(districtDV);
        dictionaryService.saveDictionaryValue(cityDV);
        dictionaryService.saveDictionaryValue(cityAreaDV);
        dictionaryService.saveDictionaryValue(cityDistrictDV);
        dictionaryService.saveDictionaryValue(streetDV);
    }

    @After
    public void tearDown() throws Exception {
//        dictionaryService.deleteDictionaryValue(countryDV.getId());
//        dictionaryService.deleteDictionaryValue(regionDV.getId());
//        dictionaryService.deleteDictionaryValue(districtDV.getId());
//        dictionaryService.deleteDictionaryValue(cityDV.getId());
//        dictionaryService.deleteDictionaryValue(cityAreaDV.getId());
//        dictionaryService.deleteDictionaryValue(cityDistrictDV.getId());
//        dictionaryService.deleteDictionaryValue(streetDV.getId());
//
//        dictionaryService.deleteDictionary(countryD.getId());
//        dictionaryService.deleteDictionary(regionD.getId());
//        dictionaryService.deleteDictionary(districtD.getId());
//        dictionaryService.deleteDictionary(cityAreaD.getId());
//        dictionaryService.deleteDictionary(cityDistrictD.getId());
//        dictionaryService.deleteDictionary(streetD.getId());
    }

    @Test
    public void testGetLocation() throws Exception {
        Location location = realtyLocationHelper.getLocation();

        assertEquals(location.getRegion().getId(), regionDV.getId());
        assertNull(location.getDistrict());
        assertEquals(location.getCity().getId(), cityDV.getId());
        assertEquals(location.getAreaCity().getId(), cityAreaDV.getId());
        assertNull(location.getDistrictCity());
        assertEquals(location.getStreet().getId(), streetDV.getId());
        assertEquals(location.getHouseNumber(), LOCATION_1_HOUSE_NUMBER);
        assertEquals(location.getSiteLocation(), LOCATION_1);
    }

    @Test
    public void testGetLocationMap() throws Exception {
        assertNull(realtyLocationHelper.getLocationMap());
    }

    @Test
    public void testFormatRegion() throws Exception {
        assertEquals(realtyLocationHelper.formatRegion(LOCATION_1_REGION_NO_FORMATED), LOCATION_1_REGION);
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
        realtyLocationHelper.putLocationMapEntry(LocationSelector.CITY_D_ID, "city");
        assertEquals(realtyLocationHelper.getLocationMap().size(), 1);
    }

    @Test
    public void testFindLocationDictionaryValue() throws Exception {
        assertNull(realtyLocationHelper.findLocationDictionaryValue("area", LocationSelector.CITY_AREA_D_ID));
        DictionaryValue city = realtyLocationHelper.findLocationDictionaryValue(Transliterator.transliterate(LOCATION_1_CITY).toUpperCase(), LocationSelector.CITY_D_ID);
        assertEquals(cityDV.getCode(), city.getCode());
        assertEquals(cityDV.getId(), city.getId());

        DictionaryValue street = realtyLocationHelper.findLocationDictionaryValue(Transliterator.transliterate(LOCATION_1_STREET).toUpperCase(), LocationSelector.STREET_D_ID);
        assertEquals(streetDV.getCode(), street.getCode());
        assertEquals(streetDV.getId(), street.getId());

        assertNull(realtyLocationHelper.findLocationDictionaryValue(Transliterator.transliterate(LOCATION_1_STREET).toUpperCase(), LocationSelector.CITY_AREA_D_ID));
    }

    @Test
    public void testGetHouseNumber() throws Exception {
        realtyLocationHelper.createLocationMap();
        assertEquals(realtyLocationHelper.getHouseNumber(), LOCATION_1_HOUSE_NUMBER);
    }

    @Test
    public void testCreateLocationMap() throws Exception {
        realtyLocationHelper.createLocationMap();
        assertNotNull(realtyLocationHelper.getLocationMap());
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
