package com.ippt.parser;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ippt.api.entity.TvDbEpisode;
import com.ippt.api.entity.TvDbSeries;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class TvDbParser {
    public List<Integer> parseSeriesId(String json) {
        List<Integer> ids = new ArrayList<>();
        JsonArray jsonArray = parseDataArray(json);
        for (JsonElement element : jsonArray) {
            String name = element.getAsJsonObject().get("seriesName").getAsString();
            if (seriesIsForbidden(name))
                continue;
            int id = element.getAsJsonObject().get("id").getAsInt();
            ids.add(id);
        }
        return ids;
    }

    public TvDbSeries parseSeries(String json) {
        return new TvDbSeries(new TvDbSeriesParser(parseDataObject(json)));
    }

    public List<TvDbEpisode> parseEpisodeArray(String json) {
        JsonArray jsonArray = parseDataArray(json);
        List<TvDbEpisode> tvDbEpisodes = new ArrayList<>();
        for (JsonElement je : jsonArray)
            tvDbEpisodes.add(new TvDbEpisode(new TvDbEpisodeParser(je.getAsJsonObject())));
        return tvDbEpisodes;
    }

    public int pasrsePageCount(String json) {
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = jsonParser.parse(json).getAsJsonObject();
        return jsonObject.get("links").getAsJsonObject().get("last").getAsInt();
    }

    public String parseLatestSeriesPoster(String json) {
        JsonArray jsonArray = parseDataArray(json);
        int lastPoster = jsonArray.size() - 1;
        return jsonArray.get(lastPoster).getAsJsonObject().get("fileName").getAsString();
    }

    public int parseSeasonCount(String json) {
        JsonArray seasonNumbers = parseDataObject(json).get("airedSeasons").getAsJsonArray();
        int size = seasonNumbers.size();
        return hasZeroSeason(seasonNumbers)
               ? size - 1
               : size;
    }

    private JsonArray parseDataArray(String json) {
        return new JsonParser().parse(json).getAsJsonObject().getAsJsonArray("data");
    }

    private JsonObject parseDataObject(String json) {
        return new JsonParser().parse(json).getAsJsonObject().getAsJsonObject("data");
    }

    private boolean seriesIsForbidden(String seriesName) {
        return seriesName.contains("** 403: Series Not Permitted **");
    }

    private boolean hasZeroSeason(JsonArray seasonNumbers) {
        for (JsonElement seasonNumber : seasonNumbers) {
            if (seasonNumber.getAsString().equals("0"))
                return true;
        }
        return true;
    }
}
