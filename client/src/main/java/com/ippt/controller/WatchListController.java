package com.ippt.controller;

import com.ippt.controller.util.SceneLoaderUtil;
import com.ippt.http.RequestSender;
import com.ippt.model.TvShow;
import com.ippt.unmarshaller.EntityUnmarshaller;
import com.ippt.unmarshaller.TvShowUnmarshaller;
import com.ippt.widget.TvShowTile;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.layout.TilePane;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import java.util.stream.IntStream;

public class WatchListController implements Initializable {
    @FXML
    private TilePane showTilePane;

    private List<TvShow> tvShows;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            String response = RequestSender.TvShowEntry.fetchAll();
            EntityUnmarshaller<TvShow> unmarshaller = new TvShowUnmarshaller();
            tvShows = unmarshaller.unmarshall(response);
            tvShows.forEach(this::renderTvShowTile);
        } catch (URISyntaxException | InterruptedException | ExecutionException | IOException e) {
            e.printStackTrace();
        }
    }

    private void renderTvShowTile(TvShow tvShow) {
        TvShowTile tvShowTile = new TvShowTile();
        JFXButton tvShowBtn = tvShowTile.getShowButton();
        tvShowTile.getPosterOutput().setImage(new Image(tvShow.getPosterUrl()));
        tvShowBtn.setText(tvShow.getName());
        tvShowBtn.addEventFilter(ActionEvent.ACTION, onClickShow());
        tvShowBtn.setId(String.valueOf(tvShow.getId()));
        showTilePane.getChildren().add(tvShowTile);
    }

    private EventHandler<ActionEvent> onClickShow() {
        return (ActionEvent event) -> {
            try {
                JFXButton btn = (JFXButton) event.getSource();
                int showId = Integer.parseInt(btn.getId());
                String response = RequestSender.TvShowEntry.fetchOne(showId);
                EntityUnmarshaller<TvShow> unmarshaller = new TvShowUnmarshaller();
                TvShow tvShow = unmarshaller.unmarshallEntity(response);
                SceneLoaderUtil.loadTvShowScene(event, tvShow);
            } catch (IOException | InterruptedException
                        | URISyntaxException | ExecutionException e) {
                // TODO here must be logging of exception
                e.printStackTrace();
            }
        };
    }
}
