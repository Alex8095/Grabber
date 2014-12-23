package com.frogorf.dictionary.service;

import com.frogorf.dictionary.domain.Dictionary;
import com.frogorf.dictionary.domain.DictionaryValue;
import com.frogorf.dictionary.domain.sync.DictionarySync;
import com.frogorf.dictionary.domain.sync.DictionarySyncResponse;

import java.util.List;

/**
 * Created by Alex on 03.12.14.
 */
public interface DictionaryValueSynchronize {

    DictionarySync getDictionarySync();

    void getDictionarySync(int id);

    DictionaryValue getLastDictionaryValue(Dictionary dictionary);

    DictionaryValue getParentDictionaryValue(String siteCode);

    DictionarySyncResponse execute();

    List<DictionaryValue> parseList(DictionarySyncResponse response);

}
