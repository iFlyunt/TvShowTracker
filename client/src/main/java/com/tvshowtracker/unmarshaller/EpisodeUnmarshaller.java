package com.tvshowtracker.unmarshaller;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.tvshowtracker.model.Episode;

import java.util.ArrayList;
import java.util.List;

public class EpisodeUnmarshaller implements EntityUnmarshaller<Episode> {
    private JsonParser jsonParser = new JsonParser();

    @Override
    public Episode unmarshallEntity(String responseEntry) {
        Episode.Builder episodeBuilder = new Episode.Builder();
        JsonObject entry = jsonParser.parse(responseEntry).getAsJsonObject();
        return episodeBuilder.setId(entry.get("id").getAsLong())
                             .setName(entry.get("name").getAsString())
                             .setDescription(entry.get("description").getAsString())
                             .setAirDate(entry.get("airDate").getAsString())
                             .setEpisodeNumber(entry.get("episodeNumber").getAsInt())
                             .setSeasonNumber(entry.get("seasonNumber").getAsInt())
                             .setWatched(entry.get("watched").getAsBoolean()).build();
    }

    @Override
    public List<Episode> unmarshall(String response) {
        JsonArray responseEntries = jsonParser.parse(response).getAsJsonArray();
        List<Episode> episodes = new ArrayList<>();
        for (JsonElement entity : responseEntries) {
            String responseEntry = entity.getAsJsonObject().toString();
            Episode episode = unmarshallEntity(responseEntry);
            episodes.add(episode);
        }
        return episodes;
    }
}
