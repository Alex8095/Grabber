package com.frogorf.grabber.helper;

import com.frogorf.config.HibernateConfigTest;
import com.frogorf.grabber.parser.ItemParser;
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
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by Alex on 23.12.14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {HibernateConfigTest.class})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@Transactional()
public class RealtyHelperTest {

    private static final Logger logger = LoggerFactory.getLogger(RealtyHelperTest.class);
    private static final Date DATE = new Date("2014/12/17");

    @Autowired
    private RealtyHelper realtyHelper;
    @Autowired
    private RealtyService realtyService;

    private Realty realty;

    private static final String TITLE_OWNER_TRUE = "Продаётся 3-х комн. Лесной, Волкова. Без комиссии. СОБСТВЕННИК";
    private static final String TITLE_OWNER_FALSE = "Продаётся 3-х комн. Лесной, Волкова. Без коиссии. СБСТВЕННИК";
    private static final String DATE_PUBLISHING = "Добавлено: в 13:42, 17 декабря 2014, ";

    @Before
    public void setUp() throws Exception {
        realty = new Realty();
        realtyHelper.init(realty);
    }

    @Test
    public void testInit() throws Exception {
        assertEquals(realty, realtyHelper.getRealty());
        assertEquals(realtyHelper.getParserStatus(), ItemParser.NEW);
    }

    @Test
    public void testClean() throws Exception {
        realtyHelper.clean();
        assertNull(realtyHelper.getRealty());
        assertEquals(realtyHelper.getParserStatus(), ItemParser.NEW);
    }

    @Test
    public void testSetParserStatus() throws Exception {
        assertEquals(realtyHelper.setParserStatus(ItemParser.NEW), ItemParser.NEW);
        assertEquals(realtyHelper.setParserStatus(ItemParser.DUPLICATE), ItemParser.DUPLICATE);
        assertEquals(realtyHelper.setParserStatus(ItemParser.UPDATE), ItemParser.UPDATE);
    }

    @Test
    public void testGetParserStatus() throws Exception {
        assertEquals(realtyHelper.getParserStatus(), ItemParser.NEW);
        realtyHelper.setParserStatus(ItemParser.DUPLICATE);
        assertEquals(realtyHelper.getParserStatus(), ItemParser.DUPLICATE);
        realtyHelper.setParserStatus(ItemParser.UPDATE);
        assertEquals(realtyHelper.getParserStatus(), ItemParser.UPDATE);
    }

    @Test
    public void testGetRealty() throws Exception {
        assertEquals(realty, realtyHelper.getRealty());
        realtyService.saveRealty(realty);
        assertEquals(realty, realtyHelper.getRealty());
    }

    @Test
    public void testGetParseOwner() throws Exception {
    }

    @Test
    public void testParseOwner() throws Exception {
        assertTrue(realtyHelper.parseIsNoCommision(TITLE_OWNER_TRUE));
        assertFalse(realtyHelper.parseIsNoCommision(TITLE_OWNER_FALSE));
    }

    @Test
    public void testGetParseDatePublishing() throws Exception {
        assertEquals(DATE, realtyHelper.getParseDatePublishing(DATE_PUBLISHING));
    }


}
