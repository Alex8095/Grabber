package com.frogorf.dictionary.domain;

import com.frogorf.core.domain.BaseEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "dictionary_sync")
public class DictionarySyncHistory extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "dictionary_sync_id")
    private DictionarySync dictionarySync;
    @Column
    private Date startDate;
    @Column
    private Date endDate;
    @ManyToOne
    @JoinColumn(name = "state_id")
    private DictionaryValue state;

    public DictionarySync getDictionarySync() {
        return dictionarySync;
    }

    public void setDictionarySync(DictionarySync dictionarySync) {
        this.dictionarySync = dictionarySync;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public DictionaryValue getState() {
        return state;
    }

    public void setState(DictionaryValue state) {
        this.state = state;
    }

    public DictionarySyncHistory() {
    }
}
