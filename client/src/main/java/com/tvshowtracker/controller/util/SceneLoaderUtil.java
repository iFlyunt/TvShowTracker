package com.tvshowtracker.controller.util;

import com.tvshowtracker.common.Views;

import com.tvshowtracker.controller.MainWindowController;
import com.tvshowtracker.controller.UpcomingEpisodesController;
import com.tvshowtracker.model.TvShow;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public final class SceneLoaderUtil {
    private SceneLoaderUtil() {}

    public static void loadTvShowScene(ActionEvent event, TvShow tvShow) throws IOException {
        FXMLLoader fxmlLoader;
        fxmlLoader = new FXMLLoader(UpcomingEpisodesController.class
                                            .getResource(Views.MAIN_WINDOW_VIEW));
        Parent parent = fxmlLoader.load();
        MainWindowController controller = fxmlLoader.getController();
        controller.showTvShowView(tvShow);
        SceneLoaderUtil.loadScene(event, parent);
    }

    public static void loadScene(ActionEvent event, Parent parent) {
        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setWidth(1360);
        window.setHeight(800);
        window.setScene(scene);
        window.show();
    }
}
