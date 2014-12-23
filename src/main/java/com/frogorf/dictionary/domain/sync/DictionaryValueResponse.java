package com.frogorf.dictionary.domain.sync;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by Alex on 30.11.14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DictionaryValueResponse implements Serializable {

    private String dict_id;
    private int ld_id;
    private int lang_id;
    private String dict_code;
    private String dict_name;
    private String parent_id;

    public String getDict_id() {
        return dict_id;
    }

    public void setDict_id(String dict_id) {
        this.dict_id = dict_id;
    }

    public int getLd_id() {
        return ld_id;
    }

    public void setLd_id(int ld_id) {
        this.ld_id = ld_id;
    }

    public int getLang_id() {
        return lang_id;
    }

    public void setLang_id(int lang_id) {
        this.lang_id = lang_id;
    }

    public String getDict_code() {
        return dict_code;
    }

    public void setDict_code(String dict_code) {
        this.dict_code = dict_code;
    }

    public String getDict_name() {
        return dict_name;
    }

    public void setDict_name(String dict_name) {
        this.dict_name = dict_name;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public DictionaryValueResponse() {
    }
}
