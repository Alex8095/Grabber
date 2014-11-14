package com.frogorf.dictionary.domain;

import com.frogorf.domain.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "dictionary_value")
public class DictionaryValue extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Column
    @NotNull
    private String name;
    @Column
    @NotNull
    private String code;
    @Column
    private String description;
    @Column
    private String lang;
    @ManyToOne
    @NotNull
    @JoinColumn(name = "dictionary_id")
    private Dictionary dictionary;
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private DictionaryValue dictionaryValue;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "dictionary_value", joinColumns = @JoinColumn(name = "id"), inverseJoinColumns = @JoinColumn(name = "parent_id"))
    private List<DictionaryValue> dictionaryValues;

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
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public Dictionary getDictionary() {
        return dictionary;
    }

    public void setDictionary(Dictionary dictionary) {
        this.dictionary = dictionary;
    }

    public DictionaryValue getDictionaryValue() {
        return dictionaryValue;
    }

    public void setDictionaryValue(DictionaryValue dictionaryValue) {
        this.dictionaryValue = dictionaryValue;
    }

    public List<DictionaryValue> getDictionaryValues() {
        if (this.dictionaryValues == null) {
            this.dictionaryValues = new ArrayList<DictionaryValue>();
        }
        return dictionaryValues;
    }

    public void setDictionaryValues(List<DictionaryValue> dictionaryValues) {
        this.dictionaryValues = dictionaryValues;
    }

    public DictionaryValue() {

    }

    public DictionaryValue(String name, String code, String description, String lang, Dictionary dictionary, DictionaryValue dictionaryValue, List<DictionaryValue> dictionaryValues) {
        super();
        this.name = name;
        this.code = code;
        this.description = description;
        this.lang = lang;
        this.dictionary = dictionary;
        this.dictionaryValue = dictionaryValue;
        this.dictionaryValues = dictionaryValues;
    }

    @Override
    public String toString() {
        return String.format("DictionaryValue[id=%s, name='%s', code='%s', description='%s', lang='%s', dictionary=%s, dictionaryValue=%s, dictionaryValue.size=%s]", id, name, code, description,
                lang, dictionary, dictionaryValue, getDictionaryValues().size());
    }
}
