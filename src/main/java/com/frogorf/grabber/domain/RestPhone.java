package com.frogorf.grabber.domain;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Created by Alex on 21.11.14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RestPhone {
    public String value;
}
