package com.ippt.http;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.nio.client.HttpAsyncClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public final class TvTrackerHttpHandler {
    private static final CloseableHttpAsyncClient HTTP_ASYNC_CLIENT;

    static {
        HTTP_ASYNC_CLIENT = HttpAsyncClients.createDefault();
        HTTP_ASYNC_CLIENT.start();
    }

    private int statusCode = -1;

    public String doRequest(TvTrackerRequest request)
            throws ExecutionException, InterruptedException, IOException {
        RequestBuilder builder = RequestBuilder.create(request.getMethod())
                                                      .setUri(request.getUri());
       request.getHeaders().forEach(builder::addHeader);
       HttpUriRequest httpUriRequest = builder.build();
       return sendRequest(httpUriRequest);
    }

    public String doPostRequest(TvTrackerRequest tvTrackerRequest)
            throws IOException, ExecutionException, InterruptedException {
        StringEntity body = new StringEntity(tvTrackerRequest.getBody());
        RequestBuilder builder = RequestBuilder.post().setUri(tvTrackerRequest.getUri())
                                                      .setEntity(body);
        tvTrackerRequest.getHeaders().forEach(builder::addHeader);
        HttpUriRequest httpUriRequest = builder.build();
        return sendRequest(httpUriRequest);
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void closeConnection() throws IOException {
        HTTP_ASYNC_CLIENT.close();
    }

    private String sendRequest(HttpUriRequest httpUriRequest)
            throws IOException, ExecutionException, InterruptedException {
        HttpResponse response = HTTP_ASYNC_CLIENT.execute(httpUriRequest, null).get();
        this.statusCode = response.getStatusLine().getStatusCode();
        return EntityUtils.toString(response.getEntity());
    }
}
