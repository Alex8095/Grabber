package com.frogorf.grabber.parser.selecter;

/**
 * Created by Alex on 21.11.14.
 */
public final class OlxSelector {

    public final static String CATEGORY_ITEM_LINK = "#offers_table .breakword .large a[href]";
    public final static String CATEGORY_PAGER_LINK = ".pager .item a[href]";

    public final static String ADDRESS = ".address .block";
    public final static String TITLE = ".offerheadinner h1.brkword";
    public final static String DESCRIPTION = "#textContent p.large";
    public final static String PRICE = ".pricelabel .xxxx-large";
    public final static String REALTY_OPTION = ".details .pding5_10";
    public final static String REALTY_OPTION_VALUE = ".block";

    public final static String PHONE_URI = "http://kiev.ko.olx.ua/ajax/misc/contact/phone/%s/";
    public final static String IMAGE = "#offerdescription img";
    public final static String IMAGE_REMOVE = "#offerbottombar";


}
