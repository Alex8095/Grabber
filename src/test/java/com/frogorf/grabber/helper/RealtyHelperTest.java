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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by Alex on 23.12.14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {HibernateConfigTest.class})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@Transactional()
public class RealtyHelperTest {

    private static final Logger logger = LoggerFactory.getLogger(RealtyHelperTest.class);

    @Autowired
    private RealtyHelper realtyHelper;
    @Autowired
    private RealtyService realtyService;

    private Realty realty;

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
}
