package com.ippt;

import com.ippt.api.TvDb;
import com.ippt.api.auth.TvDbAuthenticationException;
import com.ippt.api.auth.TvDbAuthenticationImpl;
import com.ippt.api.auth.TvDbCredentials;
import com.ippt.api.entity.TvDbEpisode;
import com.ippt.api.entity.TvDbSeries;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Main {
    static {
        System.setProperty("org.apache.commons.logging.Log",
                           "org.apache.commons.logging.impl.SimpleLog");
        System.setProperty("org.apache.commons.logging.simplelog.showdatetime", "true");
        System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.http", "DEBUG");
        System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.http.wire",
                           "ERROR");
    }

    public static void main(String[] args)
            throws IOException, TvDbAuthenticationException, TvDbException, InterruptedException {
        String credentials = TvDbCredentials.loadFromFile("/home/iflyunt/credentials");
        String token = new TvDbAuthenticationImpl().authenticate(credentials);
        TvDb tvDb = new TvDb(token);
//        List<TvDbEpisode> tvDbEpisodes = tvDb.getSeriesSeason(153021, "1");
//        tvDbEpisodes.forEach(tvDbEpisode -> System.out.println(tvDbEpisode.getId()));
        final Map<Integer, List<TvDbEpisode>> allSeasons = tvDb.getAllSeasons(153021);
        System.out.println(allSeasons.size());
/*
        for (TvDbEpisode tvDbEpisode : tvDbEpisodes) {
            System.out.println(tvDbEpisode.getDescription());
        }
*/
    }
}
