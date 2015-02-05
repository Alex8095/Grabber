package com.frogorf.grabber.helper;

import com.frogorf.config.HibernateConfigTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by Alex on 28.01.15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {HibernateConfigTest.class})
public class RealtyOptionCodeReaderTest {

    private static final Logger logger = LoggerFactory.getLogger(RealtyOptionCodeReaderTest.class);

    @Autowired
    private RealtyOptionCodeReader realtyOptionCodeReader;

    @Test
    public void testReadFile() throws Exception {
    }

    @Test
    public void testGetItem() throws Exception {
        String code = null;
        logger.info("codes created status: {} {}", "tes", "2");

        String[] codes = realtyOptionCodeReader.getItem("Item");
        assertNull(codes);
        codes = realtyOptionCodeReader.getItem("TOTAL_SPACE_CODES");
        assertEquals(codes.length, 4);
    }
}
