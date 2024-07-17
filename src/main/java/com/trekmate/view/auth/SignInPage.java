package com.trekmate.view.auth;

import com.trekmate.controller.AuthController;
import com.trekmate.manager.SceneManager;
import com.trekmate.model.User;
import com.trekmate.session.UserSession;

import javafx.animation.FadeTransition;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.regex.Pattern;

public class SignInPage {

    private final AuthController authController;
    private final SceneManager sceneManager;

    public SignInPage(SceneManager sceneManager) {
        this.authController = new AuthController();
        this.sceneManager = sceneManager;
    }

    public Scene getScene() {
        // Load the background image
        Image backgroundImage = new Image("images/RajgadFort.jpg");

        // Create a BackgroundImage
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

        // Create the VBox for the login form
        VBox loginForm = new VBox(10);
        loginForm.setAlignment(Pos.CENTER); // Center align the content
        loginForm.setPadding(new Insets(80));
        loginForm.setStyle("-fx-background-color: rgba(255, 255, 255, 0.8); -fx-background-radius: 10;");
        loginForm.setMinSize(400, 400);
        loginForm.setPrefSize(400, 400);
        loginForm.setMaxSize(400, 500); // Ensure the size remains fixed

        // Add widgets
        Label titleLabel = new Label("Sign In");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-text-fill: #333333;");

        Label emailLabel = new Label("Email:");
        emailLabel.setStyle("-fx-text-fill: #333333;");
        TextField emailField = new TextField();
        emailField.setPromptText("Enter your email");

        Label passwordLabel = new Label("Password:");
        passwordLabel.setStyle("-fx-text-fill: #333333;");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter your password");

        // Create a loading indicator
        ProgressIndicator loadingIndicator = new ProgressIndicator();
        loadingIndicator.setVisible(false); // Initially hidden

        Button loginButton = new Button("Login");
        loginButton.setStyle("-fx-background-color: #007bff; -fx-text-fill: white;");
        loginButton.setOnAction(event -> {
            boolean isValid = true;

            // Validate the email and password fields
            if (emailField.getText().isEmpty() || !isValidEmail(emailField.getText())) {
                emailField.setStyle("-fx-border-color: red;");
                isValid = false;
            } else {
                emailField.setStyle(null);
            }

            if (passwordField.getText().isEmpty()) {
                passwordField.setStyle("-fx-border-color: red;");
                isValid = false;
            } else {
                passwordField.setStyle(null);
            }

            if (isValid) {
                // Show loading indicator
                loadingIndicator.setVisible(true);

                Task<Void> task = new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        // Sign in the user
                        User user = authController.signIn(emailField.getText(), passwordField.getText());

                        if (user != null) {
                            // Save the user details in the session
                            UserSession.saveUserDetails(user);
                        } else {
                            throw new IllegalArgumentException("Invalid email or password.");
                        }
                        return null;
                    }

                    @Override
                    protected void succeeded() {
                        super.succeeded();
                        // Hide the loading indicator
                        loadingIndicator.setVisible(false);
                        // get stage 
                        Stage stage = (Stage) loginButton.getScene().getWindow();

                        sceneManager.addOrUpdateScenes(stage); // Refresh the scene to update the user details
                        sceneManager.switchTo("HomePage");
                    }

                    @Override
                    protected void failed() {
                        super.failed();
                        // Hide the loading indicator
                        loadingIndicator.setVisible(false);
                        showAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid email or password.");
                    }
                };

                // Run the task on a new thread
                new Thread(task).start();
            } else {
                showAlert(Alert.AlertType.ERROR, "Login Failed", "Please fill in all required fields correctly.");
            }
        });

        // Create a sign-up link to navigate to the sign-up page
        Label signUpLink = new Label("Don't have an account? Sign up!");
        signUpLink.setStyle("-fx-text-fill: blue; -fx-underline: true; -fx-cursor: hand;");
        signUpLink.setOnMouseClicked(event -> sceneManager.switchTo("SignUpPage"));

        // Add the widgets to the login form
        loginForm.getChildren().addAll(titleLabel, emailLabel, emailField, passwordLabel, passwordField, loginButton, loadingIndicator, signUpLink);
        loginForm.setOpacity(0); // Set the opacity to 0 to fade in later

        // Add the login form to the Pane
        stackPane.getChildren().add(loginForm);

        // Create a fade transition for the login form
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), loginForm);
        fadeTransition.setToValue(1); // Fade to fully opaque
        fadeTransition.play();

        // Setting the scene
        return new Scene(stackPane, Screen.getPrimary().getBounds().getWidth(), Screen.getPrimary().getBounds().getHeight());
    }

    // Check if the email is valid
    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
