package com.tvshowtracker.service;

import com.tvshowtracker.api.entity.TvDbEpisode;
import com.tvshowtracker.entity.TvShowEpisode;
import com.tvshowtracker.repository.TvShowEpisodeRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface TvEpisodeService extends Service<TvShowEpisode, Long, TvShowEpisodeRepository> {
    List<TvShowEpisode> getAllEpisodesByShowId(long showId);

    void saveEpisodesFromTvDb(Map<Integer, List<TvDbEpisode>> episodesMap, long tvDbId);

    Optional<TvShowEpisode> findUpcomingEpisode(long showId);
}
