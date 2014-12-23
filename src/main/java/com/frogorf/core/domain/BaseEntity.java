package com.frogorf.core.domain;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Tsurkin Alex
 */
@MappedSuperclass
public class BaseEntity {

    private static final Locale DEFAULT_LOCALE = Locale.getDefault();

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public boolean isNew() {
        return (this.id == null);
    }

    public Locale getLocale() {
        return DEFAULT_LOCALE;
    }

    public String getLocaleLanguage() {
        return DEFAULT_LOCALE.getLanguage();
    }

}
