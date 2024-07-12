package com.trekmate.view.auth;

import com.trekmate.firebase.FirebaseAuthService;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.regex.Pattern;

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
        VBox signUpForm = new VBox(20);
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

        Label signInLink = new Label("Already have an account? Sign in here.");
        signInLink.setStyle("-fx-text-fill: blue; -fx-underline: true; -fx-cursor: hand;");
        signInLink.setOnMouseClicked(event -> loadLoginPage(primaryStage));

        HBox nameFields = new HBox(20, firstNameField, lastNameField);
        nameFields.setAlignment(Pos.CENTER);

        signUpForm.getChildren().addAll(titleLabel, nameFields, emailField, usernameField, passwordField, signUpButton, signInLink);

        // Add the signup form to the StackPane
        stackPane.getChildren().add(signUpForm);

        signUpForm.setOpacity(0); // Start fully transparent

        // Create a fade transition for the signup form
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1.5), signUpForm);
        fadeTransition.setToValue(1); // Fade to fully opaque
        fadeTransition.play();

        // Setting the scene
        Scene scene = new Scene(stackPane, Screen.getPrimary().getBounds().getWidth(), Screen.getPrimary().getBounds().getHeight());
        primaryStage.setTitle("Sign Up");
        primaryStage.setScene(scene);
        primaryStage.show();

        signUpButton.setOnAction(event -> {
            boolean isValid = true;

            // Validate the fields firstName, lastName, email, username, and password
            if (firstNameField.getText().isEmpty()) {
                firstNameField.setStyle("-fx-border-color: red;");
                isValid = false;
            } else {
                firstNameField.setStyle(null);
            }

            if (lastNameField.getText().isEmpty()) {
                lastNameField.setStyle("-fx-border-color: red;");
                isValid = false;
            } else {
                lastNameField.setStyle(null);
            }

            if (emailField.getText().isEmpty() || !isValidEmail(emailField.getText())) {
                emailField.setStyle("-fx-border-color: red;");
                isValid = false;
            } else {
                emailField.setStyle(null);
            }

            if (usernameField.getText().isEmpty()) {
                usernameField.setStyle("-fx-border-color: red;");
                isValid = false;
            } else {
                usernameField.setStyle(null);
            }

            if (passwordField.getText().isEmpty()) {
                passwordField.setStyle("-fx-border-color: red;");
                isValid = false;
            } else {
                passwordField.setStyle(null);
            }

            if (isValid) {
                // Show loading indicator
                ProgressIndicator progressIndicator = new ProgressIndicator();
                stackPane.getChildren().add(progressIndicator);

                Task<Void> signUpTask = new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        String email = emailField.getText();
                        String username = usernameField.getText();
                        String password = passwordField.getText();
                        String firstName = firstNameField.getText();
                        String lastName = lastNameField.getText();

                        FirebaseAuthService firebaseAuthService = new FirebaseAuthService();
                        firebaseAuthService.createUser(email, password, username, firstName, lastName, "user");
                        return null;
                    }

                    @Override
                    protected void succeeded() {
                        stackPane.getChildren().remove(progressIndicator);
                        loadLoginPage(primaryStage);
                    }

                    @Override
                    protected void failed() {
                        stackPane.getChildren().remove(progressIndicator);
                        Throwable e = getException();
                        showAlert(Alert.AlertType.ERROR, "Sign Up Failed", e.getMessage());
                    }
                };

                new Thread(signUpTask).start();
            } else {
                showAlert(Alert.AlertType.ERROR, "Sign Up Failed", "Please fill in all required fields correctly.");
            }
        });
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
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

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Load the login page
    private void loadLoginPage(Stage stage) {
        try {
            SignInPage signInPage = new SignInPage();
            signInPage.start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
