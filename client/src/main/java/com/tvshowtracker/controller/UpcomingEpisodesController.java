package com.tvshowtracker.controller;

import com.tvshowtracker.controller.util.SceneLoaderUtil;
import com.tvshowtracker.http.RequestSender;
import com.tvshowtracker.model.TvShow;
import com.tvshowtracker.model.UpcomingEpisode;
import com.tvshowtracker.unmarshaller.EntityUnmarshaller;
import com.tvshowtracker.unmarshaller.TvShowUnmarshaller;
import com.tvshowtracker.unmarshaller.UpcomingEpisodeUnmarshaller;
import com.tvshowtracker.widget.UpcomingEpisodeTile;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.TilePane;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

public class UpcomingEpisodesController implements Initializable {
    @FXML
    private TilePane upcomingEpisodeTilePane;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            String response = RequestSender.EpisodeEntry.fetchUpcomingEpisodes();
            EntityUnmarshaller<UpcomingEpisode> unmarshaller = new UpcomingEpisodeUnmarshaller();
            List<UpcomingEpisode> upcomingEpisodes = unmarshaller.unmarshall(response);
            upcomingEpisodes.forEach(this::renderUpcomingEpisodeTile);
        } catch (URISyntaxException | InterruptedException | IOException | ExecutionException e) {
            // TODO handle exception properly
            e.printStackTrace();
        }
    }

    private void renderUpcomingEpisodeTile(UpcomingEpisode upcomingEpisode) {
        UpcomingEpisodeTile upcomingEpisodeTile = new UpcomingEpisodeTile();
        final Label episodeInfo = upcomingEpisodeTile.getEpisodeInfo();
        episodeInfo.setText("S" + addLeadingZero(upcomingEpisode.getSeasonNumber())
                            + "E" + addLeadingZero(upcomingEpisode.getEpisodeNumber())
                            + " | " + upcomingEpisode.getAirDate());
        JFXButton tvShowBtn = upcomingEpisodeTile.getTvShowBtn();
        tvShowBtn.setText(upcomingEpisode.getShowName());
        tvShowBtn.addEventFilter(ActionEvent.ACTION, onClickTvShowBtn());
        tvShowBtn.setId(String.valueOf(upcomingEpisode.getShowId()));
        upcomingEpisodeTile.getPosterOutput()
                           .setImage(new Image(upcomingEpisode.getShowPosterUrl()));
        upcomingEpisodeTilePane.getChildren().add(upcomingEpisodeTile);
    }

    private EventHandler<ActionEvent> onClickTvShowBtn() {
        return event -> {
            JFXButton tvShowBtn = (JFXButton) event.getSource();
            long showId = Long.parseLong(tvShowBtn.getId());
            try {
                final String response = RequestSender.TvShowEntry.fetchOne(showId);
                EntityUnmarshaller<TvShow> unmarshaller = new TvShowUnmarshaller();
                TvShow tvShow = unmarshaller.unmarshallEntity(response);
                SceneLoaderUtil.loadTvShowScene(event, tvShow);
            } catch (URISyntaxException | InterruptedException | ExecutionException | IOException e) {
                // TODO handle exception properly
                e.printStackTrace();
            }
        };
    }

    private String addLeadingZero(int number) {
        if (number >= 1 && number <= 9)
            return "0" + number;
        return "" + number;
    }
}
