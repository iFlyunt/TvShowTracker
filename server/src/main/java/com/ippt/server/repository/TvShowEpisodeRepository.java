package com.ippt.server.repository;

import com.ippt.server.entity.TvShowEpisode;
import com.ippt.server.entity.WatchedEpisode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TvShowEpisodeRepository extends JpaRepository<TvShowEpisode, Long> {
    @Query("select e from TvShowEpisode e where e.tvShow.id = ?1")
    List<TvShowEpisode> getAllEpisodesByShowId(long showId);

    @Query("select e"
           + " from TvShowEpisode e"
           + " where e.tvShow.id = ?1"
           + " and e.seasonNumber = (select max(ep.seasonNumber)"
           + " from TvShowEpisode ep where ep.tvShow.id = e.tvShow.id)"
           + " order by e.episodeNumber asc")
    List<TvShowEpisode> findLatestSeasonEpisodesByShowId(long showId);
}
