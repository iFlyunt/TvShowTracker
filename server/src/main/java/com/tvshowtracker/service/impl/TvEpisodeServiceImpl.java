package com.tvshowtracker.service.impl;

import com.tvshowtracker.api.entity.TvDbEpisode;
import com.tvshowtracker.entity.TvShow;
import com.tvshowtracker.entity.TvShowEpisode;
import com.tvshowtracker.repository.TvShowEpisodeRepository;
import com.tvshowtracker.service.AbstractService;
import com.tvshowtracker.service.TvEpisodeService;
import com.tvshowtracker.service.TvShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
public class TvEpisodeServiceImpl
        extends AbstractService<TvShowEpisode, Long, TvShowEpisodeRepository>
        implements TvEpisodeService {
    @Autowired
    private TvShowService showService;

    protected TvEpisodeServiceImpl(@Autowired TvShowEpisodeRepository repository) {
        super(repository);
    }

    @Override
    public List<TvShowEpisode> getAllEpisodesByShowId(long showId) {
        return getRepository().getAllEpisodesByShowId(showId);
    }

    @Override
    public void saveEpisodesFromTvDb(Map<Integer, List<TvDbEpisode>> episodesMap, long tvDbId) {
        TvShow tvShow = showService.findShowByTvDbId(tvDbId);
        for (Map.Entry<Integer, List<TvDbEpisode>> entry : episodesMap.entrySet()) {
            for (TvDbEpisode e : entry.getValue()) {
                TvShowEpisode episode = tvDbEpisodeToTvEpisodeEntity(e, tvShow);
                super.save(episode);
            }
        }
    }

    @Override
    public Optional<TvShowEpisode> findUpcomingEpisode(long showId) {
        final List<TvShowEpisode> episodes;
        episodes = getRepository().findLatestSeasonEpisodesByShowId(showId);
        for (TvShowEpisode episode : episodes) {
            LocalDate airDate = episode.getAirDate();
            if (Objects.isNull(airDate))
                continue;
            if (airDate.isAfter(LocalDate.now()))
                return Optional.of(episode);
        }
        return Optional.empty();
    }

    private LocalDate toLocalDate(String airDate) {
        return LocalDate.parse(airDate);
    }

    private TvShowEpisode tvDbEpisodeToTvEpisodeEntity(TvDbEpisode episode, TvShow tvShow) {
        TvShowEpisode tvShowEpisode = new TvShowEpisode();
        tvShowEpisode.setTvdbId(episode.getId());
        tvShowEpisode.setName(episode.getName());
        tvShowEpisode.setDescription(episode.getDescription());
        tvShowEpisode.setEpisodeNumber(episode.getEpisodeNumber());
        tvShowEpisode.setSeasonNumber(episode.getSeasonNumber());
        tvShowEpisode.setAirDate(episode.getAirDate().orElse(null));
        tvShowEpisode.setTvShow(tvShow);
        return tvShowEpisode;
    }
}
