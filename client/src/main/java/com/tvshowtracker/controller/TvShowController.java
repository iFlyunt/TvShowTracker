package com.tvshowtracker.controller;

import com.tvshowtracker.http.RequestSender;
import com.tvshowtracker.model.Episode;
import com.tvshowtracker.model.TvShow;
import com.tvshowtracker.unmarshaller.EntityUnmarshaller;
import com.tvshowtracker.unmarshaller.EpisodeUnmarshaller;
import com.tvshowtracker.widget.EpisodeTile;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXToggleButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ExecutionException;

public class TvShowController implements Initializable {
    @FXML
    private Label               showNameOutput;
    @FXML
    private Label               showAiringTimeOutput;
    @FXML
    private Label               networkOutput;
    @FXML
    private Label               seasonsNumberOutput;
    @FXML
    private Label               showStatusOutput;
    @FXML
    private JFXTextArea         showDescriptionOutput;
    @FXML
    private JFXToggleButton     subscribeToggle;
    @FXML
    private JFXComboBox<String> seasonSelector;
    @FXML
    private ImageView           showPoster;
    @FXML
    private VBox                episodeVBox;

    private static final int FIRST_SEASON = 1;

    private TvShow        tvShow;
    private List<Episode> episodes;
    private Map<Integer, List<Episode>> seasonToEpisodeList = new TreeMap<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void setupController(TvShow tvShow) throws URISyntaxException, InterruptedException,
            ExecutionException, IOException {
        this.tvShow = tvShow;
        renderTvShowInfo();
        EntityUnmarshaller<Episode> unmarshaller = new EpisodeUnmarshaller();
        String response = RequestSender.EpisodeEntry.fetchAll(tvShow.getId());
        this.episodes = unmarshaller.unmarshall(response);
        groupEpisodesBySeason();
        showFirstSeason();
        initSeasonSelector();
    }

    @SuppressWarnings("unchecked")
    public void onClickSeasonSelector(ActionEvent event) {
        episodeVBox.getChildren().clear();
        int selectedSeason = seasonSelector.getSelectionModel().getSelectedIndex();
        List<Episode> seasons = (List<Episode>) seasonToEpisodeList.values()
                                                                   .toArray()[selectedSeason];

        seasons.forEach(this::renderEpisodeTile);
    }

    public void onClickSubscribe(ActionEvent event)
            throws URISyntaxException, IOException, InterruptedException, ExecutionException {
        boolean subscribed = subscribeToggle.isSelected();
        String subscriptionMessage = subscribed
                                     ? SubscriptionMessages.SUBSCRIBED_MESSAGE
                                     : SubscriptionMessages.UNSUBSCRIBED_MESSAGE;
        if (subscribed)
            RequestSender.TvShowEntry.subscribe(tvShow.getId());
        else
            RequestSender.TvShowEntry.unsubscribe(tvShow.getId());
        subscribeToggle.setText(subscriptionMessage);
    }

    private void initSeasonSelector() {
        for (Integer seasonNumber : seasonToEpisodeList.keySet())
            seasonSelector.getItems().add("Season " + seasonNumber);
        seasonSelector.getSelectionModel().selectFirst();
    }

    private void renderTvShowInfo() {
        showNameOutput.setText(tvShow.getName());
        showPoster.setImage(new Image(tvShow.getPosterUrl()));
        showAiringTimeOutput.setText(tvShow.getAirsDay() + " at " + tvShow.getTime());
        showStatusOutput.setText(tvShow.getStatus());
        networkOutput.setText(tvShow.getNetwork());
        seasonsNumberOutput.setText(String.valueOf(tvShow.getSeasonCount()) + " seasons");
        showDescriptionOutput.setText(tvShow.getDescription());
        renderSubscribeToggle();
        subscribeToggle.setSelected(tvShow.isSubscribed());
    }

    private void renderSubscribeToggle() {
        if (tvShow.isSubscribed()) {
            subscribeToggle.setText(SubscriptionMessages.SUBSCRIBED_MESSAGE);
            subscribeToggle.setSelected(true);
        }
        else {
            subscribeToggle.setText(SubscriptionMessages.UNSUBSCRIBED_MESSAGE);
            subscribeToggle.setSelected(false);
        }
    }

    private void renderEpisodeTile(Episode episode) {
        EpisodeTile episodeTile = new EpisodeTile();
        JFXToggleButton watchedToggle = episodeTile.getEpisodeWatchedToggle();
        episodeTile.getEpisodeNumberOutput().setText(String.valueOf(episode.getEpisodeNumber()));
        episodeTile.getEpisodeNameBtn().setText(episode.getName());
        episodeTile.getAiringDateOutput().setText(episode.getAirDate());
        watchedToggle.setSelected(episode.isWatched());
        watchedToggle.addEventFilter(ActionEvent.ACTION, onClickEpisodeWatched());
        watchedToggle.setId(String.valueOf(episodes.indexOf(episode)));
        setWatchedToggleBehaviour(watchedToggle, episode);
        episodeVBox.getChildren().add(episodeTile);
    }

    private void setWatchedToggleBehaviour(JFXToggleButton watchedToggle, Episode episode) {
        String airingDateStr = episode.getAirDate();
        if (airingDateStr.isEmpty()) {
            watchedToggle.setDisable(true);
            return;
        }
        LocalDate airingDate = LocalDate.parse(episode.getAirDate());
        if (airingDate.isAfter(LocalDate.now())) {
            watchedToggle.setDisable(true);
            return;
        }
        watchedToggle.setDisable(false);
    }

    private void showFirstSeason() {
        Map.Entry<Integer, List<Episode>> firstSeason = seasonToEpisodeList.entrySet()
                                                                           .iterator().next();
        List<Episode> seasonEpisodes = firstSeason.getValue();
        seasonEpisodes.forEach(this::renderEpisodeTile);
    }

    private void groupEpisodesBySeason() {
        episodes.forEach(episode -> {
            seasonToEpisodeList.putIfAbsent(episode.getSeasonNumber(), new ArrayList<>());
            seasonToEpisodeList.get(episode.getSeasonNumber()).add(episode);
        });
    }

    private EventHandler<ActionEvent> onClickEpisodeWatched() {
        return event -> {
            JFXToggleButton watchedToggle = (JFXToggleButton) event.getSource();
            Episode episode = episodes.get(Integer.parseInt(watchedToggle.getId()));
            try {
                RequestSender.EpisodeEntry.markAsWatched(episode.getId());
                if (isUnsubscribed(watchedToggle))
                    RequestSender.TvShowEntry.subscribe(tvShow.getId());
            } catch (URISyntaxException | InterruptedException
                        | ExecutionException | IOException e) {
                // TODO handle exceptions correctly
                e.printStackTrace();
            }
        };
    }

    private boolean isUnsubscribed(JFXToggleButton watchedToggle) {
        return !subscribeToggle.isSelected() && watchedToggle.isSelected();
    }
}
