package com.ippt.controller;

import com.ippt.controller.util.SceneLoaderUtil;
import com.ippt.http.RequestSender;
import com.ippt.model.TvShow;
import com.ippt.unmarshaller.EntityUnmarshaller;
import com.ippt.unmarshaller.TvShowUnmarshaller;
import com.ippt.widget.SearchTile;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.TilePane;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLOutput;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.IntStream;

public class SearchController {
    @FXML
    private TilePane searchTilePane;

    private String searchText;

    public void setSearchText(String searchText) {
        this.searchText = searchText;
        try {
            this.initialize();
        } catch (URISyntaxException | InterruptedException | ExecutionException | IOException e) {
            e.printStackTrace();
        }
    }

    private void initialize()
            throws URISyntaxException, InterruptedException, ExecutionException, IOException {
        final String response = RequestSender.TvShowEntry.search(searchText);
        EntityUnmarshaller<TvShow> unmarshaller = new TvShowUnmarshaller();
        List<TvShow> tvShows = unmarshaller.unmarshall(response);
        tvShows.forEach(this::initializeSearchTile);
    }

    private void initializeSearchTile(TvShow tvShow) {
        SearchTile searchTile = new SearchTile();
        searchTile.getPosterOutput().setImage(new Image(tvShow.getPosterUrl()));
        final JFXButton showButton = searchTile.getShowButton();
        final Label subscriptionInfo = searchTile.getSubscriptionInfo();
        showButton.setText(tvShow.getName());
        showButton.addEventFilter(ActionEvent.ACTION, onClickShowButton());
        showButton.setId(String.valueOf(tvShow.getId()));
        renderSubscriptionInfo(tvShow, subscriptionInfo);
        searchTilePane.getChildren().addAll(searchTile);
    }


    private EventHandler<ActionEvent> onClickShowButton() {
        return event -> {
            JFXButton showButton = (JFXButton) event.getSource();
            long showId = Long.parseLong(showButton.getId());
            try {
                String response = RequestSender.TvShowEntry.fetchOne(showId);
                EntityUnmarshaller<TvShow> unmarshaller = new TvShowUnmarshaller();
                TvShow tvShow = unmarshaller.unmarshallEntity(response);
                SceneLoaderUtil.loadTvShowScene(event, tvShow);
            } catch (IOException | InterruptedException
                        | ExecutionException | URISyntaxException e) {
                // TODO handle exception properly
                e.printStackTrace();
            }
        };
    }

    private void renderSubscriptionInfo(TvShow tvShow, Label subscriptionInfo) {
        if (tvShow.isSubscribed())
            subscriptionInfo.setText(SubscriptionMessages.SUBSCRIBED_MESSAGE);
        else
            subscriptionInfo.setText(SubscriptionMessages.UNSUBSCRIBED_MESSAGE);
    }
}
