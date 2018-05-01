package com.ippt.http;

import com.ippt.common.MainContext;
import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicHeader;

public final class HttpConstants {
    static final URIBuilder URI_ENDPOINT_BUILDER;
    static final Header AUTH_HEADER = new BasicHeader(HttpHeaders.AUTHORIZATION,
                                                             MainContext.TOKEN);

    static final String EMPTY_REQUEST_BODY = "";

    static {
        URI_ENDPOINT_BUILDER = new URIBuilder().setScheme(UriConstants.HTTP_SCHEME)
                                               .setHost(UriConstants.HOST);
    }

    private HttpConstants() { }
}
