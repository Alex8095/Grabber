package com.frogorf.realty.domain;

/**
 * Created by Alex on 21.11.14.
 */

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

@Embeddable
@MappedSuperclass
public class RealtyLocale {

    @Column
    @NotNull
    private String title;
    @Column
    private String description;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public RealtyLocale(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public RealtyLocale() {
    }
}
