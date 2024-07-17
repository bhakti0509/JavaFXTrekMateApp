package com.trekmate.view.settings;

import com.trekmate.manager.SceneManager;
import com.trekmate.view.components.NavBar;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Screen;

import java.util.Arrays;
import java.util.List;

public class SettingsPage {

    private static final String BACKGROUND_IMAGE_PATH = "/images/SettingsBg.jpg";
    private static final String NOTIFICATION_IMAGE_PATH = "/images/NotificationLogo.jpg";
    private static final String LANGUAGE_IMAGE_PATH = "/images/LanguagesLogo.jpg";
    private static final String CHANGE_PASSWORD_IMAGE_PATH = "/images/LockLogo.jpg";
    private static final String TERMS_IMAGE_PATH = "/images/TandC.jpg";
    private static final String ABOUT_IMAGE_PATH = "/images/aboutUs.jpg";

    private static final List<String> LANGUAGES = Arrays.asList("English", "Hindi", "Spanish", "French", "German");

    private SceneManager sceneManager;

    public SettingsPage(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    public Scene createScene() {
        // Load background image
        BackgroundImage backgroundImage = new BackgroundImage(
                new Image(SettingsPage.class.getResourceAsStream(BACKGROUND_IMAGE_PATH)),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT, new BackgroundSize(400, 100, false, false, true, true)
        );

        // Root StackPane
        StackPane root = new StackPane();
        root.setBackground(new Background(backgroundImage));

        // Main layout VBox
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.TOP_CENTER);

        // Navigation bar
        NavBar navBar = new NavBar(sceneManager);
        HBox navBarBox = navBar.createNavBar();
        layout.getChildren().add(navBarBox);

        // Settings grid
        GridPane grid = createSettingsGrid();
        layout.getChildren().add(grid);

        // Add layout to root StackPane
        root.getChildren().add(layout);

        // Create and return scene
        return new Scene(root, Screen.getPrimary().getBounds().getWidth(), Screen.getPrimary().getBounds().getHeight());
    }

    private GridPane createSettingsGrid() {
        GridPane grid = new GridPane();
        grid.setVgap(20);
        grid.setHgap(10);
        grid.setPadding(new Insets(20, 10, 10, 10));
        grid.setAlignment(Pos.CENTER);

        // Notifications
        ImageView notificationImageView = createImageView(NOTIFICATION_IMAGE_PATH, 40, 40);
        Label notificationsLabel = createCenteredLabel("Notifications", 20);
        CheckBox notificationsCheckbox = new CheckBox();
        grid.add(notificationImageView, 0, 0);
        grid.add(notificationsLabel, 1, 0);
        grid.add(notificationsCheckbox, 2, 0);

        // Language
        ImageView languageImageView = createImageView(LANGUAGE_IMAGE_PATH, 40, 40);
        Label languageLabel = createCenteredLabel("App Language", 20);
        ChoiceBox<String> languageChoiceBox = new ChoiceBox<>();
        languageChoiceBox.getItems().addAll(LANGUAGES);
        languageChoiceBox.setValue("English");
        grid.add(languageImageView, 0, 1);
        grid.add(languageLabel, 1, 1);
        grid.add(languageChoiceBox, 2, 1);

        // Change Password
        ImageView changePasswordImageView = createImageView(CHANGE_PASSWORD_IMAGE_PATH, 40, 40);
        Button changePasswordButton = createCenteredButton("Change Password", event -> sceneManager.switchTo("ChangePassword"));
        grid.add(changePasswordImageView, 0, 2);
        grid.add(changePasswordButton, 1, 2);

        // Terms & Conditions
        ImageView termsImageView = createImageView(TERMS_IMAGE_PATH, 40, 40);
        Button termsButton = createCenteredButton("Terms & Conditions", event -> sceneManager.switchTo("TermsAndConditions"));
        grid.add(termsImageView, 0, 3);
        grid.add(termsButton, 1, 3);

        // About Us
        ImageView aboutImageView = createImageView(ABOUT_IMAGE_PATH, 40, 40);
        Button aboutButton = createCenteredButton("About Us", event -> sceneManager.switchTo("AboutUs"));
        grid.add(aboutImageView, 0, 4);
        grid.add(aboutButton, 1, 4);

        return grid;
    }

    private ImageView createImageView(String imagePath, double fitWidth, double fitHeight) {
        Image image = new Image(SettingsPage.class.getResourceAsStream(imagePath));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(fitWidth);
        imageView.setFitHeight(fitHeight);
        return imageView;
    }

    private Label createCenteredLabel(String text, double fontSize) {
        Label label = new Label(text);
        label.setFont(new Font(fontSize));
        label.setAlignment(Pos.CENTER);
        GridPane.setHalignment(label, javafx.geometry.HPos.CENTER);
        return label;
    }

    private Button createCenteredButton(String text, EventHandler<ActionEvent> handler) {
        Button button = new Button(text);
        button.setOnAction(handler);
        button.setAlignment(Pos.CENTER);
        GridPane.setHalignment(button, javafx.geometry.HPos.CENTER);
        return button;
    }
}
