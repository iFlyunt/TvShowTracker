package com.tvshowtracker.controller;

import com.tvshowtracker.TvDbException;
import com.tvshowtracker.api.TvDb;
import com.tvshowtracker.api.entity.TvDbSeries;
import com.tvshowtracker.entity.*;
import com.tvshowtracker.model.response.*;
import com.tvshowtracker.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/shows")
public class TvShowController {
    @Autowired
    private TvShowService showService;

    @Autowired
    private TvEpisodeService tvEpisodeService;

    @Autowired
    private WatchedEpisodeService watchedEpisodeService;

    @Autowired
    private UserService userService;

    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private TvDb tvDb;

    @GetMapping("/search")
    public ResponseEntity<?> searchShow(@RequestParam("name") String showName)
            throws TvDbException {
        List<TvShowResponse> responses = searchInLocalDb(showName);
        if (responses.size() == 0) {
            List<TvDbSeries> tvDbSeries = tvDb.searchSeries(showName);
            saveSearchResFromTvDB(tvDbSeries);
            responses = searchInLocalDb(showName);
        }
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }

    @PostMapping("/subscribe/{id}")
    public ResponseEntity<?> addShow(@PathVariable("id") long showId) {
        Subscriber user = getSubscriber();
        Optional<Subscription> subscription;
        subscription = subscriptionService.findSubscriptionByClientIdAndTvShowId(user.getId(),
                                                                                 showId);
        if (subscription.isPresent())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body(new ErrorResponse("you are already subscribed"));
        TvShow tvShow = showService.findById(showId);
        subscriptionService.subscribe(user, tvShow);
        return ResponseEntity.status(HttpStatus.OK)
                             .body(new InfoResponse("you've successfully subscribed to show"));
    }

    @RequestMapping(value = "/unsubscribe/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> unsubscribe(@PathVariable("id") long showId) {
        Subscriber user = getSubscriber();
        long userId = user.getId();
        subscriptionService.findSubscriptionByClientIdAndTvShowId(userId, showId)
                           .ifPresent(subscriptionService::delete);
        deleteWatchedEpisodes(showId, userId);
        return ResponseEntity.status(HttpStatus.OK)
                             .body(new InfoResponse("you've successfully unsubscribed from show"));
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllShows() {
        Subscriber user = getSubscriber();
        List<TvShow> tvShows = showService.findAllShowsByUserId(user.getId());
        List<TvShowResponse> tvShowResponses = new ArrayList<>();
        tvShows.forEach(s -> tvShowResponses.add(createTvShowResponse(s)));
        return ResponseEntity.status(HttpStatus.OK).body(tvShowResponses);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getShow(@PathVariable("id") long showId) {
        final Optional<TvShow> tvShow = showService.findShowById(showId);
        TvShowResponse tvShowResponse = tvShow.map(this::createTvShowResponse).orElse(null);
        return ResponseEntity.status(HttpStatus.OK).body(tvShowResponse);
    }

    @GetMapping("/{id}/episodes")
    public ResponseEntity<?> getAllShowEpisodes(@PathVariable long id) {
        List<TvShowEpisode> episodes = tvEpisodeService.getAllEpisodesByShowId(id);
        List<TvEpisodeResponse> tvEpisodeResponses = new ArrayList<>();
        episodes.forEach(e -> tvEpisodeResponses.add(createEpisodesResponse(e)));
        return ResponseEntity.status(HttpStatus.OK).body(tvEpisodeResponses);
    }

    @PostMapping("/episodes/watched/{id}")
    public ResponseEntity<?> markAsWatched(@PathVariable("id") long episodeId) {
        Subscriber user = getSubscriber();
        watchedEpisodeService.markAsWatched(user, episodeId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/episodes/upcoming")
    public ResponseEntity<?> getUpcomingEpisodes() {
        Subscriber user = getSubscriber();
        final List<TvShow> shows = showService.findRunningShowsByUserId(user.getId());
        List<UpcomingEpisodeResponse> upcomingEpisodeResponses = new ArrayList<>();
        System.out.println("upcoming");
        shows.forEach(s -> createUpcomingEpisodeResponse(s).ifPresent(upcomingEpisodeResponses::add));
        return ResponseEntity.status(HttpStatus.OK).body(upcomingEpisodeResponses);
    }

    private Optional<UpcomingEpisodeResponse> createUpcomingEpisodeResponse(TvShow tvShow) {
        final Optional<TvShowEpisode> ue = tvEpisodeService.findUpcomingEpisode(tvShow.getId());
        System.out.println(ue.isPresent());
        return ue.map(e -> {
            UpcomingEpisodeResponse upcomingEpisodeResponse = UpcomingEpisodeResponse.toResponse(e);
            upcomingEpisodeResponse.setShowPoster(tvShow.getPosterUrl());
            upcomingEpisodeResponse.setShowId(tvShow.getId());
            upcomingEpisodeResponse.setShowName(tvShow.getName());
            return upcomingEpisodeResponse;
        });
    }

    private TvShowResponse createTvShowResponse(TvShow tvShow) {
        TvShowResponse response = TvShowResponse.toResponse(tvShow);
        response.setSeasonCount(showService.getSeasonCount(tvShow.getId()));
        subscriptionService.findSubscriptionByClientIdAndTvShowId(getSubscriber().getId(),
                                                                  tvShow.getId())
                           .ifPresent(s -> response.setSubscribed(true));
        return response;
    }

    private void saveTvShowsEpisodes(List<TvDbSeries> tvDbSeries) throws TvDbException {
        for (TvDbSeries show : tvDbSeries) {
            int tvdbId = show.getId();
            tvEpisodeService.saveEpisodesFromTvDb(tvDb.getAllSeasons(tvdbId), tvdbId);
        }
    }

    private void saveSearchResFromTvDB(List<TvDbSeries> tvDbSeries) throws TvDbException {
        showService.saveShowsFromTvDbSearchRes(tvDbSeries);
        saveTvShowsEpisodes(tvDbSeries);
    }

    private List<TvShowResponse> searchInLocalDb(String showName) {
        List<TvShowResponse> tvShowResponses = new ArrayList<>();
        showService.findShowsByName(showName)
                   .forEach(s -> tvShowResponses.add(createTvShowResponse(s)));
        return tvShowResponses;
    }

    private TvEpisodeResponse createEpisodesResponse(TvShowEpisode episode) {
        Subscriber user = getSubscriber();
        boolean watched = watchedEpisodeService.getWatchedEpisode(user.getId(), episode.getId())
                                               .map(WatchedEpisode::isWatched).orElse(false);
        TvEpisodeResponse episodeResponse = TvEpisodeResponse.toResponse(episode);
        episodeResponse.setWatched(watched);
        return episodeResponse;
    }

    private void deleteWatchedEpisodes(long showId, long userId) {
        List<TvShowEpisode> episodes = tvEpisodeService.getAllEpisodesByShowId(showId);
        for (TvShowEpisode episode : episodes)
            watchedEpisodeService.getWatchedEpisode(userId, episode.getId())
                                 .ifPresent(watchedEpisodeService::delete);
    }

    private Subscriber getSubscriber() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.findUserByUsername(username);
    }
}
