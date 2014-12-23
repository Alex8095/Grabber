package com.frogorf.grabber.helper;

import com.frogorf.config.HibernateConfigTest;
import com.frogorf.realty.domain.Realty;
import com.frogorf.realty.domain.RealtyImage;
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
import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Alex on 23.12.14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {HibernateConfigTest.class})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@Transactional()
public class RealtyImageHelperTest {

    private static final Logger logger = LoggerFactory.getLogger(RealtyImageHelperTest.class);
    @Autowired
    private RealtyImageHelper realtyImageHelper;
    @Autowired
    private RealtyService realtyService;

    private Realty realty;

    private static List<String> IMAGES;
    private static String I_1 = "http://img24.olx.ua/images_slandocomua/170448393_4_644x461_prodaetsya-3-h-komn-lesnoy-volkova-bez-komissii-sobstvennik-nedvizhimost_rev016.jpg";
    private static String I_2 = "http://img22.olx.ua/images_slandocomua/170448393_1_644x461_prodaetsya-3-h-komn-lesnoy-volkova-bez-komissii-sobstvennik-kiev_rev016.jpg";
    private static String I_3 = "http://img22.olx.ua/images_slandocomua/170448393_3_644x461_prodaetsya-3-h-komn-lesnoy-volkova-bez-komissii-sobstvennik-vtorichnyy-rynok_rev016.jpg";

    @Before
    public void setUp() throws Exception {
        IMAGES = new ArrayList<>();
        IMAGES.add(I_1);
        IMAGES.add(I_2);
        IMAGES.add(I_3);

        realty = new Realty();
        realtyImageHelper.init(IMAGES, realty);
    }

    @Test
    public void testGetImages() throws Exception {
        List<RealtyImage> realtyImages = realtyImageHelper.getImages();
        assertEquals(realtyImages.size(), IMAGES.size());
        for (RealtyImage realtyImage : realtyImages) {
            assertNotNull(realtyImage.getSiteUrl());
            assertEquals(realtyImage.getRealty(), realty);
        }
    }

    @Test
    public void testCreateImage() throws Exception {
        RealtyImage realtyImage = realtyImageHelper.createImage(I_1);
        assertEquals(realtyImage.getRealty(), realty);
        assertEquals(realtyImage.getSiteUrl(), I_1);
    }

    @Test
    public void testCanAddImageToList() throws Exception {
        realty.setImages(realtyImageHelper.getImages());
        assertEquals(realty.getImages().size(), IMAGES.size());
        realtyImageHelper.init(IMAGES, realty);
        assertEquals(realtyImageHelper.getImages().size(), 0);
    }

    @Test
    public void testClean() throws Exception {
        realtyImageHelper.clean();
        assertNull(realtyImageHelper.getImages());
    }
}
