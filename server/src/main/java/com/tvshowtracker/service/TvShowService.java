package com.tvshowtracker.service;

import com.tvshowtracker.TvDbException;
import com.tvshowtracker.api.entity.TvDbSeries;
import com.tvshowtracker.entity.TvShow;
import com.tvshowtracker.repository.TvShowRepository;

import java.util.List;
import java.util.Optional;

public interface TvShowService extends Service<TvShow, Long, TvShowRepository> {
    List<TvShow> findAllShowsByUserId(long userId);

    int getSeasonCount(long showId);

    List<TvShow> findShowsByName(String name);

    void saveShowsFromTvDbSearchRes(List<TvDbSeries> tvDbSeries) throws TvDbException;

    TvShow findShowByTvDbId(long tvDbId);

    List<TvShow> findRunningShowsByUserId(long userId);

    Optional<TvShow> findShowById(long showId);
}
