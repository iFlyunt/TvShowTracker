package com.tvshowtracker.api.entity;

import com.tvshowtracker.parser.AbstractTvDbEntityParser;

abstract public class AbstractTvDbEntity {
    private AbstractTvDbEntityParser tvDbEntityParser;

    public AbstractTvDbEntity(AbstractTvDbEntityParser tvDbEntityParser) {
        this.tvDbEntityParser = tvDbEntityParser;
    }

    public int getId() {
        return tvDbEntityParser.getId();
    }

    public String getDescription() {
        return tvDbEntityParser.getDescription();
    }
}
