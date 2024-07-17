package com.trekmate.view.components;

import com.trekmate.manager.SceneManager;
import com.trekmate.model.User;
import com.trekmate.session.UserSession;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Screen;

public class NavBar {

    private SceneManager sceneManager;

    public NavBar(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    public HBox createNavBar() {
        HBox navBar = new HBox(10);
        navBar.setAlignment(Pos.CENTER);
        navBar.setPrefWidth(Screen.getPrimary().getBounds().getWidth());
        navBar.setStyle("-fx-background-color: #333;");
        navBar.setPadding(new Insets(10));

        Text appName = createAppName();
        ImageView logoView = createLogoView();
        Button homePageButton = createNavButton("Home", this::handleHomePage);
        Button myBookingsButton = createNavButton("My Bookings", this::handleMyBookings);
        Button leaderboardButton = createNavButton("Leaderboard", this::handleLeaderboard);

        if (UserSession.isLoggedIn()) {
            User userDetails = UserSession.getUserDetails();
            String firstName = userDetails.getFirstName();
            String lastName = userDetails.getLastName();
            MenuButton profileButton = createDropDownButton(firstName + " " + lastName);
            Button createTrekButton = createNavButton("Add Trek", this::handleCreateTrek);

            navBar.getChildren().addAll(logoView, appName, homePageButton, myBookingsButton, leaderboardButton);
            if (UserSession.isAdmin()) {
                navBar.getChildren().add(createTrekButton);
            }
            navBar.getChildren().add(profileButton);
            
        } else {
            Button signUpButton = createNavButton("Sign Up", this::handleSignUp);
            Button signInButton = createNavButton("Sign In", this::handleSignIn);
            navBar.getChildren().addAll(logoView, appName, homePageButton, myBookingsButton, leaderboardButton, signUpButton, signInButton);
        }

        return navBar;
    }

    private Text createAppName() {
        Text appName = new Text("TrekMate");
        appName.setFont(Font.font("Arial", 25));
        appName.setFill(Color.WHITE);
        return appName;
    }

    private ImageView createLogoView() {
        ImageView logoView = new ImageView(new Image("images/logo.png"));
        logoView.setFitHeight(50);
        logoView.setFitWidth(50);
        return logoView;
    }

    private Button createNavButton(String text, Runnable action) {
        Button button = new Button(text);
        // Set minimum width to ensure text wrapping
        button.setMinWidth(Region.USE_PREF_SIZE);
        button.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 16px;");
        button.setOnAction(e -> action.run());
        
        // Add hover effect
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #555; -fx-text-fill: white; -fx-font-size: 16px;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 16px;"));
        
        return button;
    }

    private MenuButton createDropDownButton(String name) {
        Label nameLabel = new Label(name);
        nameLabel.setStyle("-fx-text-fill: white; -fx-font-size: 16px;");
    
        MenuButton dropDown = new MenuButton("", nameLabel);
        dropDown.setGraphicTextGap(10);
        dropDown.setStyle("-fx-background-color: #333;");
    
        MenuItem profileItem = new MenuItem("Profile");
        MenuItem settingsItem = new MenuItem("Settings");
        MenuItem logoutItem = new MenuItem("Logout");
    
        profileItem.setStyle("-fx-text-fill: black; -fx-font-size: 16px;");
        settingsItem.setStyle("-fx-text-fill: black; -fx-font-size: 16px;");
        logoutItem.setStyle("-fx-text-fill: black; -fx-font-size: 16px;");
    
        profileItem.setOnAction(e -> handleProfile());
        settingsItem.setOnAction(e -> handleSettings());
        logoutItem.setOnAction(e -> handleLogout());
    
        dropDown.getItems().addAll(profileItem, settingsItem, new SeparatorMenuItem(), logoutItem);
        return dropDown;
    }

    private void handleHomePage() {
        sceneManager.switchTo("HomePage");
    }

    private void handleMyBookings() {
        sceneManager.switchTo("MyBookingsPage");
    }

    private void handleLeaderboard() {
        sceneManager.switchTo("LeaderboardPage");
    }

    private void handleProfile() {
        sceneManager.switchTo("ProfilePage");
    }

    private void handleSettings() {
        sceneManager.switchTo("SettingsPage");
    }

    private void handleCreateTrek() {
        sceneManager.switchTo("AddTrekPage");
    }

    private void handleSignUp() {
        sceneManager.switchTo("SignUpPage");
    }

    private void handleSignIn() {
        sceneManager.switchTo("SignInPage");
    }

    private void handleLogout() {
        UserSession.logout();
        sceneManager.switchTo("SignInPage");
    }
}
