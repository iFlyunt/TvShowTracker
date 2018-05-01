package com.ippt.widget;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

public class SearchTile extends TvShowTile {
    private Label subscriptionInfo;

    public SearchTile() {
        super();
        this.subscriptionInfo = new Label();
        this.subscriptionInfo.setStyle("-fx-background-color: white");
        this.subscriptionInfo.setFont(Font.font(14));
        getChildren().add(this.subscriptionInfo);
        setAlignment(Pos.CENTER_RIGHT);
    }

    public Label getSubscriptionInfo() {
        return subscriptionInfo;
    }
}
