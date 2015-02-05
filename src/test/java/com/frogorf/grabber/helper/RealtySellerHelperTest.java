package com.frogorf.grabber.helper;

import com.frogorf.config.HibernateConfigTest;
import com.frogorf.realty.domain.Seller;
import com.frogorf.realty.service.SellerService;
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

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

/**
 * Created by alex on 18.12.14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {HibernateConfigTest.class})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional()
public class RealtySellerHelperTest {

    private static final Logger logger = LoggerFactory.getLogger(RealtySellerHelperTest.class);

    private final static String SOURCE_SELLER_NAME = "Фамилия Имя ";
    private final static String SOURCE_PHONE_NUMBERS = "096 643 2892|096 643 2891";
    private final static String SOURCE_LINK = "http://olx.ua/list/user/KyPn/";

    private final static String F_N = "Имя";
    private final static String L_N = "Фамилия";
    private final static String PHONE_NUMBER_1 = "0966432892";
    private final static String PHONE_NUMBER_2 = "0966432891";
    private final static String SITE_CODE = "KyPn";
    private final static String SOURCE_PHONE_NUMBERS_HTML = "<spanclass=\\\"block\\\">0966432892<\\/span><spanclass=\\\"block\\\">0966432891<\\/span>";

    private Seller seller;
    private List<String> sellerPhones;

    @Autowired
    private RealtySellerHelper sellerHelper;
    @Autowired
    private SellerService sellerService;

    @Before
    public void setUp() throws Exception {
        sellerPhones = new ArrayList<>();
        sellerPhones.add(PHONE_NUMBER_1);
        sellerPhones.add(PHONE_NUMBER_2);

        seller = new Seller();
        seller.setLastName(L_N);
        seller.setFirstName(F_N);
        seller.setPatronymic("");
        seller.setSiteCode(SITE_CODE);
        seller.setPhoneNumbers(sellerPhones);

        sellerHelper.init(SOURCE_SELLER_NAME, SOURCE_PHONE_NUMBERS, SOURCE_LINK);
    }

    @Test
    public void testGetSeller() throws Exception {
        assertNull(sellerHelper.getSeller());
    }

    @Test
    public void testGetPhoneNumbers() throws Exception {
        List<String> phoneNumbers = sellerHelper.getPhoneNumbers();
        assertEquals(phoneNumbers.size(), 2);
        assertEquals(phoneNumbers.get(0), PHONE_NUMBER_1);
        assertEquals(phoneNumbers.get(1), PHONE_NUMBER_2);
    }

    @Test
    public void testGetPhoneNumbersHtml() throws Exception {
        sellerHelper.init(SOURCE_SELLER_NAME, SOURCE_PHONE_NUMBERS_HTML, SOURCE_LINK);
        List<String> phoneNumbers = sellerHelper.getPhoneNumbers();
        assertEquals(phoneNumbers.size(), 2);
        assertEquals(phoneNumbers.get(0), PHONE_NUMBER_1);
        assertEquals(phoneNumbers.get(1), PHONE_NUMBER_2);
    }


    @Test
    public void testGetLastName() throws Exception {
        assertEquals(L_N, sellerHelper.getLastName());
    }

    @Test
    public void testGetFirstName() throws Exception {
        assertEquals(F_N, sellerHelper.getFirstName());
    }

    @Test
    public void testGetPatronymic() throws Exception {
        assertEquals("", sellerHelper.getPatronymic());
    }

    @Test
    public void testGetSiteCode() throws Exception {
        assertEquals(SITE_CODE, sellerHelper.getSiteCode());
    }

    @Test
    public void testCreateSeller() throws Exception {
        Seller createSeller = sellerHelper.createSeller();
        assertEquals(createSeller.getLastName(), seller.getLastName());
        assertEquals(createSeller.getFirstName(), seller.getFirstName());
        assertEquals(createSeller.getPatronymic(), seller.getPatronymic());
        assertEquals(createSeller.getSiteCode(), seller.getSiteCode());
        assertEquals(createSeller.getPhoneNumbers().size(), seller.getPhoneNumbers().size());
    }

    @Test
    public void testFindSeller() throws Exception {
        Seller findSeller = sellerHelper.findSeller();
        assertNull(findSeller);
        sellerService.save(seller);
        findSeller = sellerHelper.findSeller();
        assertEquals(findSeller.getSiteCode(), SITE_CODE);
    }

    @Test
    public void testCheckedSellerPhoneNumbers() throws Exception {

    }

    @Test
    public void testGetResult() throws Exception {
        Seller resultSeller = sellerHelper.getResult();
        sellerService.save(resultSeller);
        assertEquals(resultSeller.getLastName(), seller.getLastName());
        assertEquals(resultSeller.getFirstName(), seller.getFirstName());
        assertEquals(resultSeller.getPatronymic(), seller.getPatronymic());
        assertEquals(resultSeller.getSiteCode(), seller.getSiteCode());
        assertEquals(resultSeller.getPhoneNumbers().size(), seller.getPhoneNumbers().size());
        sellerHelper.clean();
        sellerHelper.init(SOURCE_SELLER_NAME, SOURCE_PHONE_NUMBERS, SOURCE_LINK);
        resultSeller = sellerHelper.getResult();
        assertNotNull(resultSeller.getId());
        assertNotNull(sellerService.findByPhoneNumber(PHONE_NUMBER_1));
    }

    @Test
    public void testClean() throws Exception {
        sellerHelper.clean();
        assertNull(sellerHelper.getSeller());
        assertNull(sellerHelper.getSiteCode());
    }
}
