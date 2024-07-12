package com.trekmate.view.settings;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class SettingsPage extends Application {

    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Settings");

        GridPane grid = new GridPane();
        grid.setVgap(20);
        grid.setHgap(10);
        grid.setPadding(new Insets(20, 10, 10, 10));
        grid.setAlignment(Pos.CENTER); // Center the grid

        // Load notification icon
        Image notificationImage = new Image(getClass().getResourceAsStream("/images/NotificationLogo.jpg"));
        ImageView notificationImageView = new ImageView(notificationImage);
        notificationImageView.setFitWidth(40);
        notificationImageView.setFitHeight(40);
        grid.add(notificationImageView, 0, 0);

        // Notifications setting
        Label notificationsLabel = new Label("Notifications:");
        notificationsLabel.setFont(new Font(20));
        notificationsLabel.setAlignment(Pos.CENTER); // Center the label
        GridPane.setHalignment(notificationsLabel, javafx.geometry.HPos.CENTER); // Center horizontally in cell
        CheckBox notificationsCheckbox = new CheckBox();
        notificationsCheckbox.setMaxSize(70, 70);

        grid.add(notificationsLabel, 1, 0);
        grid.add(notificationsCheckbox, 2, 0);

        // Load language icon
        Image languageImage = new Image(getClass().getResourceAsStream("/images/LanguagesLogo.jpg"));
        ImageView languageImageView = new ImageView(languageImage);
        languageImageView.setFitWidth(40);
        languageImageView.setFitHeight(40);
        grid.add(languageImageView, 0, 1);

        // App language setting
        Label languageLabel = new Label("App Language:");
        languageLabel.setFont(new Font(20));
        languageLabel.setAlignment(Pos.CENTER); // Center the label
        GridPane.setHalignment(languageLabel, javafx.geometry.HPos.CENTER); // Center horizontally in cell
        ChoiceBox<String> languageChoiceBox = new ChoiceBox<>();
        languageChoiceBox.getItems().addAll("English", "Hindi", "Spanish", "French", "German");
        languageChoiceBox.setValue("English");
        GridPane.setHalignment(languageChoiceBox, javafx.geometry.HPos.CENTER); // Center horizontally in cell

        grid.add(languageLabel, 1, 1);
        grid.add(languageChoiceBox, 2, 1);

        // Load change password icon
        Image changePasswordImage = new Image(getClass().getResourceAsStream("/images/LockLogo.jpg"));
        ImageView changePasswordImageView = new ImageView(changePasswordImage);
        changePasswordImageView.setFitWidth(40);

        changePasswordImageView.setFitHeight(40);
        grid.add(changePasswordImageView, 0, 2);

        // Change password button
        Button changePasswordButton = new Button("Change Password");
        changePasswordButton.setOnAction(event -> openChangePasswordWindow());
        changePasswordButton.setAlignment(Pos.CENTER); // Center the button
        GridPane.setHalignment(changePasswordButton, javafx.geometry.HPos.CENTER); // Center horizontally in cell

        grid.add(changePasswordButton, 1, 2);

        // Load terms & conditions icon
        Image termsImage = new Image(getClass().getResourceAsStream("/images/TandC.jpg"));
        ImageView termsImageView = new ImageView(termsImage);
        termsImageView.setFitWidth(40);
        termsImageView.setFitHeight(40);
        grid.add(termsImageView, 0, 3);

        // Terms & Conditions
        Button termsButton = new Button("Terms & Conditions");
        termsButton.setOnAction(event -> showTermsAndConditions());
        termsButton.setAlignment(Pos.CENTER); // Center the button
        GridPane.setHalignment(termsButton, javafx.geometry.HPos.CENTER); // Center horizontally in cell

        grid.add(termsButton, 1, 3);

        // Load about us icon
        Image aboutImage = new Image(getClass().getResourceAsStream("/images/aboutUs.jpg"));
        ImageView aboutImageView = new ImageView(aboutImage);
        aboutImageView.setFitWidth(40);
        aboutImageView.setFitHeight(40);
        grid.add(aboutImageView, 0, 4);

        // About Us
        Button aboutButton = new Button("About Us");
        aboutButton.setOnAction(event -> showAboutUs());
        aboutButton.setAlignment(Pos.CENTER); // Center the button
        GridPane.setHalignment(aboutButton, javafx.geometry.HPos.CENTER); // Center horizontally in cell

        grid.add(aboutButton, 1, 4);

        // Set background image
        Image backgroundImage = new Image(getClass().getResourceAsStream("/images/SettingsBg.jpg"));
        BackgroundImage bgImage = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                new BackgroundSize(0, 0, true, true, true, true));
        Background background = new Background(bgImage);
        grid.setBackground(background);

        Scene settingsScene = new Scene(grid, Screen.getPrimary().getBounds().getWidth(), Screen.getPrimary().getBounds().getHeight());
        primaryStage.setScene(settingsScene);
    }

    private void openChangePasswordWindow() {
        // Launch the NewPassword scene
        ChangePassword newPassword = new ChangePassword();
        newPassword.start(primaryStage);
    }

    private void showTermsAndConditions() {
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.setAlignment(Pos.CENTER); // Center the VBox
        Label termsLabel = new Label("Terms & Conditions content goes here...");

        // Load arrow icon
        Image arrowImage = new Image(getClass().getResourceAsStream("/images/BackButtonLogo.jpg"));
        ImageView arrowImageView = new ImageView(arrowImage);
        arrowImageView.setFitWidth(15);
        arrowImageView.setFitHeight(15);

        Button backButton = new Button("Back", arrowImageView);

        vbox.getChildren().addAll(termsLabel, backButton);

        Scene termsScene = new Scene(vbox, 700, 600);
        primaryStage.setScene(termsScene);
    }

    private void showAboutUs() {
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.setAlignment(Pos.CENTER); // Center the VBox
        Label aboutLabel = new Label("About Us content goes here...");

        // Load arrow icon
        Image arrowImage = new Image(getClass().getResourceAsStream("/images/BackButtonLogo.png"));
        ImageView arrowImageView = new ImageView(arrowImage);
        arrowImageView.setFitWidth(15);
        arrowImageView.setFitHeight(15);

        Button backButton = new Button("Back", arrowImageView);
    

        vbox.getChildren().addAll(aboutLabel, backButton);

        Scene aboutScene = new Scene(vbox, 700, 600);
        primaryStage.setScene(aboutScene);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
