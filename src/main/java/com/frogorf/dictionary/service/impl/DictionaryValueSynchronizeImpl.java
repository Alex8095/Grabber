package com.frogorf.dictionary.service.impl;

import com.frogorf.dictionary.data.State;
import com.frogorf.dictionary.domain.Dictionary;
import com.frogorf.dictionary.domain.DictionaryValue;
import com.frogorf.dictionary.domain.DictionaryValueLocale;
import com.frogorf.dictionary.domain.sync.DictionarySync;
import com.frogorf.dictionary.domain.sync.DictionarySyncResponse;
import com.frogorf.dictionary.domain.sync.DictionaryValueResponse;
import com.frogorf.dictionary.service.DictionaryService;
import com.frogorf.dictionary.service.DictionarySyncService;
import com.frogorf.dictionary.service.DictionaryValueSynchronize;
import com.frogorf.kendo.data.source.DataSourceRequest;
import com.frogorf.kendo.data.source.DataSourceResult;
import com.frogorf.utils.Translit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

/**
 * Created by Alex on 03.12.14.
 */
@Service("ditionaryValueSynchronize")
public class DictionaryValueSynchronizeImpl implements DictionaryValueSynchronize {

    private static final Logger logger = LoggerFactory.getLogger(DictionaryValueSynchronizeImpl.class);
    private static String URL_TEMPLATE = "http://alfabrok.ua/rest.php?action=dictionaryvalues&startdictid=%s&ld_id=%s";

    @Autowired
    private DictionaryService dictionaryService;
    @Autowired
    private DictionarySyncService dictionarySyncService;

    private DictionarySync dictionarySync;

    @Override
    public DictionarySync getDictionarySync() {
        return dictionarySync;
    }

    @Override
    public void getDictionarySync(int id) {
        dictionarySync = dictionarySyncService.findDictionarySyncById(id);
        dictionarySync.setState(dictionaryService.findDictionaryValueById(State.START));
        dictionarySync.setSyncDate(new Date());
        dictionarySync.setMessage("");
    }

    @Override
    public DictionaryValue getLastDictionaryValue(Dictionary dictionary) {
        //SORT
        List<DataSourceRequest.SortDescriptor> sort = new ArrayList<>();
        DataSourceRequest.SortDescriptor sortDescriptor = new DataSourceRequest.SortDescriptor();
        sortDescriptor.setField("siteCode");
        sortDescriptor.setDir("desc");
        sort.add(sortDescriptor);
        //FILTER
        DataSourceRequest.FilterDescriptor filterDescriptor = new DataSourceRequest.FilterDescriptor();
        DataSourceRequest.FilterDescriptor filterDescriptor1 = new DataSourceRequest.FilterDescriptor();
        filterDescriptor1.setField("dictionary.id");
        filterDescriptor1.setValue(dictionarySync.getDictionary().getId());
        filterDescriptor1.setOperator("eq");
        filterDescriptor.setLogic("and");
        filterDescriptor.getFilters().add(filterDescriptor1);
        DataSourceRequest request = new DataSourceRequest();
        request.setSort(sort);
        request.setFilter(filterDescriptor);
        request.setTake(1);
        DataSourceResult result = dictionaryService.getDictionaryValueList(request);
        if (result.getTotal() > 0) {
            return (DictionaryValue) result.getData().get(0);
        } else {
            return null;
        }
    }

    @Override
    public DictionaryValue getParentDictionaryValue(String siteCode) {
        Map<String, String> filter = new HashMap<>();
        filter.put("site_code", siteCode);
        List<DictionaryValue> list = dictionaryService.findDictionaryValues(filter);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public DictionarySyncResponse execute() {
        dictionarySync.setState(dictionaryService.findDictionaryValueById(State.IN_PROCESS));
        RestTemplate restTemplate = new RestTemplate();
        MappingJacksonHttpMessageConverter messageConverter = new MappingJacksonHttpMessageConverter();
        messageConverter.setSupportedMediaTypes(Collections.singletonList(new MediaType("application", "json")));
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
        messageConverters.add(messageConverter);
        restTemplate.setMessageConverters(messageConverters);
        DictionaryValue dv = getLastDictionaryValue(dictionarySync.getDictionary());
        String dvSiteCode = "null";
        if (dv != null) {
            dvSiteCode = dv.getSiteCode();
        }
        DictionarySyncResponse response = restTemplate.getForObject(String.format(URL_TEMPLATE, dvSiteCode, dictionarySync.getSiteDictId()), DictionarySyncResponse.class);
        return response;
    }

    @Override
    public List<DictionaryValue> parseList(DictionarySyncResponse response) {
        List<DictionaryValue> list = new ArrayList<>();
        if (response.success) {
            for (DictionaryValueResponse valueResponse : response.data) {
                Map<String, DictionaryValueLocale> localeMap = new HashMap<>();
                localeMap.put("ru", new DictionaryValueLocale(valueResponse.getDict_name(), ""));
                DictionaryValue dictionaryValue = new DictionaryValue();
                dictionaryValue.setCode(Translit.toTranslit(valueResponse.getDict_name()).toUpperCase());
                dictionaryValue.setLocales(localeMap);
                dictionaryValue.setSiteCode(valueResponse.getDict_id());
                dictionaryValue.setDictionary(dictionarySync.getDictionary());
                if (!valueResponse.getParent_id().equals("")) {
                    dictionaryValue.setParentDictionaryValue(getParentDictionaryValue(valueResponse.getParent_id()));
                }
                list.add(dictionaryValue);
            }
            dictionarySync.setCountAdd(list.size());
        }
        return list;
    }
}
