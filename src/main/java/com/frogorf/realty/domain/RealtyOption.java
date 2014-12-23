package com.frogorf.realty.domain;

import com.frogorf.core.domain.BaseEntity;
import com.frogorf.dictionary.domain.Dictionary;
import com.frogorf.dictionary.domain.DictionaryValue;

import javax.persistence.*;

@Entity
@Table(name = "realty_option")
public class RealtyOption extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Column
    private String name;
    @Column
    private String code;
    public static final String PARAM_CODE = "code";
    @Column(name = "site_code")
    private String siteCode;

    @ManyToOne
    @JoinColumn(name = "dictionary_id")
    private Dictionary dictionary;
    @ManyToOne
    @JoinColumn(name = "implementation_type_id")
    private DictionaryValue implementationType;
    @ManyToOne
    @JoinColumn(name = "site_id")
    private DictionaryValue site;
    @Column(name = "after_value")
    private String afterValue;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code.toUpperCase();
    }

    public String getSiteCode() {
        return siteCode;
    }

    public void setSiteCode(String siteCode) {
        this.siteCode = siteCode;
    }

    public Dictionary getDictionary() {
        return dictionary;
    }

    public void setDictionary(Dictionary dictionary) {
        this.dictionary = dictionary;
    }

    public DictionaryValue getImplementationType() {
        return implementationType;
    }

    public void setImplementationType(DictionaryValue implementationType) {
        this.implementationType = implementationType;
    }

    public DictionaryValue getSite() {
        return site;
    }

    public void setSite(DictionaryValue site) {
        this.site = site;
    }

    public String getAfterValue() {
        return afterValue;
    }

    public void setAfterValue(String afterValue) {
        this.afterValue = afterValue;
    }

    public RealtyOption() {
    }
}