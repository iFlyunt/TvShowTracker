package com.ippt.api.auth;

import com.google.gson.JsonObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static java.util.stream.Collectors.joining;

public final class TvDbCredentials {
    private String credentials;

    public TvDbCredentials(String apiKey, String userKey, String userName) {
        this.credentials = convertToJson(apiKey, userKey, userName);
    }

    public String getCredentials() {
        return this.credentials;
    }

    public void storeToFile(String filePath) throws IOException {
        Files.write(Paths.get(filePath), credentials.getBytes());
    }

    public static String loadFromFile(String filePath) throws IOException {
        return Files.lines(Paths.get(filePath))
                    .collect(joining());
    }

    private String convertToJson(String apiKey, String userKey, String userName) {
        JsonObject jo = new JsonObject();
        jo.addProperty("apiKey", apiKey);
        jo.addProperty("userKey", userKey);
        jo.addProperty("userName", userName);
        return jo.toString();
    }

}
