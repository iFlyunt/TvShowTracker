package com.ippt.service;

import com.ippt.api.entity.TvDbEpisode;
import com.ippt.entity.TvShowEpisode;
import com.ippt.repository.TvShowEpisodeRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface TvEpisodeService extends Service<TvShowEpisode, Long, TvShowEpisodeRepository> {
    List<TvShowEpisode> getAllEpisodesByShowId(long showId);

    void saveEpisodesFromTvDb(Map<Integer, List<TvDbEpisode>> episodesMap, long tvDbId);

    Optional<TvShowEpisode> findUpcomingEpisode(long showId);
}
