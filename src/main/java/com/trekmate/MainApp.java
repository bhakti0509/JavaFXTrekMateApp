package com.trekmate;

import com.trekmate.session.UserSession;
import com.trekmate.view.auth.SignInPage;
import com.trekmate.view.homePage.HomePage;

import javafx.application.Application;
import javafx.stage.Stage;

public class MainApp extends Application {

    private UserSession userSession = new UserSession();

    @Override
    public void start(Stage primaryStage) {

        // Load the sign in page if the user is not logged in else load the user page
        if (userSession.isLoggedIn()) {
            HomePage homePage = new HomePage();
            homePage.start(primaryStage);
           
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
