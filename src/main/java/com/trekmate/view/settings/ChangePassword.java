package com.trekmate.view.settings;

import com.trekmate.manager.SceneManager;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import javafx.animation.ScaleTransition;
import javafx.geometry.Pos;

public class ChangePassword {

    private SceneManager sceneManager;

    public ChangePassword(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    public Scene createScene() {
        // Load the background image
        Image backgroundImage = new Image("images/SettingsBg.jpg");

        // Create a BackgroundImage
        BackgroundImage background = new BackgroundImage(backgroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(800, 600, false, false, true, false));

        // Create a Pane and set its background
        Pane pane = new Pane();
        pane.setBackground(new Background(background));
        pane.setPrefSize(800, 600);

        // Create the VBox for the new password form
        VBox newPassword = new VBox(15);
        newPassword.setAlignment(Pos.CENTER);
        newPassword.setPadding(new javafx.geometry.Insets(50));

        newPassword.setStyle("-fx-background-color: rgba(255, 255, 255, 0.8); -fx-background-radius: 10;");

        // Add widgets
        Label titleLabel = new Label("Reset Password");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-text-fill: #333333;");

        Label newPasswordLabel = new Label("New Password:");
        newPasswordLabel.setStyle("-fx-text-fill: #333333;");
        PasswordField newPasswordField = new PasswordField();
        newPasswordField.setPromptText("Enter your new password");

        Label confirmPasswordLabel = new Label("Confirm Password:");
        confirmPasswordLabel.setStyle("-fx-text-fill: #333333;");
        PasswordField confirmPasswordField = new PasswordField();
        confirmPasswordField.setPromptText("Confirm your new password");

        Button changePasswordButton = new Button("Change Password");
        changePasswordButton.setStyle("-fx-background-color: #007bff; -fx-text-fill: white;");
        changePasswordButton.setOnAction(event -> {
            if (newPasswordField.getText().equals(confirmPasswordField.getText())) {
                System.out.println("Successfully Changed Password");
            } else {
                System.out.println("Invalid Password");
            }
            System.out.println("Change Password clicked");
        });

        // Load arrow icon
        Image arrowImage = new Image(getClass().getResourceAsStream("/images/BackButtonLogo.jpg"));
        ImageView arrowImageView = new ImageView(arrowImage);
        arrowImageView.setFitWidth(15);
        arrowImageView.setFitHeight(15);

        // Back button
        Button backButton = new Button("Back", arrowImageView);
        backButton.setStyle("-fx-background-color: #007bff; -fx-text-fill: white;");
        backButton.setOnAction(event -> {
            sceneManager.switchTo("HomePage"); 
        });

        newPassword.getChildren().addAll(titleLabel, newPasswordLabel, newPasswordField, confirmPasswordLabel, confirmPasswordField, changePasswordButton, backButton);

        // Position the VBox in the center of the Pane
        newPassword.setLayoutX(450); // Adjust to center the form
        newPassword.setLayoutY(150);

        // Add the new password form and ImageView to the Pane
        pane.getChildren().addAll(newPassword);

        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(5), newPassword);
        scaleTransition.setFromX(1.0);
        scaleTransition.setToX(1.3);
        scaleTransition.setFromY(1.0);
        scaleTransition.setToY(1.3);
        scaleTransition.play();

        // Setting the scene
        return new Scene(pane, 800, 600);
    }
}
