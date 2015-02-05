package com.frogorf.grabber.domain;

import com.frogorf.core.domain.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "source_setting")
public class SourceSetting extends BaseEntity {

    private static final long serialVersionUID = 1L;
    public static String SITE_CODE = "site_code";
    public static String NAME = "name";

    @Column(name = "site_code")
    @NotNull
    private String siteCode;
    @NotNull
    private String name;
    @NotNull
    @Lob
    private String value;

    public String getSiteCode() {
        return siteCode;
    }

    public void setSiteCode(String siteCode) {
        if (siteCode != null) {
            this.siteCode = siteCode.toUpperCase();
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public SourceSetting() {
    }
}
