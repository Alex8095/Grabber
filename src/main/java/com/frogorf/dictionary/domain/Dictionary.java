package com.frogorf.dictionary.domain;

import com.frogorf.core.domain.BaseEntity;
import com.frogorf.utils.Transliterator;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "dictionary")
public class Dictionary extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Column
    @NotEmpty
    private String name;
    @Column
    @NotNull
    public static final String PARAM_CODE = "code";
    private String code;
    @Column
    private String description;
    @Column
    private String lang;

    /* parent */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_id")
    private Dictionary dictionary;

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
        this.code = Transliterator.transliterate(code).toUpperCase();
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

    public Dictionary() {

    }

    public Dictionary(String name, String code, String description, String lang) {
        super();
        this.name = name;
        this.code = code;
        this.description = description;
        this.lang = lang;
    }
}
