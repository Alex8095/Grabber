package com.frogorf.grabber.helper;

import com.frogorf.realty.domain.Seller;

import java.util.List;

/**
 * Created by Alex on 16.12.14.
 */
public interface RealtySellerHelper {

    Seller getSeller();

    List<String> getPhoneNumbers();

    String getLastName();

    String getFirstName();

    String getPatronymic();

    String getSiteCode();

    void init(String sourceSellerName, String sourcePhoneNumbers, String sourceLink);

    Seller getResult();

    Seller createSeller();

    Seller findSeller();

    void checkedSellerPhoneNumbers();

    void clean();
}
