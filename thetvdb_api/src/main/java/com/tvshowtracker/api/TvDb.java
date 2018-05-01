package com.tvshowtracker.api;

import com.tvshowtracker.TvDbException;
import com.tvshowtracker.TvDbUriResourceBuilder;
import com.tvshowtracker.TvDbUriResourceConstants;
import com.tvshowtracker.api.entity.TvDbEpisode;
import com.tvshowtracker.api.entity.TvDbSeries;
import com.tvshowtracker.http.TvDbHttpRequest;
import com.tvshowtracker.http.TvDbHttpRequestImpl;
import com.tvshowtracker.parser.TvDbParser;
import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;

import java.net.URI;
import java.util.*;

public class TvDb {
    private final Header          AUTH_HEADER;
    private final Header          LANGUAGE_HEADER;
    private final TvDbHttpRequest tvDbHttpRequest;

    private TvDbParser tvDbParser;

    public TvDb(String token) {
        tvDbHttpRequest = new TvDbHttpRequestImpl();
        AUTH_HEADER = new BasicHeader(HttpHeaders.AUTHORIZATION, token);
        LANGUAGE_HEADER = new BasicHeader(HttpHeaders.ACCEPT_LANGUAGE, "en");
        this.tvDbParser = new TvDbParser();
    }

    public List<TvDbSeries> searchSeries(final String seriesName) throws TvDbException {
        URI searchSeriesUri = new TvDbUriResourceBuilder(TvDbUriResourceConstants.SEARCH_SERIES)
                .setParam(new BasicNameValuePair("name", seriesName)).build();
        String seriesBasicData = tvDbHttpRequest.get(searchSeriesUri, AUTH_HEADER, LANGUAGE_HEADER);
        List<String> seriesFullData = tvDbHttpRequest.get(createUrisForSeriesSearch(seriesBasicData),
                                                          AUTH_HEADER, LANGUAGE_HEADER);
        return createSeries(seriesFullData);
    }

    public String getLatestSeriesPoster(final int seriesId) throws TvDbException {
        TvDbUriResourceBuilder tvDbUriResourceBuilder = new TvDbUriResourceBuilder(
                TvDbUriResourceConstants.SERIES + seriesId + TvDbUriResourceConstants.IMAGES_QUERY);
        URI uri = tvDbUriResourceBuilder.setParam(new BasicNameValuePair("keyType", "poster"))
                                        .build();
        String json;
        try {
            json = tvDbHttpRequest.get(uri, AUTH_HEADER, LANGUAGE_HEADER);
        } catch (TvDbException e) {
            e.printStackTrace();
            return "";
        }
        String posterName = tvDbParser.parseLatestSeriesPoster(json);
        return "https://www.thetvdb.com/banners/" + posterName;
    }

    public int getSeasonCount(int seriesId) throws TvDbException {
        URI episodesSummaryUri = new TvDbUriResourceBuilder(
                TvDbUriResourceConstants.SERIES + seriesId
                + TvDbUriResourceConstants.EPISODES_SUMMARY).build();
        String json = tvDbHttpRequest.get(episodesSummaryUri, AUTH_HEADER, LANGUAGE_HEADER);
        return tvDbParser.parseSeasonCount(json);
    }

    public List<TvDbEpisode> getSeriesSeason(int seriesId, String airedSeason) throws TvDbException {
        URI episodesUri = new TvDbUriResourceBuilder(TvDbUriResourceConstants.SERIES + seriesId
                                                     + TvDbUriResourceConstants.EPISODES_QUERY)
                .setParam(new BasicNameValuePair("airedSeason", airedSeason)).build();
        String json = tvDbHttpRequest.get(episodesUri, AUTH_HEADER, LANGUAGE_HEADER);
        return tvDbParser.parseEpisodeArray(json);
    }

    public Map<Integer, List<TvDbEpisode>> getAllSeasons(int seriesId) throws TvDbException {
        TvDbUriResourceBuilder builder;
        builder = new TvDbUriResourceBuilder(TvDbUriResourceConstants.SERIES + seriesId
                                           + TvDbUriResourceConstants.EPISODES);
        URI uri = builder.build();
        String json = tvDbHttpRequest.get(uri, AUTH_HEADER, LANGUAGE_HEADER);
        PageTurner pageTurner = new PageTurner(tvDbParser.pasrsePageCount(json));
        Map<Integer, List<TvDbEpisode>> map = new TreeMap<>();
        while (pageTurner.hasNext()) {
            String page = pageTurner.next(builder);
            List<TvDbEpisode> episodes = tvDbParser.parseEpisodeArray(page);
            for (TvDbEpisode episode : episodes) {
                int seasonNumber = episode.getSeasonNumber();
                map.putIfAbsent(seasonNumber, new ArrayList<>());
                map.get(seasonNumber).add(episode);
            }
        }
        map.remove(0); // remove zero season
        return map;
    }

    private List<URI> createUrisForSeriesSearch(String json) throws TvDbException {
        List<Integer> ids = tvDbParser.parseSeriesId(json);
        List<URI> seriesUris = new ArrayList<>();
        for (Integer id : ids) {
            URI uri = new TvDbUriResourceBuilder(TvDbUriResourceConstants.SERIES + id).build();
            seriesUris.add(uri);
        }
        return seriesUris;
    }

    private List<TvDbSeries> createSeries(List<String> tvShowsJson) {
        List<TvDbSeries> tvShows = new ArrayList<>();
        for (String s : tvShowsJson)
            tvShows.add(tvDbParser.parseSeries(s));
        return tvShows;
    }

    private class PageTurner {
        private final int LAST_PAGE;

        private int currentPage;

        public PageTurner(int lastPage) {
            this.LAST_PAGE = lastPage;
            this.currentPage = 1;
        }

        public boolean hasNext() {
            return currentPage <= LAST_PAGE;
        }

        public String next(TvDbUriResourceBuilder builder) throws TvDbException {
            URI uri = builder.setParam(new BasicNameValuePair("page",
                                                              String.valueOf(currentPage))).build();
            String pageData = tvDbHttpRequest.get(uri, AUTH_HEADER, LANGUAGE_HEADER);
            currentPage++;
            return pageData;
        }
    }
}
