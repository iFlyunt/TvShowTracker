package com.ippt.parser;

import com.google.gson.JsonObject;

import java.time.LocalDateTime;
import java.util.Optional;

public class TvDbSeriesParser extends AbstractTvDbEntityParser {

    public TvDbSeriesParser(JsonObject jsonObject) {
        super(jsonObject);
    }

    public String getAirsDayOfWeek() {
        return getJsonObject().get("airsDayOfWeek").getAsString();
    }

    public String getAirsTime() {
        return getJsonObject().get("airsTime").getAsString();
    }

    public String getNetwork() {
        return getJsonObject().get("network").getAsString();
    }

    public String getStatus() {
        return getJsonObject().get("status").getAsString();
    }

    public String getName() {
        return getJsonObject().get("seriesName").getAsString();
    }
}
