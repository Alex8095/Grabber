package com.frogorf.grabber.domain;

import com.frogorf.core.domain.BaseEntity;
import com.frogorf.core.location.domain.Location;
import com.frogorf.dictionary.domain.DictionaryValue;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "task")
public class Task extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Column
    @NotNull
    private String url;
    @Embedded
    @Fetch(FetchMode.SELECT)
    private Location location;
    @Column(name = "is_sale")
    private Boolean isSale;
    @Column(name = "is_rent")
    private Boolean isRent;
    @Column(name = "is_change")
    private Boolean isChange;
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private DictionaryValue ownerType;
    @ManyToOne
    @JoinColumn(name = "implementation_id")
    private DictionaryValue implementationType;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public DictionaryValue getOwnerType() {
        return ownerType;
    }

    public void setOwnerType(DictionaryValue ownerType) {
        this.ownerType = ownerType;
    }

    public DictionaryValue getImplementationType() {
        return implementationType;
    }

    public void setImplementationType(DictionaryValue implementationType) {
        this.implementationType = implementationType;
    }

    public Boolean getIsSale() {
        return isSale;
    }

    public void setIsSale(Boolean isSale) {
        this.isSale = isSale;
    }

    public Boolean getIsRent() {
        return isRent;
    }

    public void setIsRent(Boolean isRent) {
        this.isRent = isRent;
    }

    public Boolean getIsChange() {
        return isChange;
    }

    public void setIsChange(Boolean isChange) {
        this.isChange = isChange;
    }

    public Task() {
    }
}
