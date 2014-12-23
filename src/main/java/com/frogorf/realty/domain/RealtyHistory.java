package com.frogorf.realty.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.frogorf.core.domain.BaseEntity;
import com.frogorf.dictionary.domain.DictionaryValue;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "realty_history")
public class RealtyHistory extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "action_id")
    private DictionaryValue action;
    @Column(name = "date_action")
    private Date dateAction;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "realty_id", nullable = false)
    private Realty realty;

    public DictionaryValue getAction() {
        return action;
    }

    public void setAction(DictionaryValue action) {
        this.action = action;
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

    public RealtyHistory() {
        dateAction = new Date();
    }

    public RealtyHistory(Realty realty, DictionaryValue action) {
        this.realty = realty;
        this.action = action;
    }
}