package com.ippt.widget;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class TvShowTile extends VBox {
    private JFXButton showButton;
    private ImageView posterOutput;

    public TvShowTile() {
        createAllComponents();
        initializeAllComponents();
        getChildren().addAll(this.posterOutput, this.showButton);
        setSpacing(TileConstants.SPACING);
    }

    public JFXButton getShowButton() {
        return this.showButton;
    }

    public ImageView getPosterOutput() {
        return this.posterOutput;
    }

    private void initializeAllComponents() {
        this.showButton.setStyle("-fx-background-color: #fff;");
        this.showButton.setFont(Font.font(14));
        setupComponentsSizes();
    }

    private void setupComponentsSizes() {
        this.showButton.setPrefSize(TileConstants.BUTTON_WIDTH, TileConstants.BUTTON_HEIGHT);
        this.posterOutput.setFitWidth(TileConstants.IMAGE_VIEW_WIDTH);
        this.posterOutput.setFitHeight(TileConstants.IMAGE_VIEW_HEIGHT);
        this.posterOutput.setPreserveRatio(false);
    }

    private void createAllComponents() {
        this.showButton = new JFXButton();
        this.posterOutput = new ImageView();
    }
}
