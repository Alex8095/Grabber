package com.frogorf.grabber.domain;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Created by Alex on 21.11.14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RestPhone {
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public RestPhone() {

    }

    public RestPhone(String value) {
        this.value = value;
    }
}
