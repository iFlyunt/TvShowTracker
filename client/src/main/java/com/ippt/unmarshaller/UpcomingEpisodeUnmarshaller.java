package com.ippt.unmarshaller;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ippt.model.UpcomingEpisode;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.List;

public class UpcomingEpisodeUnmarshaller implements EntityUnmarshaller<UpcomingEpisode> {
    private JsonParser jsonParser = new JsonParser();
    @Override
    public UpcomingEpisode unmarshallEntity(String responseEntry) {
        JsonObject entry = jsonParser.parse(responseEntry).getAsJsonObject();
        UpcomingEpisode.Builder builder = new UpcomingEpisode.Builder();
        return builder.setId(entry.get("episodeId").getAsLong())
                      .setShowId(entry.get("showId").getAsLong())
                      .setName(entry.get("episodeName").getAsString())
                      .setShowName(entry.get("showName").getAsString())
                      .setEpisodeNumber(entry.get("episodeNumber").getAsInt())
                      .setSeasonNumber(entry.get("seasonNumber").getAsInt())
                      .setShowPosterUrl(entry.get("showPoster").getAsString())
                      .setAirDate(entry.get("airDate").getAsString()).build();
    }

    @Override
    public List<UpcomingEpisode> unmarshall(String response) {
        final JsonArray entries = jsonParser.parse(response).getAsJsonArray();
        List<UpcomingEpisode> upcomingEpisodes = new ArrayList<>();
        for (JsonElement entry : entries) {
            final String s = entry.getAsJsonObject().toString();
            UpcomingEpisode upcomingEpisode = unmarshallEntity(s);
            upcomingEpisodes.add(upcomingEpisode);
        }
        return upcomingEpisodes;
    }
}
