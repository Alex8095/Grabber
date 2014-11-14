package com.frogorf.dictionary.domain;

import com.frogorf.domain.BaseEntity;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "dictionary")
public class Dictionary extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Column
    @NotEmpty
    private String name;
    @Column
    @NotNull
    private String code;
    @Column
    private String description;
    @Column
    private String lang;
    @OneToMany(mappedBy = "dictionary", fetch = FetchType.EAGER)
    private List<DictionaryValue> dictionaryValues;

    /* parent */
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Dictionary dictionary;
    @ManyToMany
    @JoinTable(name = "dictionary", joinColumns = @JoinColumn(name = "id"), inverseJoinColumns = @JoinColumn(name = "parent_id"))
    private List<Dictionary> dictionarys;

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

    /**
     * @return the dictionaryValues
     */
    public List<DictionaryValue> getDictionaryValues() {
        if (this.dictionaryValues == null)
            this.dictionaryValues = new ArrayList<DictionaryValue>();
        return this.dictionaryValues;
    }

    public void setDictionaryValues(List<DictionaryValue> dictionaryValues) {
        this.dictionaryValues = dictionaryValues;
    }

    public Dictionary getDictionary() {
        return dictionary;
    }

    public void setDictionary(Dictionary dictionary) {
        this.dictionary = dictionary;
    }

    public List<Dictionary> getDictionarys() {
        return dictionarys;
    }

    public void setDictionarys(List<Dictionary> dictionarys) {
        this.dictionarys = dictionarys;
    }

    public Dictionary() {

    }

    public Dictionary(String name, String code, String description, String lang, List<DictionaryValue> dictionaryValues) {
        super();
        this.name = name;
        this.code = code;
        this.description = description;
        this.lang = lang;
        this.dictionaryValues = dictionaryValues;
    }

    @Override
    public String toString() {
        return String.format("Dictionary[id=%s, name='%s', code='%s', description='%s', lang='%s', dictionaryValues.size=%s]", id, name, code, description, lang, getDictionaryValues().size());
    }
}
