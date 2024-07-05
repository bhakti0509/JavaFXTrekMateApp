package com.trekmate.view.auth;

import com.trekmate.firebase.FirebaseAuthService;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

public class SignUpPage extends Application {

    @Override
    public void start(Stage primaryStage) {

        // Load images
        Image backgroundImage = new Image(getClass().getResource("/images/signUpBg.jpg").toString());

        // Create background image
        BackgroundImage background = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(400, 100, false, false, true, true)
        );

        // Create the StackPane and set its background
        StackPane stackPane = new StackPane();
        stackPane.setBackground(new Background(background));
        stackPane.setPrefSize(700, 600);

        // Create the VBox for the signup form
        VBox signUpForm = new VBox(30);
        signUpForm.setAlignment(Pos.TOP_CENTER);
        signUpForm.setPadding(new Insets(25));
        signUpForm.setStyle("-fx-background-color: rgba(255, 255, 255, 0.8); -fx-background-radius: 10;");
        signUpForm.setMinSize(400, 400);
        signUpForm.setPrefSize(400, 400);
        signUpForm.setMaxSize(400, 500); // Ensure the size remains fixed

        // Add widgets
        Label titleLabel = new Label("Sign Up");
        titleLabel.setFont(Font.font("Sans", FontWeight.BOLD, 30)); // Bold font
        titleLabel.setStyle("-fx-text-fill: #333333; -fx-margin-bottom: 10px;"); // Add margin
        titleLabel.setPadding(new Insets(0, 0, 10, 0)); // Padding bottom

        TextField firstNameField = createTextField("First Name", "Enter your first name");
        TextField lastNameField = createTextField("Last Name", "Enter your last name");
        TextField emailField = createTextField("Email", "Enter your email");
        TextField usernameField = createTextField("Username", "Enter your username");
        PasswordField passwordField = createPasswordField("Password", "Enter your password");

        Button signUpButton = new Button("Sign Up");
        signUpButton.setStyle("-fx-background-color: #007bff; -fx-text-fill: white;");
        signUpButton.setOnAction(event -> {
            // Handle signup logic here
            String email = emailField.getText();
            String username = usernameField.getText();
            String password = passwordField.getText();
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();

            FirebaseAuthService firebaseAuthService = new FirebaseAuthService();
            try {
                firebaseAuthService.createUser(email, password, username, firstName, lastName, "user");
                loadLoginPage(primaryStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        HBox nameFields = new HBox(20, firstNameField, lastNameField);
        nameFields.setAlignment(Pos.CENTER);

        signUpForm.getChildren().addAll(titleLabel, nameFields, emailField, usernameField, passwordField, signUpButton);

        // Add the signup form to the StackPane
        stackPane.getChildren().add(signUpForm);

        signUpForm.setOpacity(0); // Start fully transparent

        // Create a fade transition for the signup form
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1.5), signUpForm);
        fadeTransition.setToValue(1); // Fade to fully opaque
        fadeTransition.play();

        // Setting the scene
        Scene scene = new Scene(stackPane, 700, 600);
        primaryStage.setTitle("Sign Up");
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
