package com.tvshowtracker.unmarshaller;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.tvshowtracker.model.TvShow;

import java.util.ArrayList;
import java.util.List;

public class TvShowUnmarshaller implements EntityUnmarshaller<TvShow> {
    private JsonParser jsonParser = new JsonParser();

    @Override
    public TvShow unmarshallEntity(String responseEntry) {
        JsonObject jsonObject = jsonParser.parse(responseEntry).getAsJsonObject();
        TvShow.Builder tvShowBuilder = new TvShow.Builder();
        return tvShowBuilder.setId(jsonObject.get("id").getAsLong())
                            .setName(jsonObject.get("name").getAsString())
                            .setDescription(jsonObject.get("description").getAsString())
                            .setAirsDay(jsonObject.get("airsDayOfWeek").getAsString())
                            .setTime(jsonObject.get("airsTime").getAsString())
                            .setNetwork(jsonObject.get("network").getAsString())
                            .setPosterUrl(jsonObject.get("posterUrl").getAsString())
                            .setSeasonCount(jsonObject.get("seasonCount").getAsInt())
                            .setStatus(jsonObject.get("status").getAsString())
                            .setSubscribed(jsonObject.get("subscribed").getAsBoolean())
                            .build();
    }

    @Override
    public List<TvShow> unmarshall(String response) {
        JsonArray jsonArray = jsonParser.parse(response).getAsJsonArray();
        List<TvShow> tvShows = new ArrayList<>();
        for (JsonElement jsonElement : jsonArray) {
            String responseEntry = jsonElement.getAsJsonObject().toString();
            TvShow tvShow = unmarshallEntity(responseEntry);
            tvShows.add(tvShow);
        }
        return tvShows;
    }
}
