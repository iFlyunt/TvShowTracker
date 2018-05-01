package com.tvshowtracker.model;

public abstract class AbstractEntity {

    public abstract static class Builder<B extends Builder> {
        private long id;
        private String name;
        private String description;
        private B self;

        protected Builder() {
            this.self = (B) this;
        }

        public B setId(long id) {
            this.id = id;
            return self;
        }

        public B setName(String name) {
            this.name = name;

            return self;
        }

        public B setDescription(String description) {
            this.description = description;
            return self;
        }

        protected B builder() {
            return self;
        }
    }

    private long id;
    private String name;
    private String description;

    protected AbstractEntity(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.description = builder.description;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "AbstractEntity{" + "id=" + id + ", name='" + name + '\'' + ", description='"
               + description + '\'' + '}';
    }
}
