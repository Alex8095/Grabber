package com.frogorf.realty.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.frogorf.core.domain.BaseEntity;

import javax.persistence.*;

/**
 * Created by Alex on 25.01.15.
 */
@Entity
@Table(name = "realty_option_compliance")
public class RealtyOptionCompliance extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Column(name = "implementation_code")
    private String implementationCode;
    @Column(name = "site_code")
    private String siteCode;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "realty_option_id", nullable = false)
    private RealtyOption realtyOption;

    public String getImplementationCode() {
        return implementationCode;
    }

    public void setImplementationCode(String implementationCode) {
        this.implementationCode = implementationCode;
    }

    public String getSiteCode() {
        return siteCode;
    }

    public void setSiteCode(String siteCode) {
        this.siteCode = siteCode;
    }

    public RealtyOption getRealtyOption() {
        return realtyOption;
    }

    public void setRealtyOption(RealtyOption realtyOption) {
        this.realtyOption = realtyOption;
    }

    public RealtyOptionCompliance() {
    }
}
