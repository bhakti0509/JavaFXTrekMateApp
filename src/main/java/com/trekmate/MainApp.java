package com.trekmate;

import com.trekmate.manager.SceneManager;
import com.trekmate.session.UserSession;

import javafx.application.Application;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        SceneManager sceneManager = new SceneManager(primaryStage);

        // Load the appropriate page based on user session
        if (UserSession.isLoggedIn()) {
            sceneManager.switchTo("HomePage");
        } else {
            sceneManager.switchTo("SignInPage");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
