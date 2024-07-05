package com.trekmate.view.auth;

import javafx.animation.FadeTransition;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

public class SignUpController extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        Image backgroundImage = new Image("images/Vasota2.jpg");
        Image ig = new Image("images/coremain.png");
        ImageView iv = new ImageView(ig);

        // Create a BackgroundImage
        BackgroundImage background = new BackgroundImage(backgroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(400,
                        100,
                        false,
                        false,
                        true,
                        true));

        VBox v = new VBox(iv);
        v.setLayoutX(50);
        v.setAlignment(Pos.TOP_CENTER);

        // Create a Pane and set its background
        Pane pane = new Pane();
        pane.setBackground(new Background(background));
        pane.setPrefSize(500, 600);

        // Create the VBox for the login form
        VBox loginForm = new VBox(10);
        loginForm.setLayoutY(150);
        loginForm.setAlignment(Pos.CENTER);
        loginForm.setPadding(new Insets(50));
        // loginForm.setStyle("-fx-background-color: rgba(255, 255, 255, 0.8);
        // -fx-background-radius: 10;");

        // Add widgets
        Label titleLabel = new Label("SignUp");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-text-fill: #333333;");
        titleLabel.setFont(new Font(20));

        Label firstnameLabel = new Label("First Name:");
        firstnameLabel.setStyle("-fx-text-fill: #333333;");
        TextField firstnameField = new TextField();
        firstnameField.setPromptText("Enter your Firstname");

        Label lastnameLabel = new Label("Lastname:");
        lastnameLabel.setStyle("-fx-text-fill: #333333;");
        TextField lastnameField = new TextField();
        lastnameField.setPromptText("Enter your lastname");

        Label usernameLabel = new Label("Username:");
        usernameLabel.setStyle("-fx-text-fill: #333333;");
        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter your username");

        Label passwordLabel = new Label("Password:");
        passwordLabel.setStyle("-fx-text-fill: #333333;");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter your password");

        Button loginButton = new Button("Login");
        loginButton.setStyle("-fx-background-color: #007bff; -fx-text-fill: white;");
        loginButton.setOnAction(event -> {
            // Handle login logic here
            System.out.println("Login clicked");
        });

        HBox hb = new HBox(20, firstnameLabel, firstnameField, lastnameLabel, lastnameField);
        // hb.setLayoutY(10);

        loginForm.getChildren().addAll(titleLabel, hb, usernameLabel, usernameField, passwordLabel, passwordField,
                loginButton);

        // Position the VBox initially outside the scene
        loginForm.setTranslateX(-400); // Start off-screen to the left
        loginForm.setOpacity(0); // Start fully transparent

        // Add the login form to the Pane
        pane.getChildren().add(loginForm);
        pane.getChildren().add(v);

        // Create a translate transition to move the login form into view
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(7), loginForm);
        translateTransition.setToX(300); // Move to the center of the scene

        // Create a fade transition for the login form
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(7), loginForm);
        fadeTransition.setToValue(1); // Fade to fully opaque

        // Play both transitions together
        translateTransition.play();
        fadeTransition.play();

        // Create a rotate transition for the ImageView
        RotateTransition rotateTransition = new RotateTransition(Duration.seconds(1.5), iv);
        rotateTransition.setByAngle(360);
        rotateTransition.setAxis(Rotate.Y_AXIS);
        rotateTransition.setCycleCount(RotateTransition.INDEFINITE);
        rotateTransition.play();

        // Setting the scene
        Scene scene = new Scene(pane, 700, 600);

        primaryStage.setTitle("Login Page with Background");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
