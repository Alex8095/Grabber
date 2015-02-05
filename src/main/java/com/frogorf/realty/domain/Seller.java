package com.frogorf.realty.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.frogorf.core.domain.BaseEntity;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "seller")
public class Seller extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Column(name = "last_name")
    private String lastName;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "patronymic")
    private String patronymic;
    @Column(name = "main_site_id")
    private Long mainSiteId;
    @Column(name = "is_realtor")
    private Boolean isRealtor;
    @Column(name = "site_code")
    private String siteCode;

    @LazyCollection(LazyCollectionOption.FALSE)
    @ElementCollection
    @CollectionTable(name = "seller_phone_number", joinColumns = @JoinColumn(name = "seller_id"))
    @Column(name = "phone_number")
    public List<String> phoneNumbers;
    @JsonIgnore
    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany(mappedBy = "seller", fetch = FetchType.EAGER)
    private List<Realty> realties;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public List<String> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(List<String> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public List<Realty> getRealties() {
        return realties;
    }

    public void setRealties(List<Realty> realties) {
        this.realties = realties;
    }

    public Long getMainSiteId() {
        return mainSiteId;
    }

    public void setMainSiteId(Long mainSiteId) {
        this.mainSiteId = mainSiteId;
    }

    public String getSiteCode() {
        return siteCode;
    }

    public void setSiteCode(String siteCode) {
        this.siteCode = siteCode;
    }

    public Boolean getIsRealtor() {
        return isRealtor;
    }

    public void setIsRealtor(Boolean isRealtor) {
        this.isRealtor = isRealtor;
    }

    public String getFullName() {
        StringBuilder sb = new StringBuilder();
        if (getLastName() != null && !getLastName().isEmpty()) {
            sb.append(getLastName());
        }
        if (getFirstName() != null && !getFirstName().isEmpty()) {
            sb.append(" ");
            sb.append(getFirstName());
        }
        if (getPatronymic() != null && !getPatronymic().isEmpty()) {
            sb.append(" ");
            sb.append(getPatronymic());
        }
        return sb.toString();
    }

    public String getShortFullName() {
        StringBuilder sb = new StringBuilder();
        if (getLastName() != null && !getLastName().isEmpty()) {
            sb.append(getLastName());
        }
        if (getFirstName() != null && !getFirstName().isEmpty()) {
            sb.append(" ");
            sb.append(getFirstName().substring(0, 1));
            sb.append(".");
        }
        if (getPatronymic() != null && !getPatronymic().isEmpty()) {
            sb.append(" ");
            sb.append(getPatronymic().substring(0, 1));
            sb.append(".");
        }
        return sb.toString();
    }

    public Seller() {
        isRealtor = false;
    }
}
