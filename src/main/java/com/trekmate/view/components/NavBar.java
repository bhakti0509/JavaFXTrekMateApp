package com.trekmate.view.components;

import com.trekmate.session.UserSession;
import com.trekmate.view.auth.SignInPage;
import com.trekmate.view.settings.SettingsPage;
import com.trekmate.view.trek.AddTrekPage;

import javafx.geometry.Insets;
import javafx.geometry.Point3D;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.Map;

public class NavBar {

    private UserSession userSession = new UserSession();
    private Stage primaryStage;

    public NavBar(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public HBox createNavBar() {
        HBox navBar = new HBox(10);
        navBar.setAlignment(Pos.CENTER_LEFT);
        navBar.setPrefSize(Screen.getPrimary().getBounds().getWidth(), 60);
        navBar.setStyle("-fx-background-color: #333;");
        navBar.setPadding(new Insets(10));

        Text appName = createAppName();
        ImageView logoView = createLogoView();
        Button myBookingsButton = createNavButton("My Bookings", 200, this::handleMyBookings);
        Button leaderboardButton = createNavButton("Leaderboard", 200, this::handleLeaderboard);

        if (userSession.isLoggedIn()) {
            Map<String, Object> userDetails = userSession.getUserDetails();
            String firstName = (String) userDetails.get("firstName");
            String lastName = (String) userDetails.get("lastName");
            Label userNameLabel = new Label(firstName + " " + lastName);
            userNameLabel.setStyle("-fx-font-size: 20px;-fx-text-fill: white;-fx-font-weight: bold;");

            ImageView profileImage = new ImageView(new Image("images/logo.jpg")); // Placeholder image
            profileImage.setFitHeight(40);
            profileImage.setFitWidth(40);
            profileImage.setClip(new javafx.scene.shape.Circle(20, 20, 20));

            Button profileButton = createNavButton("Profile", 150, this::handleProfile);
            Button settingsButton = createNavButton("Settings", 150, this::handleSettings);
            Button createTrekButton = createNavButton("Add Trek", 200, this::handleCreateTrek);
            Button logoutButton = createNavButton("Logout", 150, this::handleLogout);

            HBox userBox = new HBox(10);
            userBox.setAlignment(Pos.CENTER);
            userBox.getChildren().addAll(profileImage, userNameLabel);

            navBar.getChildren().addAll(logoView, appName, myBookingsButton, leaderboardButton, profileButton, settingsButton, createTrekButton, logoutButton, userBox);
        } else {
            Button loginButton = createNavButton("Login", 150, this::handleLogin);
            Button signUpButton = createNavButton("Sign Up", 150, this::handleSignUp);

            navBar.getChildren().addAll(logoView, appName, myBookingsButton, leaderboardButton, loginButton, signUpButton);
        }

        return navBar;
    }

    private Text createAppName() {
        Text appName = new Text("TrekMate");
        appName.setFill(Color.GREEN);
        appName.setStyle("-fx-font-size: 40px;-fx-font-weight: Bold;");
        appName.setRotationAxis(Point3D.ZERO);

        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(Color.AQUAMARINE);
        dropShadow.setOffsetX(5);
        dropShadow.setOffsetY(5);
        appName.setEffect(dropShadow);

        return appName;
    }

    private ImageView createLogoView() {
        ImageView logoView = new ImageView(new Image("images/logo.jpg")); // Placeholder image
        logoView.setFitHeight(60);
        logoView.setFitWidth(60);
        logoView.setClip(new javafx.scene.shape.Circle(30, 30, 30));
        return logoView;
    }

    private Button createNavButton(String text, int width, javafx.event.EventHandler<javafx.event.ActionEvent> handler) {
        Button button = new Button(text);
        button.setPrefSize(width, 20);
        button.setStyle("-fx-font-size: 20px;-fx-background-color: #333;-fx-text-fill: white; -fx-padding: 10;-fx-font-weight: bold;");
        button.setOnMouseEntered(event -> button.setStyle("-fx-font-size: 20px;-fx-background-color: #777;-fx-text-fill: white; -fx-padding: 10;-fx-font-weight: bold;"));
        button.setOnMouseExited(event -> button.setStyle("-fx-font-size: 20px;-fx-background-color: #333;-fx-text-fill: white; -fx-padding: 10;-fx-font-weight: bold;"));
        button.setOnAction(handler);
        return button;
    }

    private void handleMyBookings(javafx.event.ActionEvent event) {
        // Handle My Bookings button click
        System.out.println("My Bookings button clicked");
    }

    private void handleLeaderboard(javafx.event.ActionEvent event) {
        // Handle Leaderboard button click
        System.out.println("Leaderboard button clicked");
    }

    private void handleProfile(javafx.event.ActionEvent event) {
        // Handle Profile button click
        System.out.println("Profile button clicked");
    }

    private void handleSettings(javafx.event.ActionEvent event) {
        // Handle Settings button click
        SettingsPage settingsPage = new SettingsPage();
        settingsPage.start(primaryStage);
    }

    private void handleLogin(javafx.event.ActionEvent event) {
        // Handle Login button click
        System.out.println("Login button clicked");
    }

    private void handleSignUp(javafx.event.ActionEvent event) {
        // Handle Sign Up button click
        System.out.println("Sign Up button clicked");
    }

    private void handleCreateTrek(javafx.event.ActionEvent event) {
        // Handle Create Trek button click
        AddTrekPage addTrekPage = new AddTrekPage();
        addTrekPage.start(primaryStage);
    }

    private void handleLogout(javafx.event.ActionEvent event) {
        // Handle Logout button click
        userSession.logout();
        System.out.println("User logged out");

        // Redirect to SignInPage
        SignInPage signInPage = new SignInPage();
        try {
            signInPage.start(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
