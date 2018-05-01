package com.tvshowtracker.model;

public class Episode extends AbstractEpisode {

    public static class Builder extends AbstractEpisode.Builder<Builder> {
        private boolean watched;

        public Builder setWatched(boolean watched) {
            this.watched = watched;
            return this;
        }

        public Episode build() {
            return new Episode(this);
        }
    }

    private boolean watched;

    private Episode(Builder builder) {
        super(builder);
        this.watched = builder.watched;
    }

    public boolean isWatched() {
        return watched;
    }

    @Override
    public String toString() {
        return "Episode{" + "watched=" + watched + "} " + super.toString();
    }
}
