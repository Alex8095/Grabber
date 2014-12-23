package com.frogorf.grabber.helper;

import com.frogorf.config.HibernateConfigTest;
import com.frogorf.dictionary.domain.Dictionary;
import com.frogorf.dictionary.service.DictionaryService;
import com.frogorf.grabber.helper.selector.RealtyOptionSelector;
import com.frogorf.grabber.parser.ItemParser;
import com.frogorf.realty.domain.Realty;
import com.frogorf.realty.domain.RealtyOption;
import com.frogorf.realty.domain.RealtyOptionValue;
import com.frogorf.realty.service.RealtyService;
import com.frogorf.utils.Translit;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by Alex on 21.12.14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {HibernateConfigTest.class})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@Transactional()
public class RealtyOptionHelperTest {

    private static final Logger logger = LoggerFactory.getLogger(RealtySellerHelperTest.class);

    @Autowired
    private RealtyOptionHelper realtyOptionHelper;
    @Autowired
    private RealtyService realtyService;
    @Autowired
    private DictionaryService dictionaryService;

    private final static String ROV_1_K = "Объявление от:";
    private final static String ROV_1_V = "Частного лица";
    private final static String ROV_2_K = "Тип дома:";
    private final static String ROV_2_V = "Продажа домов за городом";
    private final static String ROV_3_K = "Площадь дома:";
    private final static String ROV_3_V = "370 м2";
    private final static String ROV_4_K = "Удалённость от города:";
    private final static String ROV_4_V = "12 км";

    private Realty realty;
    private Map<String, String> realtySourceMap;

    @Before
    public void setUp() throws Exception {
        realty = new Realty();
        realtySourceMap = new HashMap<>();
        realtySourceMap.put(ROV_1_K, ROV_1_V);
        realtySourceMap.put(ROV_2_K, ROV_2_V);
        realtySourceMap.put(ROV_3_K, ROV_3_V);
        realtySourceMap.put(ROV_4_K, ROV_4_V);
        realtyOptionHelper.init(realtySourceMap, realty);

        Dictionary dictionary = new Dictionary();
        dictionary.setCode("DICTIONARY_OPTION");
        dictionary.setName("Dictionary option");
        dictionaryService.saveDictionary(dictionary);
        RealtyOptionSelector.setPARENT_DICTIONARY_ID(dictionary.getId());
    }

    @Test
    public void testGetStatus() throws Exception {
        assertEquals(realtyOptionHelper.getParserStatus(), ItemParser.NEW);
    }

    @Test
    public void testInit() throws Exception {
        assertEquals(realtyOptionHelper.getOptionSource().size(), realtySourceMap.size());
        assertEquals(realtyOptionHelper.getRealty(), realty);
    }

    @Test
    public void testGetResult() throws Exception {
        List<RealtyOptionValue> l = realtyOptionHelper.getResult();
        assertEquals(l.size(), realtySourceMap.size());
        realty.setId(null);
        realty.setRealtyOptionValues(l);
        realtyService.saveRealty(realty);
        realtyService.saveRealtyOptionList(l);
    }

    @Test
    public void testGetRealtyOptionValue() throws Exception {
        RealtyOptionValue rov = realtyOptionHelper.getRealtyOptionValue(ROV_1_K, ROV_1_V);
        assertNull(rov.getId());
        assertEquals(rov.getRealty(), realty);
        assertEquals(rov.getDictionaryValue().getCode(), Translit.toTranslit(ROV_1_V));
        List<RealtyOptionValue> l = new ArrayList<>();
        l.add(rov);
        realty.setRealtyOptionValues(l);
        realtyService.saveRealty(realty);
        realtyService.saveRealtyOptionList(l);
    }

    @Test
    public void testIsIntegerRealtyOptionValue() throws Exception {
        assertTrue(realtyOptionHelper.isIntegerRealtyOptionValue("123"));
        assertTrue(realtyOptionHelper.isIntegerRealtyOptionValue(ROV_3_V));
        assertFalse(realtyOptionHelper.isIntegerRealtyOptionValue(ROV_1_V));
    }

    @Test
    public void testGetRealtyOptionValueValue() throws Exception {
        assertEquals(realtyOptionHelper.getRealtyOptionValueValue(ROV_3_V), (Integer) 370);
        assertEquals(realtyOptionHelper.getRealtyOptionValueValue(ROV_2_V), ROV_2_V);
    }

    @Test
    public void testGetIntegerValueFromString() throws Exception {
        assertEquals(realtyOptionHelper.getIntegerValueFromString("123"), (Integer) 123);
        assertEquals(realtyOptionHelper.getIntegerValueFromString(ROV_3_V), (Integer) 370);
        assertNull(realtyOptionHelper.getIntegerValueFromString(ROV_1_V));
    }

    @Test
    public void testGetRealtyOptionAfterValue() throws Exception {
        assertNull(realtyOptionHelper.getRealtyOptionAfterValue(ROV_2_V));
        assertEquals(realtyOptionHelper.getRealtyOptionAfterValue(ROV_3_V), "м2");
        assertEquals(realtyOptionHelper.getRealtyOptionAfterValue(ROV_4_V), "км");
    }

    @Test
    public void testFindDictionaryValue() throws Exception {
        assertNull(realtyOptionHelper.findDictionaryValue(ROV_2_V));
    }

    @Test
    public void testIsCanAddedRealtyOptionValueToList() throws Exception {

    }

    @Test
    public void testFindRealtyOption() throws Exception {
        RealtyOption realtyOption = realtyOptionHelper.findRealtyOption(ROV_3_K, ROV_3_V);
        assertEquals(realtyOption.getCode(), Translit.toTranslit(ROV_3_K).replace(":", ""));
        assertEquals(realtyOption.getAfterValue(), "м2");
        assertEquals(realtyOption.getName(), ROV_3_K.replace(":", ""));
    }

    @Test
    public void testGetRealtyOptionTypeDesign() throws Exception {

    }

    @Test
    public void testSaveRealtyOption() throws Exception {
        RealtyOption realtyOption = realtyOptionHelper.findRealtyOption(ROV_3_K, ROV_3_V);
        realtyService.saveRealtyOption(realtyOption);
        RealtyOption ro = realtyService.findRealtyOptionById(realtyOption.getId());
        assertEquals(ro.getCode(), realtyOption.getCode());
        assertEquals(ro.getAfterValue(), realtyOption.getAfterValue());
        assertEquals(ro.getName(), realtyOption.getName());
        assertNull(ro.getDictionary());
        assertNull(ro.getImplementationType());
    }

    @Test
    public void testClean() throws Exception {
        realtyOptionHelper.clean();
        assertNull(realtyOptionHelper.getRealty());
        assertNull(realtyOptionHelper.getOptionSource());
        assertEquals(realtyOptionHelper.getParserStatus(), ItemParser.NEW);
    }
}
