package com.trekmate;

import com.trekmate.view.auth.SignInPage;
import com.trekmate.view.dashboards.AdminPage;
import com.trekmate.view.dashboards.UserPage;

import javafx.application.Application;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Main Application");

        // Set the primary stage width to full screen width
        primaryStage.setWidth(Screen.getPrimary().getBounds().getWidth());
        primaryStage.setHeight(Screen.getPrimary().getBounds().getHeight()); // Set desired height

        // Show the sign in page
        new UserPage().start(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
