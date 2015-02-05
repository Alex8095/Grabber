package com.frogorf.grabber.parser;

import com.frogorf.config.HibernateConfigTest;
import com.frogorf.grabber.domain.Task;
import com.frogorf.grabber.parser.impl.OlxRealtyItemParser;
import com.frogorf.realty.service.SellerServiceTest;
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
import java.net.URL;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

/**
 * Created by Alex on 21.11.14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {HibernateConfigTest.class})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@Transactional()
public class OlxRealtyItemParserTest {

    private static final Logger logger = LoggerFactory.getLogger(SellerServiceTest.class);

    @Autowired
    private ItemParser realtyParser;
    private final String REALTY_LINK = "http://kiev.ko.olx.ua/obyavlenie/svetlaya-1-komnatnaya-kvartira-v-zhk-panorama-na-pecherske-IDaRzVd.html";
    private final String REALTY_CODE = "aRzVd";
    private final String REALTY_ADRESS = "Киев, Киевская область, Печерский, ул. Щорса 44а";
    private final String TASK_LINK = "http://";
    private final String REALTY_DOCUMENT_PRICE = "1 564 000 грн.";
    private final String REALTY_PRICE = "1564000";
    private final String REALTY_PRICE_CURRENCY = "грн";
    private final String REALTY_PHONE_URL = "http://kiev.ko.olx.ua/ajax/misc/contact/phone/aRzVd/";
    private final String PHONE = "044 591 1904";

    private Task task;

    @Before
    public void setUp() throws Exception {
//        realtyParser = new OlxRealtyItemParser();
//        realtyParser.parse(REALTY_LINK);
//
//        task = new Task();
//        task.setUrl("");
    }

    @Test
    public void testGetRealtyDocument() throws Exception {
        assertNotNull(realtyParser.getRealtyDocument());
    }

    @Test
    public void testGetRealtyAdress() throws Exception {
        assertEquals(realtyParser.getRealtyLocation(), REALTY_ADRESS);
    }


    @Test
    public void testGetRealtySiteCode() throws Exception {
        assertEquals(realtyParser.getRealtySiteCode(), REALTY_CODE);
    }

    @Test
    public void testGetRealtyImages() throws Exception {
        assertEquals(realtyParser.getRealtyImages().size(), 7);
    }

    @Test
    public void testSetTask() throws Exception {
        realtyParser.setTask(task);
        assertEquals(realtyParser.getTask(), task);
    }

    @Test
    public void testGetTask() throws Exception {
        realtyParser.setTask(task);
        assertEquals(realtyParser.getTask(), task);
    }

    @Test
    public void testGetRealtyTitle() throws Exception {
        assertEquals(realtyParser.getRealtyTitle(), "Светлая 1-комнатная квартира в ЖК \"Панорама на Печерске\"");
    }

    @Test
    public void testGetRealtyDescription() throws Exception {
        assertNotNull(realtyParser.getRealtyDescription());
    }

    @Test
    public void testGetDocumentRealtyPrice() throws Exception {
        assertEquals(realtyParser.getDocumentRealtyPrice(), REALTY_DOCUMENT_PRICE);
    }

    @Test
    public void testGetRealtyOptions() throws Exception {
        assertEquals(realtyParser.getRealtyOptions().size(), 8);
    }

    @Test
    public void testGetPhoneURI() throws Exception {
        URL url = new URL(REALTY_PHONE_URL);
        assertEquals(realtyParser.getPhoneURL(), url);
    }

    @Test
    public void testParser() throws Exception {
        Integer status = realtyParser.parse(REALTY_LINK);
        logger.info("status: %s", status);
    }
}
