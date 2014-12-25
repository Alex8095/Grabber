package com.frogorf.realty.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.frogorf.core.domain.BaseEntity;
import com.frogorf.core.location.domain.Location;
import com.frogorf.dictionary.domain.DictionaryValue;
import com.frogorf.utils.Translit;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "realty")
public class Realty extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Embedded
    private Location location;
    @ManyToOne
    @JoinColumn(name = "owner_type_id")
    private DictionaryValue ownerType;
    @ManyToOne
    @JoinColumn(name = "implementation_type_id")
    private DictionaryValue implementationType;
    @Column(name = "is_sale")
    private Boolean isSale;
    @Column(name = "is_rent")
    private Boolean isRent;
    @Column(name = "is_change")
    private Boolean isChange;
    @Column(name = "price")
    private Long price;
    @Column(name = "date_publishing")
    private Date datePublishing;
    @Column(name = "date_create")
    private Date dateCreate;
    @Column(name = "date_last_update")
    private Date dateLastUpdate;
    @Column(name = "site_code")
    private String siteCode;
    public final static String SITE_CODE_PARAM = "siteCode";
    @Column(name = "site_url")
    private String siteUrl;
    public final static String SITE_URL_PARAM = "siteUrl";
    @Column(name = "main_site_code")
    private String mainSiteCode;
    public final static String MAIN_SITE_CODE_PARAM = "mainSiteCode";
    @Column(name = "date_up_to_main_site")
    private Date dateUpToMainSite;
    @Column(name = "date_last_update_to_main_site")
    private Date dateLastUpdateToMainSite;
//    public final static String TITLE_PARAM = "locales";
    @ElementCollection(targetClass = RealtyLocale.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "realty_locale", joinColumns = @JoinColumn(name = "realty_id"))
    @MapKeyColumn(name = "locale")
    @Fetch(FetchMode.SELECT)
    private Map<String, RealtyLocale> locales;
    @ManyToOne
    @JoinColumn(name = "currency_id")
    private DictionaryValue currency;
    @JsonIgnore
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "realty")
    private List<RealtyHistoryPrice> realtyHistoryPrices;
    @JsonIgnore
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "realty")
    private List<RealtyHistory> realtyHistory;
    @JsonIgnore
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "realty")
    private List<RealtyImage> images;
    @JsonIgnore
    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "realty_seller", joinColumns = {@JoinColumn(name = "realty_id", nullable = false, updatable = false)}, inverseJoinColumns = {@JoinColumn(name = "seller_id", nullable = false, updatable = false)})
    private List<Seller> sellers;
    @ManyToOne
    @JoinColumn(name = "realty_seller_id")
    private Seller seller;
    @JsonIgnore
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "realty")
    private List<RealtyOptionValue> realtyOptionValues;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
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

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Date getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(Date dateCreate) {
        this.dateCreate = dateCreate;
    }

    public Date getDateLastUpdate() {
        return dateLastUpdate;
    }

    public void setDateLastUpdate(Date dateLastUpdate) {
        this.dateLastUpdate = dateLastUpdate;
    }

    public String getSiteCode() {
        return siteCode;
    }

    public void setSiteCode(String siteCode) {
        this.siteCode = Translit.toTranslit(siteCode).toLowerCase();
    }

    public String getSiteUrl() {
        return siteUrl;
    }

    public void setSiteUrl(String siteUrl) {
        this.siteUrl = siteUrl;
    }

    public String getMainSiteCode() {
        return mainSiteCode;
    }

    public void setMainSiteCode(String mainSiteCode) {
        this.mainSiteCode = Translit.toTranslit(mainSiteCode).toLowerCase();
    }

    public Date getDateUpToMainSite() {
        return dateUpToMainSite;
    }

    public void setDateUpToMainSite(Date dateUpToMainSite) {
        this.dateUpToMainSite = dateUpToMainSite;
    }

    public Date getDateLastUpdateToMainSite() {
        return dateLastUpdateToMainSite;
    }

    public void setDateLastUpdateToMainSite(Date dateLastUpdateToMainSite) {
        this.dateLastUpdateToMainSite = dateLastUpdateToMainSite;
    }

    public Map<String, RealtyLocale> getLocales() {
        return locales;
    }

    public void setLocales(Map<String, RealtyLocale> locales) {
        this.locales = locales;
    }

    public DictionaryValue getCurrency() {
        return currency;
    }

    public void setCurrency(DictionaryValue currency) {
        this.currency = currency;
    }

    public String getTitle() {
        if (locales.containsKey(getLocaleLanguage()))
            return locales.get(getLocaleLanguage()).getTitle();
        else
            return "";
    }

    public void setTitle(String title) {
        if (locales.containsKey(getLocaleLanguage())) {
            locales.get(getLocaleLanguage()).setTitle(title);
        }
    }

    public String getDescription() {
        if (locales.containsKey(getLocaleLanguage()))
            return locales.get(getLocaleLanguage()).getDescription();
        else
            return "";
    }

    public void setDescription(String description) {
        if (locales.containsKey(getLocaleLanguage())) {
            locales.get(getLocaleLanguage()).setDescription(description);
        }
    }

    public List<RealtyHistoryPrice> getRealtyHistoryPrices() {
        return realtyHistoryPrices;
    }

    public void setRealtyHistoryPrices(List<RealtyHistoryPrice> realtyHistoryPrices) {
        this.realtyHistoryPrices = realtyHistoryPrices;
    }

    public List<RealtyHistory> getRealtyHistory() {
        return realtyHistory;
    }

    public void setRealtyHistory(List<RealtyHistory> realtyHistory) {
        this.realtyHistory = realtyHistory;
    }

    public List<RealtyImage> getImages() {
        return images;
    }

    public void setImages(List<RealtyImage> images) {
        this.images = images;
    }

    public List<Seller> getSellers() {
        return sellers;
    }

    public void setSellers(List<Seller> sellers) {
        this.sellers = sellers;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public List<RealtyOptionValue> getRealtyOptionValues() {
        return realtyOptionValues;
    }

    public void setRealtyOptionValues(List<RealtyOptionValue> realtyOptionValues) {
        this.realtyOptionValues = realtyOptionValues;
    }

    public Date getDatePublishing() {
        return datePublishing;
    }

    public void setDatePublishing(Date datePublishing) {
        this.datePublishing = datePublishing;
    }

    public Realty() {
        dateCreate = new Date();
        dateLastUpdate = new Date();
        locales = new HashMap<>();
    }
}
