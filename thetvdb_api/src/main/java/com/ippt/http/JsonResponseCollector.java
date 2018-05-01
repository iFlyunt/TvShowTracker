package com.ippt.http;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import static java.util.stream.Collectors.joining;

class JsonResponseCollector implements FutureCallback<HttpResponse> {
    private List<String>   jsonResponses;
    private CountDownLatch countDownLatch;

    JsonResponseCollector(CountDownLatch countDownLatch) {
        jsonResponses = new ArrayList<>();
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void completed(HttpResponse httpResponse) {
        try {
            HttpEntity httpEntity = httpResponse.getEntity();
            jsonResponses.add(EntityUtils.toString(httpEntity));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        countDownLatch.countDown();
    }

    @Override
    public void failed(Exception e) {
        countDownLatch.countDown();
    }

    @Override
    public void cancelled() {
        countDownLatch.countDown();
    }

    public List<String> collect() {
        return jsonResponses;
    }
}
