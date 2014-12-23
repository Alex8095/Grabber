package com.frogorf.dictionary.domain;

import com.sun.istack.NotNull;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;

/**
 * Created by Alex on 15.11.14.
 */
@Embeddable
@MappedSuperclass
public class DictionaryValueLocale {

    @Column
    @NotNull
    private String name;

    @Column
    private String description;

    public DictionaryValueLocale() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public DictionaryValueLocale(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
