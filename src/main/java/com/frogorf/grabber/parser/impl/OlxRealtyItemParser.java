package com.frogorf.grabber.parser.impl;

import com.frogorf.grabber.domain.Task;
import com.frogorf.grabber.helper.*;
import com.frogorf.grabber.parser.ItemParser;
import com.frogorf.grabber.parser.selecter.OlxSelector;
import com.frogorf.realty.domain.Realty;
import com.frogorf.realty.service.RealtyService;
import com.frogorf.realty.service.SellerService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

/**
 * Created by Alex on 20.11.14.
 */
@Service("OlxItemParserService")
@Transactional()
public class OlxRealtyItemParser implements ItemParser {

    private static final Logger logger = LoggerFactory.getLogger(OlxRealtyItemParser.class);

    private Task task;
    private String link;
    private Document realtyDocument;

    @Autowired
    private RealtyService realtyService;
    @Autowired
    private SellerService sellerService;
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
        realty.setTitle(getRealtyTitle());
        realty.setDescription(getRealtyDescription());
        realty.setSiteCode(getRealtySiteCode());
        realty.setSiteUrl(link);
        realty.setDatePublishing(realtyHelper.getParseDatePublishing(getParseDatePublishing()));

        initRealtyHelper();
        if (realty.isNew()) {
            initRealtyImageHelper();
            initRealtyLocationHelper();
            initRealtyOptionHelper();
        }
        initRealtyPriceHelper();
        initRealtySellerHelper();

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
        }

        if (realtyHelper.getParserStatus() == ItemParser.NEW) {
            realty.setDateCreate(new Date());
        } else {
            realty.setDateLastUpdate(new Date());
        }
        saveRealty(realty);
        return realtyHelper.getParserStatus();
    }

    private void checkDuplicate() {
        Map<String, String> params = new HashMap<>();
        params.put(Realty.SITE_URL_PARAM, realty.getSiteUrl());
        params.put(Realty.SITE_CODE_PARAM, realty.getSiteCode());
        realty = realtyHelper.findRealtyOr(params);
    }

    private void initRealtyHelper() {
        try {
            realtyHelper.init(realty);
            realty.setOwnerType(realtyHelper.getParseOwner(getRealtyTitle()));
            realty.setIsCommission(realtyHelper.parseIsNoCommision(realty.getTitle()));
            checkDuplicate();
        } catch (Exception e) {
            logger.error(String.valueOf(e));
        }
    }

    private void initRealtyImageHelper() {
        try {
            realtyImageHelper.init(getRealtyImages(), realty);
            realty.setImages(realtyImageHelper.getImages());
        } catch (Exception e) {
            logger.error(String.valueOf(e));
        }
    }

    private void initRealtyLocationHelper() {
        try {
            realtyLocationHelper.init(getRealtyLocation(), realty);
            realty.setLocation(realtyLocationHelper.getLocation());
        } catch (Exception e) {
            logger.error(String.valueOf(e));
        }
    }

    private void initRealtyOptionHelper() {
        try {
            realtyOptionHelper.init(getRealtyOptions(), realty);
            realty.setRealtyOptionValues(realtyOptionHelper.getResult());
            realtyHelper.setParserStatus(realtyOptionHelper.getParserStatus());
            realty.setTotalSpace(realtyOptionHelper.getRealtyTotalSpace());
            realty.setKitchenSpace(realtyOptionHelper.getRealtyKitchenSpace());
            realty.setLivingSpace(realtyOptionHelper.getRealtyLivingSpace());
            realty.setCountRooms(realtyOptionHelper.getRealtyCountRooms());
            realty.setFloor(realtyOptionHelper.getRealtyFloor());
            realty.setFloors(realtyOptionHelper.getRealtyFloors());
        } catch (Exception e) {
            logger.error(String.valueOf(e));
        }
    }

    private void initRealtyPriceHelper() {
        try {
            realtyPriceHelper.init(getDocumentRealtyPrice(), realty);
            realtyHelper.setParserStatus(realtyPriceHelper.getParserStatus());
            realty.setPrice(realtyPriceHelper.getPrice());
            realty.setCurrency(realtyPriceHelper.getCurrencyDictionaryValue());
            realty.setRealtyHistoryPrices(realtyPriceHelper.getRealtyHistoryPrices());
        } catch (Exception e) {
            logger.error(String.valueOf(e));
        }
    }

    private void initRealtySellerHelper() {
        try {
            realtySellerHelper.init(getRealtySeller(), getRealtySellerPhones(), getRealtySellerSourceLink());
            realty.setSeller(realtySellerHelper.getResult());
        } catch (Exception e) {
            logger.error(String.valueOf(e));
        }
    }

    private Realty saveRealty(Realty realty) {
        sellerService.save(realty.getSeller());
        realtyService.saveRealty(realty);
        realtyService.saveRealtyOptionValueList(realty.getRealtyOptionValues());
        realtyService.saveRealtyImages(realty.getImages());
        realtyService.saveRealtyHistoryPrices(realty.getRealtyHistoryPrices());
        return realty;
    }

    @Override
    public String getParseDatePublishing() {
        Elements e = realtyDocument.select(OlxSelector.DATE_PUBLISHING);
        return (e != null ? e.first().text() : "");
    }

    @Override
    public String getRealtySeller() {
        Elements e = realtyDocument.select(OlxSelector.SELLER);
        return (e != null ? e.first().text() : "");
    }

    @Override
    public String getRealtySellerSourceLink() {
        Elements e = realtyDocument.select(OlxSelector.SELLER_LINK);
        return (e != null ? e.first().attr("abs:href") : "");
    }

    @Override
    public void getDocument(String link) throws IOException {
        realtyDocument = Jsoup.connect(link).get();
    }

    @Override
    public String getRealtyLocation() {
        Elements e = realtyDocument.select(OlxSelector.LOCATION);
        return (e != null ? e.first().text() : "");
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
    public String getRealtySellerPhones() {
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
