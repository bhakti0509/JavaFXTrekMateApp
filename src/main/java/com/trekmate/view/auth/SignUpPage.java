package com.trekmate.view.auth;

import com.trekmate.controller.AuthController;
import com.trekmate.manager.SceneManager;
import com.trekmate.model.User;

import javafx.animation.FadeTransition;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Screen;
import javafx.util.Duration;

import java.util.regex.Pattern;

public class SignUpPage {

    private AuthController authController;
    private SceneManager sceneManager;
    private StackPane stackPane;

    public SignUpPage(SceneManager sceneManager) {
        this.authController = new AuthController();
        this.sceneManager = sceneManager;
    }

    public Scene getScene() {
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
        this.stackPane = new StackPane();
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
        titleLabel.setFont(javafx.scene.text.Font.font("Sans", javafx.scene.text.FontWeight.BOLD, 30)); // Bold font
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
        signInLink.setOnMouseClicked(event -> sceneManager.switchTo("SignInPage"));

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

        // Handle sign up button action
        signUpButton.setOnAction(event -> {
            boolean isValid = validateFields(firstNameField, lastNameField, emailField, usernameField, passwordField);

            if (isValid) {
                signUpUser(firstNameField.getText(), lastNameField.getText(), emailField.getText(),
                        usernameField.getText(), passwordField.getText());
            } else {
                showAlert(Alert.AlertType.ERROR, "Sign Up Failed", "Please fill in all required fields correctly.");
            }
        });

        return scene;
    }

    private boolean validateFields(TextField firstNameField, TextField lastNameField, TextField emailField,
                                   TextField usernameField, PasswordField passwordField) {
        boolean isValid = true;

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

        return isValid;
    }

    private void signUpUser(String firstName, String lastName, String email, String username, String password) {
        ProgressIndicator progressIndicator = new ProgressIndicator();
        stackPane.getChildren().add(progressIndicator);

        Task<Void> signUpTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {

                // Sign up the user
                User user = createUser(firstName, lastName, email, username, password);
                authController.signUp(user);
                return null;
            }

            @Override
            protected void succeeded() {
                stackPane.getChildren().remove(progressIndicator);
                showAlert(Alert.AlertType.INFORMATION, "Sign Up Successful", "Account created successfully. Please sign in.");
                sceneManager.switchTo("SignInPage");
            }

            @Override
            protected void failed() {
                stackPane.getChildren().remove(progressIndicator);
                Throwable e = getException();
                showAlert(Alert.AlertType.ERROR, "Sign Up Failed", e.getMessage());
            }
        };

        new Thread(signUpTask).start();
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

    private User createUser(String firstName, String lastName, String email, String username, String password) {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(password);
        return user;
    }
}
