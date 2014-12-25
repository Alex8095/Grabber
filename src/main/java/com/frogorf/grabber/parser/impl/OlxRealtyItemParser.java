package com.frogorf.grabber.parser.impl;

import com.frogorf.grabber.domain.Task;
import com.frogorf.grabber.helper.*;
import com.frogorf.grabber.parser.ItemParser;
import com.frogorf.grabber.parser.selecter.OlxSelector;
import com.frogorf.realty.domain.Realty;
import com.frogorf.realty.service.RealtyService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Alex on 20.11.14.
 */
@Service("OlxItemParserService")
public class OlxRealtyItemParser implements ItemParser {

    private Task task;
    private String link;
    private Document realtyDocument;

    @Autowired
    private RealtyService realtyService;
    @Autowired
    private RealtyHelper realtyHelper;
    @Autowired
    private RealtyImageHelper realtyImageHelper;
    @Autowired
    private RealtyLocationHelper realtyLocationHelper;
    @Autowired
    private RealtyOptionHelper realtyOptionHelper;
    @Autowired
    private RealtyPriceHelper realtyPriceHelper;
    @Autowired
    private RealtySellerHelper realtySellerHelper;

    private Realty realty;

    @Override
    public Document getRealtyDocument() {
        return realtyDocument;
    }

    @Override
    public Integer parse(String link) throws IOException {
        this.link = link;
        getDocument(link);
        realty = new Realty();
//        realtyHelper.init(realty);
        realty.setPrice(getRealtyPrice());
        realty.setTitle(getRealtyTitle());
        realty.setDescription(getRealtyDescription());
        realty.setSiteCode(getRealtySiteCode());
        realty.setSiteUrl(link);
        if (task != null) {
            if (task.getLocation() != null) {
                realty.setLocation(task.getLocation());
            }
            realty.setIsRent(task.getIsRent());
            realty.setIsSale(task.getIsSale());
            realty.setIsChange(task.getIsChange());
            if (task.getOwnerType() != null) {
                realty.setOwnerType(task.getOwnerType());
            }
            if (task.getImplementationType() != null) {
                realty.setImplementationType(task.getImplementationType());
            }
            List<String> images = getRealtyImages();
            if (images != null) {
//                realty.setImages(new ArrayList<RealtyImage>());
//                for (String imageLink : images) {
//                    realty.getImages().add(new RealtyImage(imageLink));
//                }
            }
            realtyService.saveRealty(realty);
        }
        return ItemParser.NEW;
    }

    @Override
    public void getDocument(String link) throws IOException {
        realtyDocument = Jsoup.connect(link).get();
    }

    @Override
    public String getRealtyAddress() {
        Elements adresses = realtyDocument.select(OlxSelector.ADDRESS);
        return (adresses != null ? adresses.first().text().replace(" ,", ",") : "");
    }

    @Override
    public String getRealtySiteCode() {
        return link.substring(link.lastIndexOf("-") + 1, link.indexOf(".html")).replace("ID", "");
    }

    @Override
    public List<String> getRealtyImages() {
        Elements elements = realtyDocument.select(OlxSelector.IMAGE);
        List<String> images = new ArrayList<>();
        for (Element image : elements) {
            images.add(image.attr("abs:src"));
        }
        return images;
    }

    @Override
    public void setRealtyService(RealtyService realtyService) {
        this.realtyService = realtyService;
    }

    @Override
    public void setTask(Task task) {
        this.task = task;
    }

    @Override
    public Task getTask() {
        return task;
    }

    @Override
    public String getRealtyTitle() {
        Elements titles = realtyDocument.select(OlxSelector.TITLE);
        return (titles != null ? titles.first().text() : "");
    }

    @Override
    public String getRealtyDescription() {
        Elements description = realtyDocument.select(OlxSelector.DESCRIPTION);
        return (description != null ? description.text() : "");
    }

    @Override
    public String getDocumentRealtyPrice() {
        Elements prices = realtyDocument.select(OlxSelector.PRICE);
        return (prices != null ? prices.first().text() : "");
    }

    @Override
    public String getRealtyCurrency() {
        String price = getDocumentRealtyPrice();
        return (!price.equals("") ? price.replaceAll("\\d+.*", "") : "");
    }

    @Override
    public Long getRealtyPrice() {
        String price = getDocumentRealtyPrice();
        return (!price.equals("") && price != null ? Long.valueOf(price.replaceAll("\\D+", "")) : null);
    }

    @Override
    public Map<String, String> getRealtyOptions() {
        Elements elements = realtyDocument.select(OlxSelector.REALTY_OPTION);
        Map<String, String> options = new HashMap<>();
        for (Element element : elements) {
            String value = element.select(OlxSelector.REALTY_OPTION_VALUE).first().text();
            element.select(OlxSelector.REALTY_OPTION_VALUE).first().remove();
            String key = element.text();
            options.put(key, value);
        }
        return options;
    }

    @Override
    public URL getPhoneURL() {
        URL uri = null;
        try {
            uri = new URL(String.format(OlxSelector.PHONE_URI, getRealtySiteCode()));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return uri;
    }

    @Override
    public String getRealtyContactPhone() {
        URL url = getPhoneURL();
        if (url != null) {
            BufferedReader in = null;
            try {
                in = new BufferedReader(new InputStreamReader(url.openStream()));
                String inputLine;
                StringBuffer sb = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    sb.append(inputLine);
                }
                return sb.toString().replace("{\"value\":\"", "").replace("\"}", "");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
