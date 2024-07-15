package com.trekmate.view.settings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

import com.trekmate.view.homePage.HomePage;

public class ProfilePage{

    private TextField nameField;
    private TextField usernameField;
    private TextField emailField;
    private TextField mobileField;
    private TextField addressField;
    private ImageView profileImageView;
    private ImageView vehicleImageView;
    private Button editButton;
    private Button saveButton;
    private Button uploadProfilePhotoButton;
    private Button backButton;
    private Circle vehicleCircle;
    

    public void start(Stage primaryStage) {
        // Create the profile picture
        Image profileImage = new Image("file:profile_picture.png");
        profileImageView = new ImageView(profileImage);
        profileImageView.setFitWidth(100);
        profileImageView.setFitHeight(100);

        // Create the vehicle picture with a circular shape
        vehicleImageView = new ImageView();
        vehicleImageView.setFitWidth(150);
        vehicleImageView.setFitHeight(150);

        vehicleCircle = new Circle(75, 75, 75);
        vehicleCircle.setStroke(Color.BLACK);
        vehicleCircle.setFill(Color.LIGHTGRAY);
        vehicleImageView.setClip(vehicleCircle);

        // Create a button to upload a profile photo of an user
        uploadProfilePhotoButton = new Button("Upload Photo");
        uploadProfilePhotoButton.setStyle("-fx-font-size: 14px;");
        uploadProfilePhotoButton.setOnAction(e -> uploadProfilePhoto(primaryStage));

        // Create text fields for user details
        nameField = new TextField("");
        nameField.setPromptText("Enter your name");
        nameField.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        usernameField = new TextField("");
        usernameField.setPromptText("Enter your username");
        usernameField.setStyle("-fx-font-size: 16px;-fx-font-weight: bold;");

        emailField = new TextField("");
        emailField.setPromptText("Enter your email");
        emailField.setStyle("-fx-font-size: 16px;-fx-font-weight: bold;");

        mobileField = new TextField("");
        mobileField.setPromptText("Enter your mobile number");
        mobileField.setStyle("-fx-font-size: 16px;-fx-font-weight: bold;");

        addressField = new TextField("");
        addressField.setPromptText("Enter your address");
        addressField.setStyle("-fx-font-size: 16px;-fx-font-weight: bold;");

        // Create buttons for editing and saving the profile
        editButton = new Button("Edit Profile");
        editButton.setStyle("-fx-font-size: 16px;");
        editButton.setOnAction(e -> setEditingMode(true));

        saveButton = new Button("Save Profile");
        saveButton.setStyle("-fx-font-size: 16px;");
        saveButton.setOnAction(e -> setEditingMode(false));
        saveButton.setVisible(false);

        // Create a back button
        backButton = new Button("Back");
        backButton.setStyle("-fx-font-size: 16px;");
        backButton.setOnAction(e -> handleBackButton(primaryStage));

        // Create a layout for the profile details
        VBox detailsLayout = new VBox(5);
        detailsLayout.setAlignment(Pos.CENTER); // Center align all items in detailsLayout
        detailsLayout.getChildren().addAll(
                vehicleImageView,
                uploadProfilePhotoButton,
                new Label("Name:"), nameField,
                new Label("Username:"), usernameField,
                new Label("Email:"), emailField,
                new Label("Mobile No:"), mobileField,
                new Label("Address:"), addressField,
                editButton, saveButton);
        detailsLayout.setPadding(new Insets(10));

        // Create the main layout
        HBox mainLayout = new HBox(20);
        mainLayout.setAlignment(Pos.CENTER); // Center align mainLayout
        mainLayout.getChildren().addAll(profileImageView, detailsLayout);
        mainLayout.setPadding(new Insets(20));

        // Create the bottom layout with the back button
        HBox bottomLayout = new HBox();
        bottomLayout.setAlignment(Pos.CENTER);
        bottomLayout.getChildren().add(backButton);
        bottomLayout.setPadding(new Insets(10));

        // Create the overall layout
        VBox overallLayout = new VBox();
        overallLayout.getChildren().addAll(mainLayout, bottomLayout);

        // Set background image for the overall layout
        Image backgroundImage = new Image("images/ProfPageBg.jpg");
        BackgroundImage background = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true)
        );
        overallLayout.setBackground(new Background(background));

        // Create the scene and set the stage
        Scene Profilescene = new Scene(overallLayout,2080,1080);
        primaryStage.setTitle("TrekMate");
        primaryStage.setFullScreen(true);
        primaryStage.setScene(Profilescene);
        primaryStage.show();

        // Initially set to viewing mode
        setEditingMode(true);
    }

    private void uploadProfilePhoto(Stage primaryStage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Photo");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", ".png", ".jpg", "*.jpeg")
        );
        File selectedFile = fileChooser.showOpenDialog(primaryStage);
        if (selectedFile != null) {
            Image vehicleImage = new Image(selectedFile.toURI().toString());
            vehicleImageView.setImage(vehicleImage);
        }
    }

    private void setEditingMode(boolean isEditing) {
        nameField.setEditable(isEditing);
        usernameField.setEditable(isEditing);
        emailField.setEditable(isEditing);
        mobileField.setEditable(isEditing);
        addressField.setEditable(isEditing);
        editButton.setVisible(!isEditing);
        saveButton.setVisible(isEditing);
        uploadProfilePhotoButton.setVisible(isEditing);
    }

    private void handleBackButton(Stage primaryStage) {
        HomePage homePage = new HomePage();
        try {
            homePage.start(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        backButton.setStyle("-fx-font-size: 14px; -fx-background-color: #ff0000; -fx-text-fill: white;"); // Change color to red
    }
}
