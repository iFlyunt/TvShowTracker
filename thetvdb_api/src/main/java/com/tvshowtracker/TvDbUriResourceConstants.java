package com.tvshowtracker;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public final class TvDbUriResourceConstants {
    public static final String REFRESH_TOKEN    = "/refresh_token";
    public static final String LOGIN            = "/login";
    public static final String SEARCH_SERIES    = "/search/series";
    public static final String SERIES           = "series/";
    public static final String IMAGES_QUERY     = "/images/query";
    public static final String EPISODES         = "/episodes";
    public static final String EPISODES_SUMMARY = EPISODES + "/summary";
    public static final String EPISODES_QUERY   = EPISODES + "/query";

    private TvDbUriResourceConstants() {throw new NotImplementedException();}
}
