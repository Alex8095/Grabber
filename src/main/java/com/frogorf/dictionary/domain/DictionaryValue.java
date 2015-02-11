package com.frogorf.dictionary.domain;


import com.frogorf.core.domain.BaseEntity;
import com.frogorf.utils.Transliterator;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "dictionary_value")
public class DictionaryValue extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Column
    @NotNull
    private String code;
    public static final String PARAM_CODE = "code";

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "dictionary_id", nullable = false)
    private Dictionary dictionary;
    public static final String PARAM_DICTIONARY_ID = "dictionary_id";

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private DictionaryValue parentDictionaryValue;
    @ElementCollection(targetClass = DictionaryValueLocale.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "dictionary_value_locale", joinColumns = @JoinColumn(name = "dictionary_value_id"))
    @MapKeyColumn(name = "locale")
    @Fetch(FetchMode.SELECT)
    private Map<String, DictionaryValueLocale> locales;
    public static final String PARAM_SITE_CODE = "siteCode";
    @Column(name = "site_code")
    private String siteCode;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = Transliterator.transliterate(code).toUpperCase();
    }

    public Dictionary getDictionary() {
        return dictionary;
    }

    public void setDictionary(Dictionary dictionary) {
        this.dictionary = dictionary;
    }

    public DictionaryValue getParentDictionaryValue() {
        return parentDictionaryValue;
    }

    public void setParentDictionaryValue(DictionaryValue parentDictionaryValue) {
        this.parentDictionaryValue = parentDictionaryValue;
    }

    public Map<String, DictionaryValueLocale> getLocales() {
        return locales;
    }

    public void setLocales(Map<String, DictionaryValueLocale> locales) {
        this.locales = locales;
    }

    public String getName() {
        checkLocales();
        if (locales.containsKey(getLocaleLanguage()))
            return locales.get(getLocaleLanguage()).getName();
        else
            return "";
    }

    public void setName(String name) {
        checkLocales();
        if (!locales.containsKey(getLocaleLanguage())) {
            locales.put(getLocaleLanguage(), new DictionaryValueLocale());
        }
        locales.get(getLocaleLanguage()).setName(name);
    }

    public String getDescription() {
        checkLocales();
        if (locales.containsKey(getLocaleLanguage()))
            return locales.get(getLocaleLanguage()).getDescription();
        else
            return "";
    }

    public void setDescription(String description) {
        checkLocales();
        if (!locales.containsKey(getLocaleLanguage())) {
            locales.put(getLocaleLanguage(), new DictionaryValueLocale());
        }
        locales.get(getLocaleLanguage()).setDescription(description);
    }

    public String getSiteCode() {
        return siteCode;
    }

    public void setSiteCode(String siteCode) {
        this.siteCode = siteCode;
    }

    private void checkLocales() {
        if (locales == null) {
            locales = new HashMap<>();
        }
    }

    public DictionaryValue() {
    }

}
