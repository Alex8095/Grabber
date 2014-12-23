package com.frogorf.dictionary.domain.sync;

import com.frogorf.core.domain.BaseEntity;
import com.frogorf.dictionary.domain.Dictionary;
import com.frogorf.dictionary.domain.DictionaryValue;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "dictionary_sync")
public class DictionarySync extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "dictionary_id")
    private Dictionary dictionary;
    @Column(name = "site_dict_id")
    @NotNull
    private String siteDictId;
    @ManyToOne
    @JoinColumn(name = "state_id")
    private DictionaryValue state;
    @Column(name = "count_add")
    private int countAdd;
    @Column(name = "sync_date")
    private Date syncDate;
    @Column
    @Lob
    private String message;

    public Dictionary getDictionary() {
        return dictionary;
    }

    public void setDictionary(Dictionary dictionary) {
        this.dictionary = dictionary;
    }

    public String getSiteDictId() {
        return siteDictId;
    }

    public void setSiteDictId(String siteDictId) {
        this.siteDictId = siteDictId;
    }

    public DictionaryValue getState() {
        return state;
    }

    public void setState(DictionaryValue state) {
        this.state = state;
    }

    public int getCountAdd() {
        return countAdd;
    }

    public void setCountAdd(int countAdd) {
        this.countAdd = countAdd;
    }

    public Date getSyncDate() {
        return syncDate;
    }

    public void setSyncDate(Date syncDate) {
        this.syncDate = syncDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DictionarySync() {
    }
}
