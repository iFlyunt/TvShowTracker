package com.tvshowtracker.controller;

import com.tvshowtracker.common.Views;
import com.tvshowtracker.controller.util.SceneLoaderUtil;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public class RegistrationView {
    @FXML
    private JFXTextField usernameInput;
    @FXML
    private JFXTextField passwordInput;
    @FXML
    private JFXTextField lastNameInput;
    @FXML
    private JFXTextField firstNameInput;

    public void signUp(ActionEvent event) throws IOException {
        Parent loginViewParent = FXMLLoader.load(getClass().getResource(Views.LOGIN_VIEW));
        SceneLoaderUtil.loadScene(event, loginViewParent);
    }
}
