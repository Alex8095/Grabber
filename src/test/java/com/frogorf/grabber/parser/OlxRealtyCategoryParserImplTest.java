package com.frogorf.grabber.parser;

import com.frogorf.grabber.domain.Task;
import com.frogorf.grabber.parser.CategoryParser;
import com.frogorf.grabber.parser.impl.OlxRealtyCategoryParserImpl;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.*;

/**
 * Created by alex on 20.11.14.
 */
public class OlxRealtyCategoryParserImplTest {

    private CategoryParser olxCategoryParser;
    private Task task;
    private static String START_PAGE_URL = "http://kiev.ko.olx.ua/nedvizhimost/prodazha-kvartir/";
    private static String NEXT_PAGE_URL = "http://kiev.ko.olx.ua/nedvizhimost/prodazha-kvartir/?page=2";

    @Before
    public void setUp() throws Exception {
        task = new Task();
        task.setUrl(START_PAGE_URL);

        olxCategoryParser = new OlxRealtyCategoryParserImpl();
    }

    @Test
    public void testSetTask() throws Exception {
        assertEquals(START_PAGE_URL, olxCategoryParser.getTask().getUrl());
    }

    @Test
    public void testGetCurrentDocument() throws Exception {
        olxCategoryParser.getDocument(START_PAGE_URL);
        assertNotNull(olxCategoryParser.getCurrentDocument());
    }

    @Test
    public void testGetRealtyLinks() throws Exception {
        olxCategoryParser.getDocument(START_PAGE_URL);
        assertEquals(olxCategoryParser.getRealtyLinks().size(), 39);
    }

    @Test
    public void testParseRealty() throws Exception {
    }

    @Test
    public void testGetNextCategoryPageLink() throws Exception {
        olxCategoryParser.getDocument(START_PAGE_URL);
        assertEquals(olxCategoryParser.getNextCategoryPageLink(), NEXT_PAGE_URL);
    }

    @Test
    public void testIsIssetNextCategotyPage() throws Exception {
        olxCategoryParser.getDocument(START_PAGE_URL);
        assertTrue(olxCategoryParser.isIssetNextCategotyPage(olxCategoryParser.getNextCategoryPageLink()));
    }

    @Test
    public void testGetCategoryPagingLinks() throws Exception {
        olxCategoryParser.getDocument(START_PAGE_URL);
        assertEquals(olxCategoryParser.getCategoryPagingLinks().size(), 13);
        //System.out.println(olxCategoryParser.getCategoryPagingLinks().size());
    }

    @Test
    public void testParseCategoryPage() throws Exception {
        olxCategoryParser.getDocument(START_PAGE_URL);
        olxCategoryParser.parseCategoryPage(START_PAGE_URL);
//        System.out.println(olxCategoryParser.getCurrentPageNumber());
        assertEquals(olxCategoryParser.getCurrentPageNumber(), 3);
    }
}
