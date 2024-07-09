package com.trekmate;

import com.trekmate.session.UserSession;
import com.trekmate.view.auth.SignInPage;
import com.trekmate.view.dashboards.AdminPage;
import com.trekmate.view.dashboards.UserPage;

import javafx.application.Application;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class MainApp extends Application {

    private UserSession userSession = new UserSession();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Main Application");

        // Set the primary stage width to full screen width
        primaryStage.setWidth(Screen.getPrimary().getBounds().getWidth());
        primaryStage.setHeight(Screen.getPrimary().getBounds().getHeight()); // Set desired height

        // Load the sign in page if the user is not logged in else load the user page
        if (userSession.isLoggedIn()) {
            if (userSession.isAdmin()) {
                // Load the admin page
                AdminPage adminPage = new AdminPage();
                adminPage.start(primaryStage);
            } else {
                // Load the user page
                UserPage userPage = new UserPage();
                userPage.start(primaryStage);
            }
        } else {
            // Load the sign in page
            SignInPage signInPage = new SignInPage();
            signInPage.start(primaryStage);
            
        }
        
    }

    public static void main(String[] args) {
        launch(args);
    }
}
