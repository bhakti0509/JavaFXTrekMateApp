package com.trekmate.view.auth;

import com.trekmate.firebase.FirebaseAuthService;
import com.trekmate.session.UserSession;
import com.trekmate.view.dashboards.AdminPage;
import com.trekmate.view.dashboards.UserPage;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Map;
import java.util.regex.Pattern;

public class SignInPage extends Application {

    private FirebaseAuthService firebaseAuthService;
    private UserSession userSession;

    public SignInPage() {
        this.firebaseAuthService = new FirebaseAuthService();
        this.userSession = new UserSession();
    }

    @Override
    public void start(Stage primaryStage) {
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
        Label titleLabel = new Label("Login");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-text-fill: #333333;");

        Label emailLabel = new Label("Email:");
        emailLabel.setStyle("-fx-text-fill: #333333;");
        TextField emailField = new TextField();
        emailField.setPromptText("Enter your email");

        Label passwordLabel = new Label("Password:");
        passwordLabel.setStyle("-fx-text-fill: #333333;");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter your password");

        Button loginButton = new Button("Login");
        loginButton.setStyle("-fx-background-color: #007bff; -fx-text-fill: white;");
        loginButton.setOnAction(event -> {
            boolean isValid = true;

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
                try {
                    Map<String, Object> userData = firebaseAuthService.signIn(emailField.getText(), passwordField.getText());
                    userSession.saveUserDetails(userData);
                    String role = (String) userData.get("role");
                    if (role.equals("admin")) {
                        loadAdminPage(primaryStage);
                    } else {
                        loadHomePage(primaryStage);
                    }
                } catch (Exception e) {
                    showAlert(Alert.AlertType.ERROR, "Login Failed", e.getMessage());
                }
            } else {
                showAlert(Alert.AlertType.ERROR, "Login Failed", "Please fill in all required fields correctly.");
            }
        });

        Label signUpLink = new Label("Don't have an account? Sign up!");
        signUpLink.setStyle("-fx-text-fill: blue; -fx-underline: true; -fx-cursor: hand;");
        signUpLink.setOnMouseClicked(event -> loadSignUpPage(primaryStage));

        loginForm.getChildren().addAll(titleLabel, emailLabel, emailField, passwordLabel, passwordField, loginButton, signUpLink);

        loginForm.setOpacity(0); // Start fully transparent

        // Add the login form to the Pane
        stackPane.getChildren().add(loginForm);

        // Create a fade transition for the login form
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), loginForm);
        fadeTransition.setToValue(1); // Fade to fully opaque
        fadeTransition.play();

        // Setting the scene
        Scene scene = new Scene(stackPane, 700, 600);

        primaryStage.setTitle("Login Page with Background");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

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

    private void loadSignUpPage(Stage stage) {
        try {
            SignUpPage signUpPage = new SignUpPage();
            signUpPage.start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadHomePage(Stage stage) {
        try {
            UserPage userPage = new UserPage();
            userPage.start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadAdminPage(Stage stage) {
        try {
            AdminPage adminPage = new AdminPage();
            adminPage.start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
