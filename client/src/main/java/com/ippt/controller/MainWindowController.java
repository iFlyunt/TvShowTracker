package com.ippt.controller;

import com.ippt.common.Views;
import com.ippt.model.TvShow;
import com.ippt.controller.util.SceneLoaderUtil;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;

public class MainWindowController {
    @FXML
    private JFXTextField searchInput;
    @FXML
    private AnchorPane sideMenuPane;
    @FXML
    private AnchorPane mainWindowPane;

    public void signOut(ActionEvent actionEvent) throws IOException {
        Parent loginViewParent = FXMLLoader.load(getClass().getResource(Views.LOGIN_VIEW));
        SceneLoaderUtil.loadScene(actionEvent, loginViewParent);
    }

    public void showProfile(ActionEvent event) {

    }

    public void showWatchlist(ActionEvent event) throws IOException {
        Parent watchListView = FXMLLoader.load(getClass().getResource(Views.WATCHLIST_VIEW));
        setupDefaultLayout(watchListView);
        mainWindowPane.getChildren().setAll(watchListView);
    }

    public void showUpcomingEpisodes(ActionEvent event) throws IOException {
        Parent upcomingEpisodesView = FXMLLoader
                .load(getClass().getResource(Views.UPCOMING_EPISODES_VIEW));
        setupDefaultLayout(upcomingEpisodesView);
        mainWindowPane.getChildren().setAll(upcomingEpisodesView);
    }

    public void searchTvShow(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(Views.SEARCH_VIEW));
        Parent searchView = fxmlLoader.load();
        SearchController searchController = fxmlLoader.getController();
        searchController.setSearchText(searchInput.getText());
        setupDefaultLayout(searchView);
        mainWindowPane.getChildren().setAll(searchView);
    }

    public void showTvShowView(TvShow tvShow) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            Parent tvShowView = fxmlLoader.load(getClass().getResource(Views.TV_SHOW_VIEW)
                                                          .openStream());
            TvShowController tvShowController = fxmlLoader.getController();
            tvShowController.setupController(tvShow);
            setupDefaultLayout(tvShowView);
            mainWindowPane.getChildren().setAll(tvShowView);
        } catch (URISyntaxException | InterruptedException | ExecutionException | IOException e) {
            e.printStackTrace();
        }
    }

    private void setupDefaultLayout(Parent view) {
        AnchorPane.setTopAnchor(view, 0.0);
        AnchorPane.setBottomAnchor(view, 0.0);
        AnchorPane.setRightAnchor(view, 0.0);
        AnchorPane.setLeftAnchor(view, sideMenuPane.getLayoutX());
    }
}
