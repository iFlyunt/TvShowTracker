package com.ippt.api.entity;

import com.ippt.parser.AbstractTvDbEntityParser;

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
