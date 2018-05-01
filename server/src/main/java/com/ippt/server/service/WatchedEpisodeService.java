package com.ippt.server.service;

import com.ippt.server.entity.Subscriber;
import com.ippt.server.entity.WatchedEpisode;
import com.ippt.server.repository.WatchedEpisodeRepository;

import java.util.Optional;

public interface WatchedEpisodeService
        extends Service<WatchedEpisode, Long, WatchedEpisodeRepository> {
    Optional<WatchedEpisode> getWatchedEpisode(long userId, long episodeId);

    void markAsWatched(Subscriber user, long episodeId);
}
