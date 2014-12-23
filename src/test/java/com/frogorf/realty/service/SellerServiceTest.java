package com.frogorf.realty.service;

import com.frogorf.config.HibernateConfigTest;
import com.frogorf.realty.domain.Seller;
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
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by alex on 15.12.14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {HibernateConfigTest.class})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@Transactional()
public class SellerServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(SellerServiceTest.class);

    @Autowired
    private SellerService sellerService;

    private Seller seller1;
    private Seller seller2;
    private final static String S_LAST_NAME = "s_last_name";
    private final static String S_FIRST_NAME = "s_first_name";
    private final static String S_PATRONYMIC = "s_patronymic";
    private final static Boolean S_IS_REALTOR = false;
    private final static String S_LAST_NAME_2 = "s_last_name_2";
    private final static String S_FIRST_NAME_2 = "s_first_name_2";
    private final static String S_PATRONYMIC_2 = "s_patronymic_2";
    private final static Boolean S_IS_REALTOR_2 = true;
    private final static String S_SITE_CODE = "site_code";
    private final static String S_PHONE_1 = "1";
    private final static String S_PHONE_2 = "2";
    private final static List<String> S_PHONES;

    static {
        ArrayList<String> s_phones = new ArrayList<String>();
        s_phones.add(SellerServiceTest.S_PHONE_1);
        s_phones.add(SellerServiceTest.S_PHONE_2);
        S_PHONES = Collections.unmodifiableList(s_phones);
    }

    @Before
    public void setUp() throws Exception {
        seller1 = new Seller();
        seller1.setLastName(S_LAST_NAME);
        seller1.setFirstName(S_FIRST_NAME);
        seller1.setPatronymic(S_PATRONYMIC);
        seller1.setIsRealtor(S_IS_REALTOR);
        seller1.setPhoneNumbers(S_PHONES);
        seller1.setSiteCode(S_SITE_CODE);

        seller2 = new Seller();
        seller2.setLastName(S_LAST_NAME_2);
        seller2.setFirstName(S_FIRST_NAME_2);
        seller2.setPatronymic(S_PATRONYMIC_2);
        seller2.setIsRealtor(S_IS_REALTOR_2);
    }

    @Test
    public void testSeller() throws Exception {
        assertEquals(seller1.getFirstName(), S_FIRST_NAME);
        assertEquals(seller1.getLastName(), S_LAST_NAME);
        assertEquals(seller1.getPatronymic(), S_PATRONYMIC);
        assertEquals(seller1.getIsRealtor(), S_IS_REALTOR);
        assertNotNull(seller1.getPhoneNumbers());
        assertEquals(seller1.getPhoneNumbers().size(), S_PHONES.size());
        assertNull(seller2.getPhoneNumbers());
    }


    @Test
    public void testFindById() throws Exception {
        sellerService.save(seller1);
        Seller seller = sellerService.findById(seller1.getId());
        assertNotNull(seller);
        assertEquals(seller.getFirstName(), S_FIRST_NAME);
    }

    @Test
    public void testFindByPhoneNumber() throws Exception {
        sellerService.save(seller1);
        Seller seller = sellerService.findByPhoneNumber(S_PHONE_1);
        assertNotNull(seller);
        assertEquals(seller.getFirstName(), S_FIRST_NAME);
        Seller sellerNoPhone = sellerService.findByPhoneNumber("");
        assertNull(sellerNoPhone);
    }

    @Test
    public void testFindBySiteCode() throws Exception {
        sellerService.save(seller1);
        Seller seller = sellerService.findBySiteCode(S_SITE_CODE);
        assertEquals(seller.getSiteCode(), S_SITE_CODE);
        seller = sellerService.findBySiteCode("test");
        assertNull(seller);
    }


    @Test
    public void testSave() throws Exception {
        sellerService.save(seller1);
        assertNotNull(seller1.getId());
    }

    @Test
    public void testDelete() throws Exception {
        sellerService.save(seller1);
        assertNotNull(seller1.getId());
        Integer sellerId = seller1.getId();
        sellerService.delete(sellerId);
        assertNull(sellerService.findById(sellerId));
    }

    @Test
    public void testGetDataSourceList() throws Exception {
        sellerService.save(seller1);
        sellerService.save(seller2);
    }
    //Busty Teachers And Students Love Fucking Hardcore clip-01
}
