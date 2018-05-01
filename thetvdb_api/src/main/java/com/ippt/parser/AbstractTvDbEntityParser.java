package com.ippt.parser;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

abstract public class AbstractTvDbEntityParser {
    private JsonObject jsonObject;
    AbstractTvDbEntityParser(JsonObject data) {
        this.jsonObject = data;
    }

    public String getDescription() {
        return toEmptyStringIfJsonNull(jsonObject.get("overview"));
    }

    public int getId() {
        return jsonObject.get("id").getAsInt();
    }

    protected JsonObject getJsonObject() {
        return jsonObject;
    }

    protected String toEmptyStringIfJsonNull(JsonElement element) {
        if (element.isJsonNull())
            return "";
        return element.getAsString();
    }
}
