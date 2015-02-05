package com.frogorf.realty.domain;

import com.frogorf.core.domain.BaseEntity;
import com.frogorf.dictionary.domain.Dictionary;
import com.frogorf.dictionary.domain.DictionaryValue;
import com.frogorf.utils.Translit;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.transaction.Transaction;
import java.util.List;

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
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "realtyOption")
    private List<RealtyOptionCompliance> realtyOptionCompliances;

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
        this.code = Translit.toTranslit(code).toUpperCase();
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

    public List<RealtyOptionCompliance> getRealtyOptionCompliances() {
        return realtyOptionCompliances;
    }

    public void setRealtyOptionCompliances(List<RealtyOptionCompliance> realtyOptionCompliances) {
        this.realtyOptionCompliances = realtyOptionCompliances;
    }

    public RealtyOption() {
    }
}