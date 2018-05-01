package com.ippt.server.repository;

import com.ippt.server.entity.WatchedEpisode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface WatchedEpisodeRepository extends JpaRepository<WatchedEpisode, Long> {
    @Query("select we from WatchedEpisode we"
           + " where we.subscriber.id = ?1"
           + " and we.episode.id = ?2")
    Optional<WatchedEpisode> getEpisodeStatus(long userId, long episodeId);
}
