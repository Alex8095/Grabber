package com.frogorf.grabber.parser.impl;

import com.frogorf.grabber.domain.Task;
import com.frogorf.grabber.domain.TaskHistory;
import com.frogorf.grabber.parser.CategoryParser;
import com.frogorf.grabber.parser.ItemParser;
import com.frogorf.grabber.parser.selecter.OlxSelector;
import com.frogorf.grabber.service.GrabberService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;

/**
 * Created by alex on 19.11.14.
 */
@Service("OlxCategoryParserService")
public class OlxRealtyCategoryParserImpl implements CategoryParser {

    private static final Logger logger = LoggerFactory.getLogger(OlxRealtyCategoryParserImpl.class);

    @Autowired
    private ItemParser realtyParser;
    @Autowired
    private GrabberService grabberService;

    private Task task;
    private Document currentDocument;
    private int currentPageNumber = 1;
    public int grabberIteration = 2;
    private TaskHistory taskHistory;

    public OlxRealtyCategoryParserImpl() {
    }

    public void initTask(Integer taskId) {
        task = grabberService.findTaskById(taskId);
        taskHistory = new TaskHistory();
        taskHistory.setTask(task);
        taskHistory.setStartDate(new Date());
    }

    @Override
    public int getCurrentPageNumber() {
        return currentPageNumber;
    }

    @Override
    public TaskHistory getTaskHistory() {
        return taskHistory;
    }

    @Override
    public Task getTask() {
        return task;
    }

    @Override
    public void start() throws IOException {
        parseCategoryPage(task.getUrl());
    }

    @Override
    public void getDocument(String url) throws IOException {
        currentDocument = Jsoup.connect(url).get();
    }

    @Override
    public Document getCurrentDocument() {
        return currentDocument;
    }

    @Override
    public Elements getRealtyLinks() {
        return currentDocument.select(OlxSelector.CATEGORY_ITEM_LINK);
    }

    @Override
    public void parseRealty(String url) throws IOException {
        realtyParser.setTask(task);
        switch (realtyParser.parse(url)) {
            case ItemParser.NEW:
                taskHistory.setCountNew(taskHistory.getCountNew() + 1);
                break;
            case ItemParser.DUPLICATE:
                taskHistory.setCountDuplicated(taskHistory.getCountDuplicated() + 1);
                break;
            case ItemParser.UPDATE:
                taskHistory.setCountUpdate(taskHistory.getCountUpdate() + 1);
                break;
        }
        taskHistory.setCountFound(taskHistory.getCountFound() + 1);
    }

    @Override
    public String getNextCategoryPageLink() {
        return String.format("%s?page=%s", task.getUrl(), currentPageNumber + 1);

    }

    @Override
    public Boolean isIssetNextCategotyPage(String nextCategoryPage) {
        for (Element link : getCategoryPagingLinks()) {
            if (link.attr("abs:href").equals(nextCategoryPage)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Elements getCategoryPagingLinks() {
        return currentDocument.select(OlxSelector.CATEGORY_PAGER_LINK);
    }

    @Override
    public void parseCategoryPage(String url) throws IOException {
        if (currentPageNumber <= grabberIteration) {
            getDocument(url);
            Elements realtyLinks = getRealtyLinks();
            if (realtyLinks.size() > 0) {
                for (Element realtyLink : realtyLinks) {
                    parseRealty(realtyLink.attr("abs:href"));
                }
            }
            String nextCategoryPageLink = getNextCategoryPageLink();
            if (isIssetNextCategotyPage(nextCategoryPageLink)) {
                currentPageNumber++;
                parseCategoryPage(nextCategoryPageLink);
            }
        } else {
            return;
        }
    }
}
