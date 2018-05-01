package com.tvshowtracker;

import com.tvshowtracker.common.Views;
import com.tvshowtracker.http.RequestSender;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    static {
        System.setProperty("org.apache.commons.logging.Log",
                           "org.apache.commons.logging.impl.SimpleLog");
        System.setProperty("org.apache.commons.logging.simplelog.showdatetime", "true");
        System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.http", "DEBUG");
        System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.http.wire",
                           "ERROR");
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource(Views.LOGIN_VIEW));
        primaryStage.setTitle("Tv Show Tracker");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    @Override
    public void stop() throws IOException {
        RequestSender.HTTP_HANDLER.closeConnection();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
