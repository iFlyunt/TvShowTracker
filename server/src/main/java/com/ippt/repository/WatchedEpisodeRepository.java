package com.ippt.repository;

import com.ippt.entity.WatchedEpisode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface WatchedEpisodeRepository extends JpaRepository<WatchedEpisode, Long> {
    @Query("select we from WatchedEpisode we"
           + " where we.subscriber.id = ?1"
           + " and we.episode.id = ?2")
    Optional<WatchedEpisode> getEpisodeStatus(long userId, long episodeId);
}
