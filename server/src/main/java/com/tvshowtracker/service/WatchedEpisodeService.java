package com.tvshowtracker.service;

import com.tvshowtracker.entity.Subscriber;
import com.tvshowtracker.entity.WatchedEpisode;
import com.tvshowtracker.repository.WatchedEpisodeRepository;

import java.util.Optional;

public interface WatchedEpisodeService
        extends Service<WatchedEpisode, Long, WatchedEpisodeRepository> {
    Optional<WatchedEpisode> getWatchedEpisode(long userId, long episodeId);

    void markAsWatched(Subscriber user, long episodeId);
}
