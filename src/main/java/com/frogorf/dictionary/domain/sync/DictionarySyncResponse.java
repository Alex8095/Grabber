package com.frogorf.dictionary.domain.sync;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Alex on 30.11.14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DictionarySyncResponse implements Serializable {
    public Boolean success = false;
    public List<DictionaryValueResponse> data;
}
