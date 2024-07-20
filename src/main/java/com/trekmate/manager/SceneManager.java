package com.trekmate.manager;

import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.HashMap;

import com.trekmate.view.auth.SignInPage;
import com.trekmate.view.auth.SignUpPage;
import com.trekmate.view.homePage.HomePage;
import com.trekmate.view.leaderboard.LeaderboardPage;
import com.trekmate.view.settings.AboutUs;
                                                                                                   
import com.trekmate.view.settings.ChangePassword;

import com.trekmate.view.settings.ProfilePage;
import com.trekmate.view.settings.SettingsPage;

import com.trekmate.view.settings.TermsAndConditions;
import com.trekmate.view.trek.AddTrekPage;
import com.trekmate.view.trek.MyBooking;

public class SceneManager {

    private Stage primaryStage;
    private HashMap<String, Scene> scenes = new HashMap<>();

    public SceneManager(Stage primaryStage) {
        this.primaryStage = primaryStage;

        addOrUpdateScenes(primaryStage);
    }

    public void addScene(String name, Scene scene) {
        scenes.put(name, scene);
    }

    public void switchTo(String sceneName) {
        Scene scene = scenes.get(sceneName);
        if (scene != null) {
            primaryStage.setScene(scene);
            primaryStage.show();
        }
    }

    public void addOrUpdateScenes(Stage primaryStage) {
        
        // Create the scenes
        SignInPage signInPage = new SignInPage(this);
        Scene signInScene = signInPage.getScene();

        HomePage homePage = new HomePage(this);
        Scene homeScene = homePage.getScene(primaryStage);

        SignUpPage signUpPage = new SignUpPage(this);
        Scene signUpScene = signUpPage.getScene();

        SettingsPage settingsPage = new SettingsPage(this);
        Scene settingsScene = settingsPage.createScene();

        ChangePassword changePassword = new ChangePassword(this);
        Scene changePasswordScene = changePassword.createScene();

        AboutUs aboutus = new AboutUs(this);
        Scene aboutusScene = aboutus.createScene();

        TermsAndConditions terms = new TermsAndConditions(this);
        Scene termScene  = terms.createScene();

        ProfilePage profilePage = new ProfilePage(this);
        Scene profileScene = profilePage.createScene();

        AddTrekPage addTrekPage = new AddTrekPage(this);
        Scene addTrekScene = addTrekPage.createScene();

        LeaderboardPage leaderboard = new LeaderboardPage(this);
        Scene leaderboardScene  = leaderboard.createScene();

        MyBooking myBookingPage = new MyBooking(this);
        Scene myBookingScene = myBookingPage.getScene();


        // Add scenes to the map
        this.addScene("SignInPage", signInScene);
        this.addScene("SignUpPage", signUpScene);
        this.addScene("HomePage", homeScene);
        this.addScene("SettingsPage", settingsScene);
        this.addScene("ChangePassword", changePasswordScene);
        this.addScene("AboutUs", aboutusScene); // Add Aboutus scene to SceneManager
        this.addScene("ProfilePage", profileScene);
        this.addScene("AddTrekPage", addTrekScene);
        this.addScene("LeaderboardPage", leaderboardScene);
        this.addScene("TermsAndConditions", termScene); 
        this.addScene("MyBooking", myBookingScene);

    }

    public void refreshMyBookingsPage() {
        MyBooking myBookingPage = new MyBooking(this);
        Scene myBookingScene = myBookingPage.getScene();
        this.addScene("MyBooking", myBookingScene);
    }

}

