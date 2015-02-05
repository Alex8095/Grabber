package com.frogorf.grabber.parser;

import com.frogorf.grabber.domain.Task;
import com.frogorf.realty.service.RealtyService;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * Created by alex on 19.11.14.
 */
public interface ItemParser {

    static final int DUPLICATE = 1;
    static final int NEW = 2;
    static final int UPDATE = 3;

    void setTask(Task task);

    Task getTask();

    Document getRealtyDocument();

    Integer parse(String link) throws IOException;

    String getParseDatePublishing();

    String getRealtySeller();

    String getRealtySellerPhones();

    String getRealtySellerSourceLink();

    void getDocument(String link) throws IOException;

    String getRealtyLocation();

    String getRealtySiteCode();

    List<String> getRealtyImages();

    String getRealtyTitle();

    String getRealtyDescription();

    String getDocumentRealtyPrice();

    Map<String, String> getRealtyOptions();

    URL getPhoneURL();
}
