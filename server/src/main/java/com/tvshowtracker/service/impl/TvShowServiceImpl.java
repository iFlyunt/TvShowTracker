package com.tvshowtracker.service.impl;

import com.tvshowtracker.TvDbException;
import com.tvshowtracker.api.TvDb;
import com.tvshowtracker.api.entity.TvDbSeries;
import com.tvshowtracker.entity.TvShow;
import com.tvshowtracker.repository.TvShowRepository;
import com.tvshowtracker.service.AbstractService;
import com.tvshowtracker.service.TvShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TvShowServiceImpl extends AbstractService<TvShow, Long, TvShowRepository>
        implements TvShowService {
    @Autowired
    private TvDb tvDb;

    protected TvShowServiceImpl(@Autowired TvShowRepository repository) {
        super(repository);
    }

    @Override
    public List<TvShow> findAllShowsByUserId(long userId) {
        return getRepository().findAllShowsByUserId(userId);
    }

    @Override
    public int getSeasonCount(long showId) {
        return getRepository().getSeasonCount(showId);
    }

    @Override
    public List<TvShow> findShowsByName(String name) {
        return getRepository().findShowsByName(name);
    }

    @Override
    public void saveShowsFromTvDbSearchRes(List<TvDbSeries> tvDbSeries) throws TvDbException {
        for (TvDbSeries tvdbShow : tvDbSeries) {
            TvShow tvShow = tvDbShowToTvShowEntity(tvdbShow);
            super.save(tvShow);
        }
    }

    @Override
    public TvShow findShowByTvDbId(long tvDbId) {
        return getRepository().findShowByTvDbId(tvDbId);
    }

    @Override
    public List<TvShow> findRunningShowsByUserId(long userId) {
        return getRepository().findRunningShowsByUserId(userId);
    }

    @Override
    public Optional<TvShow> findShowById(long showId) {
        return getRepository().findById(showId);
    }

    private TvShow tvDbShowToTvShowEntity(TvDbSeries tvDbSeries) throws TvDbException {
        TvShow tvShow = new TvShow();
        int tvDbId = tvDbSeries.getId();
        tvShow.setTvdbId(tvDbId);
        tvShow.setName(tvDbSeries.getName());
        tvShow.setDescription(tvDbSeries.getDescription());
        tvShow.setAirsDayOfWeek(tvDbSeries.getAirsDayOfWeek());
        tvShow.setAirsTime(tvDbSeries.getAirsTime().orElse(null));
        tvShow.setNetwork(tvDbSeries.getNetwork());
        tvShow.setPosterUrl(tvDb.getLatestSeriesPoster(tvDbId));
        tvShow.setStatus(tvDbSeries.getStatus());
        return tvShow;
    }
}
