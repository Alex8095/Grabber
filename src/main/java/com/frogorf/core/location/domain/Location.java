package com.frogorf.core.location.domain;

import com.frogorf.dictionary.domain.Dictionary;
import com.frogorf.dictionary.domain.DictionaryValue;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class Location {

    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private DictionaryValue county;
    @ManyToOne
    @JoinColumn(name = "region_id")
    private DictionaryValue region;
    @ManyToOne
    @JoinColumn(name = "district_id")
    private DictionaryValue district;
    @ManyToOne
    @JoinColumn(name = "city_id")
    private DictionaryValue city;
    @ManyToOne
    @JoinColumn(name = "area_city_id")
    private DictionaryValue areaCity;
    @ManyToOne
    @JoinColumn(name = "district_city_id")
    private DictionaryValue districtCity;
    @ManyToOne
    @JoinColumn(name = "street_id")
    private DictionaryValue street;
    @Column(name = "house_number")
    private String houseNumber;
    @Column
    private String number;
    @Column(name = "site_location")
    private String siteLocation;

    public DictionaryValue getCounty() {
        return county;
    }

    public void setCounty(DictionaryValue county) {
        this.county = county;
    }

    public DictionaryValue getRegion() {
        return region;
    }

    public void setRegion(DictionaryValue region) {
        this.region = region;
    }

    public DictionaryValue getDistrict() {
        return district;
    }

    public void setDistrict(DictionaryValue district) {
        this.district = district;
    }

    public DictionaryValue getCity() {
        return city;
    }

    public void setCity(DictionaryValue city) {
        this.city = city;
    }

    public DictionaryValue getAreaCity() {
        return areaCity;
    }

    public void setAreaCity(DictionaryValue areaCity) {
        this.areaCity = areaCity;
    }

    public DictionaryValue getDistrictCity() {
        return districtCity;
    }

    public void setDistrictCity(DictionaryValue districtCity) {
        this.districtCity = districtCity;
    }

    public DictionaryValue getStreet() {
        return street;
    }

    public void setStreet(DictionaryValue street) {
        this.street = street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getSiteLocation() {
        return siteLocation;
    }

    public void setSiteLocation(String siteLocation) {
        this.siteLocation = siteLocation;
    }

    public Location() {
    }
}
