package com.frogorf.grabber.parser;

import com.frogorf.grabber.domain.Task;
import com.frogorf.grabber.domain.TaskHistory;
import com.frogorf.realty.service.RealtyService;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by alex on 19.11.14.
 */
public interface CategoryParser {

    void initTask(Integer taskId, Task task);

    int getCurrentPageNumber();

    TaskHistory getTaskHistory();

    Task getTask();

    void start() throws IOException;

    void getDocument(String url) throws IOException;

    Document getCurrentDocument();

    Boolean isIssetNextCategotyPage(String nextCategoryPage);

    Elements getRealtyLinks();

    void parseRealty(String url) throws IOException;

    public String getNextCategoryPageLink();

    Elements getCategoryPagingLinks();

    void parseCategoryPage(String url) throws IOException;

    void finish();
}
