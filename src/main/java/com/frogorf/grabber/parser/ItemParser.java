package com.frogorf.grabber.parser;

import com.frogorf.grabber.domain.Task;
import com.frogorf.realty.service.RealtyService;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Map;

/**
 * Created by alex on 19.11.14.
 */
public interface ItemParser {

    static final int DUPLICATE = 1;
    static final int NEW = 2;
    static final int UPDATE = 3;

    void setRealtyService(RealtyService realtyService);

    void setTask(Task task);

    Task getTask();

    Document getRealtyDocument();

    Integer parse(String link) throws IOException;

    void getDocument(String link) throws IOException;

    String getRealtyAddress();

    String getRealtySiteCode();

    List<String> getRealtyImages();

    String getRealtyTitle();

    String getRealtyDescription();

    String getDocumentRealtyPrice();

    String getRealtyCurrency();

    Long getRealtyPrice();

    Map<String, String> getRealtyOptions();

    URI getPhoneURI();

    String getRealtyContactPhone();
}
