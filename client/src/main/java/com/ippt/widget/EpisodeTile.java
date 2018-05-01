package com.ippt.widget;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXToggleButton;
import com.sun.javafx.font.freetype.HBGlyphLayout;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.text.Font;

public class EpisodeTile extends HBox {
    private Label episodeNumberOutput;
    private JFXButton episodeNameBtn;
    private Region regionAfterEpisodeBtn;
    private Label airingDateOutput;
    private Region regionAfterAiringDateOutput;
    private JFXToggleButton episodeWatchedToggle;

    public EpisodeTile() {
        createComponents();
        setupWidgetsProperties();
        getChildren().addAll(episodeNumberOutput, episodeNameBtn, regionAfterEpisodeBtn,
                             airingDateOutput, regionAfterAiringDateOutput, episodeWatchedToggle);

    }

    public Label getEpisodeNumberOutput() {
        return this.episodeNumberOutput;
    }

    public JFXButton getEpisodeNameBtn() {
        return this.episodeNameBtn;
    }

    public Label getAiringDateOutput() {
        return this.airingDateOutput;
    }

    public JFXToggleButton getEpisodeWatchedToggle() {
        return this.episodeWatchedToggle;
    }

    private void setupWidgetsProperties() {
        setupHBox();
        setupComponentsFonts();
        setupComponentsSizes();
        setupRegionBehaviour();
    }

    private void setupRegionBehaviour() {
        HBox.setHgrow(this.episodeNumberOutput, Priority.ALWAYS);
        HBox.setHgrow(this.episodeNameBtn, Priority.ALWAYS);
        HBox.setHgrow(this.regionAfterEpisodeBtn, Priority.ALWAYS);
        HBox.setHgrow(this.regionAfterAiringDateOutput, Priority.ALWAYS);
    }

    private void setupComponentsSizes() {
        this.episodeNumberOutput.setPrefSize(30, 24);
        this.episodeNameBtn.setPrefSize(300, 30);
        this.regionAfterEpisodeBtn.setPrefSize(50, 60);
        this.airingDateOutput.setPrefSize(74, 20);
        this.regionAfterAiringDateOutput.setPrefSize(320, 60);
        this.episodeWatchedToggle.setPrefSize(76, 60);
    }

    private void setupComponentsFonts() {
        this.episodeNumberOutput.setFont(Font.font(17));
        this.episodeNameBtn.setFont(Font.font(14));
        this.airingDateOutput.setFont(Font.font(14));
    }

    private void setupHBox() {
        this.setStyle("-fx-background-color: #fff;");
        this.setPadding(new Insets(0, 0, 0, 5));
        this.setAlignment(Pos.CENTER_LEFT);
    }

    private void createComponents() {
        this.episodeNumberOutput = new Label();
        this.episodeNameBtn = new JFXButton();
        this.regionAfterEpisodeBtn = new Region();
        this.airingDateOutput = new Label();
        this.regionAfterAiringDateOutput = new Region();
        this.episodeWatchedToggle = new JFXToggleButton();
    }
}
