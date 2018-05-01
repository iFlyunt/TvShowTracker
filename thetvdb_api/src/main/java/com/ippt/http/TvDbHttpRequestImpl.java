package com.ippt.http;

import com.ippt.TvDbException;
import org.apache.http.*;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
// TODO Reimplement this horrible thing
public final class TvDbHttpRequestImpl implements TvDbHttpRequest {
    private static final List<Header>             DEFAULT_HEADER;
    private static final CloseableHttpAsyncClient HTTP_ASYNC_CLIENT;

    private static final String CONNECTION_FAILURE_MESSAGE = "Connection failed";

    static {
        DEFAULT_HEADER = Collections.singletonList(
                new BasicHeader(HttpHeaders.ACCEPT, "application/json"));
        HTTP_ASYNC_CLIENT = HttpAsyncClients.custom()
                                            .setDefaultHeaders(DEFAULT_HEADER)
                                            .disableCookieManagement()
                                            .build();
    }

    public String get(final URI uri, final Header... headers) throws TvDbException {
        HttpUriRequest getRequest = RequestBuilder.get()
                                                  .setUri(uri)
                                                  .build();
        getRequest.setHeaders(headers);
        String jsonResponse;
        try {
            HTTP_ASYNC_CLIENT.start();
            Future<HttpResponse> future = HTTP_ASYNC_CLIENT.execute(getRequest, null);
            HttpResponse httpResponse = future.get();
            checkHttpResponse(httpResponse);
            jsonResponse = readResponse(httpResponse);
        } catch (IOException | InterruptedException | ExecutionException e) {
            throw new TvDbException(CONNECTION_FAILURE_MESSAGE, e);
        }
        return jsonResponse;
    }

    @Override
    public List<String> get(final List<URI> uris, final Header... headers) throws TvDbException {
        try {
            HTTP_ASYNC_CLIENT.start();
            List<HttpUriRequest> httpUriRequests = new ArrayList<>();
            for (URI uri : uris) {
                HttpUriRequest request = RequestBuilder.get()
                                                       .setUri(uri)
                                                       .build();
                request.setHeaders(headers);
                httpUriRequests.add(request);
            }
            final CountDownLatch countDownLatch = new CountDownLatch(httpUriRequests.size());
            final JsonResponseCollector jsonResponseCollector = new JsonResponseCollector(
                    countDownLatch);
            for (HttpUriRequest httpUriRequest : httpUriRequests)
                HTTP_ASYNC_CLIENT.execute(httpUriRequest, jsonResponseCollector);
            countDownLatch.await();
            return jsonResponseCollector.collect();
        } catch (InterruptedException e) {
            throw new TvDbException(CONNECTION_FAILURE_MESSAGE, e);
        }
    }

    public String post(final URI uri, final String json, final Header... headers)
            throws TvDbException {
        StringEntity entity;
        try {
            entity = new StringEntity(json);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        HttpUriRequest postRequest = RequestBuilder.post()
                                                   .setUri(uri)
                                                   .setEntity(entity)
                                                   .build();
        postRequest.setHeaders(headers);
        String jsonResponse;
        try {
            HTTP_ASYNC_CLIENT.start();
            HttpResponse httpResponse = HTTP_ASYNC_CLIENT.execute(postRequest, null)
                                                         .get();
            checkHttpResponse(httpResponse);
            jsonResponse = readResponse(httpResponse);
        } catch (IOException | InterruptedException | ExecutionException e) {
            throw new TvDbException(CONNECTION_FAILURE_MESSAGE, e);
        }
        return jsonResponse;
    }

    private static void checkHttpResponse(final HttpResponse httpResponse) throws TvDbException {
        StatusLine statusLine = httpResponse.getStatusLine();
        int statusCode = statusLine.getStatusCode();
        if (statusCode == HttpStatus.SC_NOT_FOUND)
            throw new TvDbException("Resource not found");
        if (statusCode == HttpStatus.SC_SERVICE_UNAVAILABLE
                || statusCode == HttpStatus.SC_INTERNAL_SERVER_ERROR)
            throw new TvDbException("Service unavailable " + statusLine.getReasonPhrase());
    }

    private static String readResponse(final HttpResponse httpResponse) throws IOException {
        return EntityUtils.toString(httpResponse.getEntity());
    }
}
