package com.frogorf.dictionary.service;

import com.frogorf.config.HibernateConfigTest;
import com.frogorf.dictionary.domain.Dictionary;
import com.frogorf.dictionary.domain.DictionaryValue;
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
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;

/**
 * Created by alex on 21.11.14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {HibernateConfigTest.class})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional()
public class DictionaryServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(DictionaryServiceTest.class);

    @Autowired
    private DictionaryService dictionaryService;

    private Dictionary dictionary;
    private DictionaryValue dictionaryValue;
    private final String D_CODE = "d_code";
    private final String D_DESC = "d_desc";
    private final String D_NAME = "d_name";
    private final String DV_CODE = "dv_code";
    private final String DV_DESC = "dv_desc";
    private final String DV_NAME = "dv_name";


    @Before
    public void setUp() throws Exception {
        dictionary = new Dictionary();
        dictionary.setCode(D_CODE);
        dictionary.setDescription(D_DESC);
        dictionary.setName(D_NAME);

        dictionaryValue = new DictionaryValue();
        dictionaryValue.setCode(DV_CODE);
        dictionaryValue.setDescription(DV_DESC);
        dictionaryValue.setName(DV_NAME);
    }


    @Test
    public void testFindDictionaryAll() throws Exception {
        List<Dictionary> dictionaryList = dictionaryService.findDictionaryAll();
        assertNotNull(dictionaryList.size());
    }

    @Test
    public void testFindDictionaryById() throws Exception {

    }

    @Test
    public void testFindDictionaryByCode() throws Exception {

    }

    @Test
    public void testSaveDictionary() throws Exception {
        dictionaryService.saveDictionary(dictionary);
        assertNotNull(dictionary.getId());
    }

    @Test
    public void testDeleteDictionary() throws Exception {

    }

    @Test
    public void testGetDictionaryList() throws Exception {

    }

    @Test
    public void testFindDictionaryValueAll() throws Exception {

    }

    @Test
    public void testFindDictionaryValuesByCode() throws Exception {
        String d_code = "dnewcode";
        String d_name = "dvnewname";
        dictionary.setCode(d_code);
        dictionaryService.saveDictionary(dictionary);
        assertNotNull(dictionary.getId());
        dictionaryValue.setDictionary(dictionary);
        dictionaryValue.setName(d_name);
        dictionaryService.saveDictionaryValue(dictionaryValue);
        List<DictionaryValue> list = dictionaryService.findDictionaryValuesByCode(d_code);
        assertEquals(dictionary.getCode(), d_code);
        assertNotNull(list);
        assertEquals(list.get(0).getDictionary().getCode(), d_code);
        assertEquals(list.get(0).getName(), d_name);

    }

    @Test
    public void testFindDictionaryValueAllByDictionary() throws Exception {

    }

    @Test
    public void testFindDictionaryValueAllByDictionaryId() throws Exception {
        dictionaryService.saveDictionary(dictionary);
        dictionaryValue.setDictionary(dictionary);
        dictionaryService.saveDictionaryValue(dictionaryValue);

        logger.info(dictionary.getId().toString());
        List<DictionaryValue> list = dictionaryService.findDictionaryValueAllByDictionaryId(dictionary.getId());
        assertEquals(list.get(0).getDictionary(), dictionary);
        assertEquals(list.get(0).getName(), DV_NAME);
    }

    @Test
    public void testFindDictionaryValueById() throws Exception {

    }

    @Test
    public void testSaveDictionaryValue() throws Exception {
        dictionaryService.saveDictionary(dictionary);
        dictionaryValue.setDictionary(dictionary);
        dictionaryService.saveDictionaryValue(dictionaryValue);
        assertEquals(dictionaryValue.getDescription(), DV_DESC);
        assertNotNull(dictionaryValue.getId());
    }

    @Test
    public void testDeleteDictionaryValue() throws Exception {

    }

    @Test
    public void testGetDictionaryValueList() throws Exception {

    }
}
