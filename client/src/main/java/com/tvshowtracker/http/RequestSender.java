package com.tvshowtracker.http;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;

import static com.tvshowtracker.http.HttpConstants.*;
import static com.tvshowtracker.http.TvTrackerRequest.Builder;
import static com.tvshowtracker.http.TvTrackerRequest.Method;
import static com.tvshowtracker.http.UriConstants.*;

public class RequestSender {
    public static final TvTrackerHttpHandler HTTP_HANDLER = new TvTrackerHttpHandler();
    private static final Builder DEFAULT_POST_REQUEST_BUILDER;
    private static final Builder DEFAULT_REQUEST_BUILDER;
    
    static {
        DEFAULT_POST_REQUEST_BUILDER = new Builder().addHeader(AUTH_HEADER).setMethod(Method.POST)
                                                    .setBody(EMPTY_REQUEST_BODY);
        DEFAULT_REQUEST_BUILDER = new Builder().addHeader(AUTH_HEADER);
    }

    public static class EpisodeEntry {
        public static String fetchAll(long showId) throws URISyntaxException, InterruptedException,
                ExecutionException, IOException {
            String pathBuilder = SHOWS_PATH + "/" + showId + ALL_EPISODES_PATH;
            URI uri = URI_ENDPOINT_BUILDER.setPath(pathBuilder).build();
            final TvTrackerRequest request = DEFAULT_REQUEST_BUILDER.setMethod(Method.GET)
                                                                    .setUri(uri).build();
            return HTTP_HANDLER.doRequest(request);
        }

        public static void markAsWatched(long episodeId)
                throws URISyntaxException, InterruptedException, ExecutionException, IOException {
            URI uri = URI_ENDPOINT_BUILDER.setPath(WATCHED_EPISODES_PATH + episodeId).build();
            final TvTrackerRequest request = DEFAULT_POST_REQUEST_BUILDER.setUri(uri).build();
            HTTP_HANDLER.doPostRequest(request);
        }

        public static String fetchUpcomingEpisodes()
                throws URISyntaxException, InterruptedException, ExecutionException, IOException {
            URI uri = URI_ENDPOINT_BUILDER.setPath(UPCOMING_EPISODES_PATH).build();
            final TvTrackerRequest request = DEFAULT_REQUEST_BUILDER.setMethod(Method.GET)
                                                                    .setUri(uri).build();
            return HTTP_HANDLER.doRequest(request);
        }
    }

    public static class TvShowEntry {
        public static String search(String showName)
                throws URISyntaxException, InterruptedException, ExecutionException, IOException {
            URI uri = URI_ENDPOINT_BUILDER.setPath(SHOW_SEARCH_PATH).setParameter("name", showName)
                                          .build();
            final TvTrackerRequest request = DEFAULT_REQUEST_BUILDER.setMethod(Method.GET)
                                                                    .setUri(uri).build();
            return HTTP_HANDLER.doRequest(request);
        }

        public static String fetchOne(long showId)
                throws URISyntaxException, InterruptedException, ExecutionException, IOException {
            final URI uri = URI_ENDPOINT_BUILDER.setPath(SHOWS_PATH + "/" + showId).build();
            TvTrackerRequest tvShowRequest = DEFAULT_REQUEST_BUILDER.setMethod(Method.GET)
                                                                    .setUri(uri).build();
            return HTTP_HANDLER.doRequest(tvShowRequest);
        }

        public static String fetchAll()
                throws URISyntaxException, InterruptedException, ExecutionException, IOException {
            final URI uri = URI_ENDPOINT_BUILDER.setPath(ALL_SHOWS_PATH).build();
            TvTrackerRequest allShowsRequest = DEFAULT_REQUEST_BUILDER.setUri(uri)
                                                                      .setMethod(Method.GET)
                                                                      .build();
            return HTTP_HANDLER.doRequest(allShowsRequest);
        }

        public static String subscribe(long showId)
                throws URISyntaxException, IOException, InterruptedException, ExecutionException {
            final URI uri = URI_ENDPOINT_BUILDER.setPath(SHOW_SUBSCRIBE_PATH + showId).build();
            TvTrackerRequest subscribeRequest = DEFAULT_POST_REQUEST_BUILDER.setMethod(Method.POST)
                                                                            .setBody(EMPTY_REQUEST_BODY)
                                                                            .setUri(uri).build();
            return HTTP_HANDLER.doPostRequest(subscribeRequest);
        }

        public static String unsubscribe(long showId)
                throws URISyntaxException, InterruptedException, ExecutionException, IOException {
            URI uri = URI_ENDPOINT_BUILDER.setPath(UriConstants.SHOW_UNSUBSCRIBE_PATH + showId)
                                          .build();
            TvTrackerRequest unsubscribeRequest = DEFAULT_REQUEST_BUILDER.setMethod(Method.DELETE)
                                                                         .setUri(uri).build();
            return HTTP_HANDLER.doRequest(unsubscribeRequest);
        }
    }
}
