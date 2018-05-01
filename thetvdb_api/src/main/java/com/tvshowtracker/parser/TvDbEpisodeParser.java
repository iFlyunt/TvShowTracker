package com.tvshowtracker.parser;

import com.google.gson.JsonObject;

public class TvDbEpisodeParser extends AbstractTvDbEntityParser {

    TvDbEpisodeParser(JsonObject jsonObject) {
        super(jsonObject);
    }

    public String getName() {
        return toEmptyStringIfJsonNull(getJsonObject().get("episodeName"));
    }

    public String getAirDate() {
        return getJsonObject().get("firstAired").getAsString();
    }

    public int getEpisodeNumber() {
        return getJsonObject().get("airedEpisodeNumber").getAsInt();
    }

    public int getSeasonNumber() {
        return getJsonObject().get("airedSeason").getAsInt();
    }
}
