package com.frogorf.grabber.helper.impl;

import com.frogorf.grabber.helper.RealtyOptionCodeReader;
import com.frogorf.utils.exception.GrabberException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Alex on 28.01.15.
 */
@Service("realtyOptionCodeReaderService")
public class RealtyOptionCodeReaderImpl implements RealtyOptionCodeReader {
    private static final Logger logger = LoggerFactory.getLogger(RealtyOptionCodeReaderImpl.class);
    private Map<String, String[]> codes;

    public Map<String, String[]> getCodes() {
        return codes;
    }

    @Autowired
    public void readFile() {
        try {
            InputStream is = new URL("http://admin.alfabrok.ua/cms/app/includes/data/realtyOptionSelectorCodes.json").openStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            JSONParser jsonParser = new JSONParser();
            JSONArray jsonArray = (JSONArray) jsonParser.parse(readAll(rd));
            if (jsonArray != null) {
                codes = new HashMap<>();
                for (Object o : jsonArray) {
                    JSONObject jsonObject = (JSONObject) o;
                    String jOptionCode = ((String) jsonObject.get("optionCode"));
                    if (jOptionCode != null) {
                        Object[] objects = ((JSONArray) jsonObject.get("codes")).toArray();
                        String[] strings = null;
                        if (objects != null) {
                            strings = Arrays.copyOf(objects, objects.length, String[].class);
                        }
                        codes.put(jOptionCode, strings);
                    }
                }
            }
        } catch (Exception e) {
            String ex = GrabberException.format(e);
            logger.error(ex);
        } finally {
            logger.info("codes created status: {}", codes != null);
        }
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    @Override
    public String[] getItem(String realtyOptionCode) {
        if (codes != null) {
            if (codes.containsKey(realtyOptionCode)) {
                return codes.get(realtyOptionCode);
            }
        }
        return null;
    }

}
