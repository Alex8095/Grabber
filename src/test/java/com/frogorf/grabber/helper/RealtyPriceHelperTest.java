package com.frogorf.grabber.helper;

import com.frogorf.config.HibernateConfigTest;
import com.frogorf.dictionary.domain.Dictionary;
import com.frogorf.dictionary.domain.DictionaryValue;
import com.frogorf.dictionary.service.DictionaryService;
import com.frogorf.grabber.parser.ItemParser;
import com.frogorf.realty.domain.Realty;
import com.frogorf.realty.service.RealtyService;
import com.frogorf.utils.Transliterator;
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

import static org.junit.Assert.*;

/**
 * Created by Alex on 23.12.14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {HibernateConfigTest.class})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@Transactional()
public class RealtyPriceHelperTest {

    private static final Logger logger = LoggerFactory.getLogger(RealtyPriceHelperTest.class);

    @Autowired
    private RealtyPriceHelper realtyPriceHelper;
    @Autowired
    private RealtyService realtyService;
    @Autowired
    private DictionaryService dictionaryService;

    private final static String PRICE_1 = "69 000 $";
    private final static Long PRICE_1_V = Long.valueOf(69000);
    private final static String PRICE_1_C = "$";
    private final static String PRICE_2 = "70 000 $";
    private final static Long PRICE_2_V = Long.valueOf(70000);
    private final static String PRICE_2_C = "$";

    private DictionaryValue currencyDictionary;
    private Realty realty;

    @Before
    public void setUp() throws Exception {
        realty = new Realty();
        realtyPriceHelper.init(PRICE_1, realty);

        Map<String, String> params = new HashMap<>();
        params.put(DictionaryValue.PARAM_CODE, Transliterator.transliterate(PRICE_1_C));

        currencyDictionary = dictionaryService.findDictionaryValue(params);
        if (currencyDictionary == null) {
            Dictionary d = new Dictionary();
            d.setName("name");
            currencyDictionary = new DictionaryValue();
            currencyDictionary.setDictionary(d);
            currencyDictionary.setCode(Transliterator.transliterate(PRICE_1_C));
            currencyDictionary.setName(PRICE_1_C);
            dictionaryService.saveDictionary(d);
            dictionaryService.saveDictionaryValue(currencyDictionary);
        }
    }

    @Test
    public void testGetRealty() throws Exception {
        assertEquals(realtyPriceHelper.getRealty(), realty);
    }

    @Test
    public void testInit() throws Exception {
        assertNotNull(realtyPriceHelper);
        assertEquals(realtyPriceHelper.getRealty(), realty);
    }

    @Test
    public void testGetPrice() throws Exception {
        assertEquals(realtyPriceHelper.getPrice(), PRICE_1_V);
        realtyPriceHelper.init(PRICE_2, realty);
        assertEquals(realtyPriceHelper.getPrice(), PRICE_2_V);
    }

    @Test
    public void testHelper() throws Exception {
        realty.setPrice(realtyPriceHelper.getPrice());
        realty.setCurrency(realtyPriceHelper.getCurrencyDictionaryValue());
        realty.setRealtyHistoryPrices(realtyPriceHelper.getRealtyHistoryPrices());
        realtyService.saveRealty(realty);
        realtyService.saveRealtyHistoryPrices(realty.getRealtyHistoryPrices());

        assertEquals(realty.getPrice(), PRICE_1_V);
        assertEquals(realty.getCurrency().getId(), currencyDictionary.getId());
        assertEquals(realty.getRealtyHistoryPrices().size(), 1);
        assertEquals(realtyPriceHelper.getParserStatus(), ItemParser.NEW);

        realtyPriceHelper.init(PRICE_2, realty);
        realty.setPrice(realtyPriceHelper.getPrice());
        realty.setCurrency(realtyPriceHelper.getCurrencyDictionaryValue());
        realty.setRealtyHistoryPrices(realtyPriceHelper.getRealtyHistoryPrices());
        realtyService.saveRealty(realty);
        realtyService.saveRealtyHistoryPrices(realty.getRealtyHistoryPrices());

        assertEquals(realty.getPrice(), PRICE_2_V);
        assertEquals(realty.getCurrency().getId(), currencyDictionary.getId());
        assertEquals(realty.getRealtyHistoryPrices().size(), 2);
        assertEquals(realtyPriceHelper.getParserStatus(), ItemParser.UPDATE);
    }

    @Test
    public void testGetRealtyHistoryPrice() throws Exception {

    }

    @Test
    public void testGetCurrencyDictionaryValue() throws Exception {
        DictionaryValue dv = realtyPriceHelper.getCurrencyDictionaryValue();
        assertEquals(dv.getCode(), currencyDictionary.getCode());
        assertEquals(dv.getName(), currencyDictionary.getName());
        assertEquals(dv, currencyDictionary);
    }

    @Test
    public void testGetStatus() throws Exception {

    }

    @Test
    public void testClean() throws Exception {
        realtyPriceHelper.clean();
        assertNull(realtyPriceHelper.getRealty());
        assertNull(realtyPriceHelper.getCurrencyDictionaryValue());
        assertNull(realtyPriceHelper.getRealtyHistoryPrice());
        assertEquals(realtyPriceHelper.getParserStatus(), ItemParser.NEW);
    }

    @Test
    public void testGetPriceValue() throws Exception {
        assertEquals(PRICE_1_V, realtyPriceHelper.getPriceValue());
    }

    @Test
    public void testGetCurrencyValue() throws Exception {
        assertEquals(PRICE_1_C, realtyPriceHelper.getCurrencyValue());
    }
}
