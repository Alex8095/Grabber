package com.frogorf.realty.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.frogorf.core.domain.BaseEntity;
import com.frogorf.dictionary.domain.DictionaryValue;

import javax.persistence.*;

/**
 * Created by Alex on 23.11.14.
 */
@Entity
@Table(name = "realty_image")
public class RealtyImage extends BaseEntity {

    @Column(name = "site_url")
    private String siteUrl;
    @Column
    private String width;
    @Column
    private String height;
    @Column
    private String description;
    @ManyToOne
    @JoinColumn(name = "type_id")
    private DictionaryValue type;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "realty_id", nullable = false)
    private Realty realty;

    public String getSiteUrl() {
        return siteUrl;
    }

    public void setSiteUrl(String siteUrl) {
        this.siteUrl = siteUrl;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public DictionaryValue getType() {
        return type;
    }

    public void setType(DictionaryValue type) {
        this.type = type;
    }

    public Realty getRealty() {
        return realty;
    }

    public void setRealty(Realty realty) {
        this.realty = realty;
    }

    public RealtyImage() {

    }

    public RealtyImage(String siteUrl) {
        this.siteUrl = siteUrl;
    }
}
