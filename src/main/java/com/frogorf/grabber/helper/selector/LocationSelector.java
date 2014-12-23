package com.frogorf.grabber.helper.selector;

/**
 * Created by Alex on 16.12.14.
 */
public final class LocationSelector {

    public final static int COUNTRY = 1;
    public final static int REGION = 2;
    public final static int DISTRICT = 3;
    public final static int CITY = 4;
    public final static int CITY_AREA = 5;
    public final static int CITY_DISTRICT = 6;
    public final static int STREET = 7;
    public final static int HOUSE_NUMBER = 8;

    public static int COUNTRY_D_ID = 1;
    public static int REGION_D_ID = 2;
    public static int DISTRICT_D_ID = 3;
    public static int CITY_D_ID = 4;
    public static int CITY_AREA_D_ID = 5;
    public static int CITY_DISTRICT_D_ID = 6;
    public static int STREET_D_ID = 7;

    public static void setCOUNTRY_D_ID(int COUNTRY_D_ID) {
        LocationSelector.COUNTRY_D_ID = COUNTRY_D_ID;
    }

    public static void setREGION_D_ID(int REGION_D_ID) {
        LocationSelector.REGION_D_ID = REGION_D_ID;
    }

    public static void setDISTRICT_D_ID(int DISTRICT_D_ID) {
        LocationSelector.DISTRICT_D_ID = DISTRICT_D_ID;
    }

    public static void setCITY_D_ID(int CITY_D_ID) {
        LocationSelector.CITY_D_ID = CITY_D_ID;
    }

    public static void setCITY_AREA_D_ID(int CITY_AREA_D_ID) {
        LocationSelector.CITY_AREA_D_ID = CITY_AREA_D_ID;
    }

    public static void setCITY_DISTRICT_D_ID(int CITY_DISTRICT_D_ID) {
        LocationSelector.CITY_DISTRICT_D_ID = CITY_DISTRICT_D_ID;
    }

    public static void setSTREET_D_ID(int STREET_D_ID) {
        LocationSelector.STREET_D_ID = STREET_D_ID;
    }
}