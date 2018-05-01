package com.ippt.service;

import com.ippt.entity.Subscriber;
import com.ippt.entity.WatchedEpisode;
import com.ippt.repository.WatchedEpisodeRepository;

import java.util.Optional;

public interface WatchedEpisodeService
        extends Service<WatchedEpisode, Long, WatchedEpisodeRepository> {
    Optional<WatchedEpisode> getWatchedEpisode(long userId, long episodeId);

    void markAsWatched(Subscriber user, long episodeId);
}
