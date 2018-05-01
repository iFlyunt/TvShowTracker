package com.tvshowtracker.http;

public class UriConstants {
    public static final String HTTP_SCHEME            = "http";
    public static final String HOST                   = "localhost:8080";
    public static final String SHOWS_PATH             = "/shows";
    public static final String SHOW_SEARCH_PATH       = SHOWS_PATH + "/search";
    public static final String SHOW_SUBSCRIBE_PATH    = SHOWS_PATH + "/subscribe/";
    public static final String SHOW_UNSUBSCRIBE_PATH  = SHOWS_PATH + "/unsubscribe/";
    public static final String ALL_SHOWS_PATH         = SHOWS_PATH + "/all";
    public static final String ALL_EPISODES_PATH      = "/episodes";
    public static final String WATCHED_EPISODES_PATH  = SHOWS_PATH + "/episodes/watched/";
    public static final String UPCOMING_EPISODES_PATH = SHOWS_PATH + "/episodes/upcoming";
}
