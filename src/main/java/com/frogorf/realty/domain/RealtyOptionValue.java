package com.frogorf.realty.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.frogorf.core.domain.BaseEntity;
import com.frogorf.dictionary.domain.DictionaryValue;

import javax.persistence.*;
import java.util.List;


/**
 * Created by alex on 15.12.14.
 */
@Entity
@Table(name = "realty_option_value")
public class RealtyOptionValue extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "realty_id", nullable = false)
    private Realty realty;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "realty_option_id", nullable = false)
    private RealtyOption realtyOption;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "dictionary_id")
    private DictionaryValue dictionaryValue;
    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "realty_option_dictionary_value", joinColumns = {@JoinColumn(name = "realty_option_value_id", nullable = false, updatable = false)}, inverseJoinColumns = {@JoinColumn(name = "dictionary_id", nullable = false, updatable = false)})
    private List<DictionaryValue> dictionaryValues;
    @Column
    private String value;

    public Realty getRealty() {
        return realty;
    }

    public void setRealty(Realty realty) {
        this.realty = realty;
    }

    public RealtyOption getRealtyOption() {
        return realtyOption;
    }

    public void setRealtyOption(RealtyOption realtyOption) {
        this.realtyOption = realtyOption;
    }

    public DictionaryValue getDictionaryValue() {
        return dictionaryValue;
    }

    public void setDictionaryValue(DictionaryValue dictionaryValue) {
        this.dictionaryValue = dictionaryValue;
    }

    public List<DictionaryValue> getDictionaryValues() {
        return dictionaryValues;
    }

    public void setDictionaryValues(List<DictionaryValue> dictionaryValues) {
        this.dictionaryValues = dictionaryValues;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public RealtyOptionValue() {
    }

}
