package com.tvshowtracker;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public final class TvDbUriResourceBuilder {
    private static final String URI_SCHEME = "https";
    private static final String URI_HOST   = "api.thetvdb.com";

    private String              resource;
    private List<NameValuePair> params = null;

    public TvDbUriResourceBuilder(String resource) {
        this.resource = resource;
    }

    public TvDbUriResourceBuilder setParam(NameValuePair param) {
        params = getOrCreateParamList();
        params.add(param);
        return this;
    }

    public URI build() throws TvDbException {
        URI uri;
        try {
            uri = new URIBuilder().setScheme(URI_SCHEME)
                                  .setHost(URI_HOST)
                                  .setPath(resource)
                                  .setParameters(getOrCreateParamList())
                                  .build();
            clearParamsIfNotNull();
        } catch (URISyntaxException e) {
            throw new TvDbException("Url syntax is wrong: " + e.getInput(), e);
        }
        return uri;
    }

    private List<NameValuePair> getOrCreateParamList() {
        return params == null
               ? new ArrayList<>()
               : params;
    }

    private void clearParamsIfNotNull() {
        if (params != null)
            params.clear();
    }
}
