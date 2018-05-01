package com.ippt.controller;

import com.ippt.controller.util.SceneLoaderUtil;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public class LoginView {
    @FXML
    private JFXTextField usernameInput;

    @FXML
    private JFXTextField passwordInput;

    public void signIn(ActionEvent event) throws IOException {
        Parent mainWindowViewParent = FXMLLoader
                .load(getClass().getResource("/fxml/MainWindowView.fxml"));
        SceneLoaderUtil.loadScene(event, mainWindowViewParent);
    }

    public void signUp(ActionEvent event) throws IOException {
        Parent registrationViewParent = FXMLLoader
                .load(getClass().getResource("/fxml/RegistrationView.fxml"));
        SceneLoaderUtil.loadScene(event, registrationViewParent);
    }
}
