package com.trekmate.view.auth;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class SignUpController extends Application {

    @Override
    public void start(Stage primaryStage) {

        // Create the Pane
        Pane pane = new Pane();
        pane.setPrefSize(700, 600);

        // Create the VBox for the signup form
        VBox signUpForm = new VBox(10);
        signUpForm.setAlignment(Pos.CENTER);
        signUpForm.setPadding(new Insets(50));
        signUpForm.setStyle("-fx-background-color: #f5f5f5; -fx-border-radius: 10; -fx-background-radius: 10; -fx-border-color: #cccccc;");

        // Add widgets
        Label titleLabel = new Label("Sign Up");
        titleLabel.setFont(new Font(24));
        titleLabel.setStyle("-fx-text-fill: #333333;");

        TextField firstNameField = createTextField("First Name", "Enter your first name");
        TextField lastNameField = createTextField("Last Name", "Enter your last name");
        TextField usernameField = createTextField("Username", "Enter your username");
        PasswordField passwordField = createPasswordField("Password", "Enter your password");

        Button signUpButton = new Button("Sign Up");
        signUpButton.setStyle("-fx-background-color: #007bff; -fx-text-fill: white;");
        signUpButton.setOnAction(event -> {
            // Handle signup logic here
            System.out.println("Sign Up clicked");
            // Load login page
            loadLoginPage(primaryStage);
        });

        HBox nameFields = new HBox(10, firstNameField, lastNameField);
        nameFields.setAlignment(Pos.CENTER);

        signUpForm.getChildren().addAll(titleLabel, nameFields, usernameField, passwordField, signUpButton);

        // Position the VBox initially outside the scene
        signUpForm.setTranslateX(-400); // Start off-screen to the left
        signUpForm.setOpacity(0); // Start fully transparent

        // Add the signup form to the Pane
        pane.getChildren().add(signUpForm);

        // Create a translate transition to move the signup form into view
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(1.5), signUpForm);
        translateTransition.setToX(150); // Move to the center of the scene

        // Create a fade transition for the signup form
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1.5), signUpForm);
        fadeTransition.setToValue(1); // Fade to fully opaque

        // Play both transitions together
        translateTransition.play();
        fadeTransition.play();

        // Setting the scene
        Scene scene = new Scene(pane, 700, 600, Color.WHITE);
        primaryStage.setTitle("Sign Up Page");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private TextField createTextField(String label, String promptText) {
        Label fieldLabel = new Label(label);
        fieldLabel.setStyle("-fx-text-fill: #333333;");
        TextField textField = new TextField();
        textField.setPromptText(promptText);
        VBox fieldContainer = new VBox(fieldLabel, textField);
        fieldContainer.setSpacing(5);
        return textField;
    }

    private PasswordField createPasswordField(String label, String promptText) {
        Label fieldLabel = new Label(label);
        fieldLabel.setStyle("-fx-text-fill: #333333;");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText(promptText);
        VBox fieldContainer = new VBox(fieldLabel, passwordField);
        fieldContainer.setSpacing(5);
        return passwordField;
    }

    private void loadLoginPage(Stage stage) {
        try {
            LoginPage loginPage = new LoginPage();
            loginPage.start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
