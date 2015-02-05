package com.frogorf.realty.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.frogorf.core.domain.BaseEntity;
import com.frogorf.dictionary.domain.DictionaryValue;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Alex on 21.11.14.
 */
@Entity
@Table(name = "Realty_history_price")
public class RealtyHistoryPrice extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Column
    private Long price;
    @ManyToOne
    @JoinColumn(name = "currency_id")
    private DictionaryValue currency;
    @Column(name = "date_action")
    private Date dateAction;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "realty_id", nullable = false)
    private Realty realty;

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public DictionaryValue getCurrency() {
        return currency;
    }

    public void setCurrency(DictionaryValue currency) {
        this.currency = currency;
    }

    public Date getDateAction() {
        return dateAction;
    }

    public void setDateAction(Date dateAction) {
        this.dateAction = dateAction;
    }

    public Realty getRealty() {
        return realty;
    }

    public void setRealty(Realty realty) {
        this.realty = realty;
    }

    public RealtyHistoryPrice() {
        dateAction = new Date();
    }

    public RealtyHistoryPrice(Long price, DictionaryValue currency, Realty realty) {
        this.price = price;
        this.currency = currency;
        this.realty = realty;
    }
}