package com.frogorf.grabber.helper.impl;

import com.frogorf.grabber.helper.RealtySellerHelper;
import com.frogorf.realty.domain.Seller;
import com.frogorf.realty.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 16.12.14.
 */
@Service("realtySellerHelperService")
public class RealtySellerHelperImpl implements RealtySellerHelper {

    @Autowired
    private SellerService sellerService;

    private Seller seller;
    private String siteCode;
    private List<String> phoneNumbers;

    private String sourceSellerName;
    private String sourcePhoneNumbers;
    private String sourceLink;

    @Override
    public Seller getSeller() {
        return seller;
    }

    @Override
    public List<String> getPhoneNumbers() {
        if (phoneNumbers == null) {
            phoneNumbers = new ArrayList<>();
            if (sourcePhoneNumbers.length() > 12) {
                for (String n : sourcePhoneNumbers.split("\\|")) {
                    phoneNumbers.add(cleanNumber(n));
                }
            } else {
                phoneNumbers.add(cleanNumber(sourcePhoneNumbers));
            }
        }
        return phoneNumbers;
    }

    @Override
    public String getLastName() {
        String[] words = sourceSellerName.split(" ");
        return words.length == 3 || words.length == 2 ? words[0] : "";
    }

    @Override
    public String getFirstName() {
        String[] words = sourceSellerName.split(" ");
        return words.length >= 2 ? words[1] : words[0];
    }

    @Override
    public String getPatronymic() {
        String[] words = sourceSellerName.split(" ");
        return words.length == 3 ? words[2] : "";
    }

    @Override
    public String getSiteCode() {
        if (siteCode == null && sourceLink != null) {
            siteCode = sourceLink.substring(sourceLink.lastIndexOf("user") + 5, sourceLink.lastIndexOf("/"));
        }
        return siteCode;
    }

    @Override
    public void init(String sourceSellerName, String sourcePhoneNumbers, String sourceLink) {
        clean();
        this.sourceSellerName = sourceSellerName;
        this.sourcePhoneNumbers = sourcePhoneNumbers;
        this.sourceLink = sourceLink;
    }

    @Override
    public Seller getResult() {
        seller = findSeller();
        if (seller == null) {
            seller = createSeller();
        } else {
            checkedSellerPhoneNumbers();
        }
        return seller;
    }

    @Override
    public Seller createSeller() {
        Seller createdSeller = new Seller();
        createdSeller.setLastName(getLastName());
        createdSeller.setFirstName(getFirstName());
        createdSeller.setPatronymic(getPatronymic());
        createdSeller.setSiteCode(getSiteCode());
        createdSeller.setPhoneNumbers(getPhoneNumbers());
        return createdSeller;
    }

    @Override
    public Seller findSeller() {
        Seller searchingSeller = sellerService.findBySiteCode(getSiteCode());
        if (searchingSeller == null) {
            for (String phone : getPhoneNumbers()) {
                searchingSeller = sellerService.findByPhoneNumber(phone);
                if (searchingSeller != null) {
                    return searchingSeller;
                }
            }
        }
        return searchingSeller;
    }

    @Override
    public void checkedSellerPhoneNumbers() {
        for (String phoneNumber : getPhoneNumbers()) {
            if (!isHaveSellerPhoneNumber(phoneNumber)) {
                seller.getPhoneNumbers().add(phoneNumber);
            }
        }
    }

    @Override
    public void clean() {
        sourceSellerName = null;
        sourcePhoneNumbers = null;
        sourceLink = null;
        seller = null;
        phoneNumbers = null;
        siteCode = null;
    }

    private boolean isHaveSellerPhoneNumber(String phoneNumber) {
        for (String pn : seller.getPhoneNumbers()) {
            if (pn.equals(phoneNumber)) {
                return true;
            }
        }
        return false;
    }

    private String cleanNumber(String n) {
        return n.replace(" ", "").replace("-", "");
    }

}