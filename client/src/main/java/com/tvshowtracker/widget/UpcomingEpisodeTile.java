package com.tvshowtracker.widget;

import com.jfoenix.controls.JFXButton;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class UpcomingEpisodeTile extends VBox {
    private Label     episodeInfo;
    private JFXButton tvShowBtn;
    private ImageView posterOutput;

    public UpcomingEpisodeTile() {
        createWidgets();
        setupWidgetProperties();
        getChildren().addAll(posterOutput, tvShowBtn, episodeInfo);
        setSpacing(TileConstants.SPACING);
    }

    public Label getEpisodeInfo() {
        return episodeInfo;
    }

    public JFXButton getTvShowBtn() {
        return tvShowBtn;
    }

    public ImageView getPosterOutput() {
        return posterOutput;
    }

    private void setupWidgetProperties() {

        tvShowBtn.setPrefSize(TileConstants.BUTTON_WIDTH, 28);
        tvShowBtn.setStyle("-fx-background-color: #fff;");
        tvShowBtn.setFont(Font.font(15));
        tvShowBtn.setAlignment(Pos.CENTER);
        episodeInfo.setPrefSize(200, 28);
        episodeInfo.setStyle("-fx-background-color: #fff;");
        episodeInfo.setFont(Font.font(15));
        episodeInfo.setAlignment(Pos.CENTER);
        posterOutput.setPreserveRatio(false);
        posterOutput.setFitWidth(TileConstants.IMAGE_VIEW_WIDTH);
        posterOutput.setFitHeight(TileConstants.IMAGE_VIEW_HEIGHT);
    }

    private void createWidgets() {
        episodeInfo = new Label();
        tvShowBtn = new JFXButton();
        posterOutput = new ImageView();
    }
}

