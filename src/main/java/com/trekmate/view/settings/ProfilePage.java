package com.trekmate.view.settings;

import com.trekmate.manager.SceneManager;
import com.trekmate.model.User;
import com.trekmate.session.UserSession;
import com.trekmate.view.components.NavBar;
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
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.File;

public class ProfilePage {

    private TextField firstNameField;
    private TextField lastNameField;
    private TextField usernameField;
    private TextField emailField;
    private TextField mobileField;
    private TextField addressField;
    private ImageView profileImageView;
    private Button editButton;
    private Button saveButton;
    private Button uploadProfilePhotoButton;
    private Circle vehicleCircle;
    private SceneManager sceneManager;

    public ProfilePage(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    public Scene createScene() {
        // Fetch user details from session
        User user = UserSession.getUserDetails();

        // Create the profile picture
        if (user != null && user.getProfilePictureUrl() != null && !user.getProfilePictureUrl().isEmpty()) {
            profileImageView = new ImageView(new Image(user.getProfilePictureUrl()));
        } else {
            profileImageView = new ImageView(new Image("/images/avatar.png")); // Default avatar image
        }
        profileImageView.setFitWidth(150);
        profileImageView.setFitHeight(150);

        vehicleCircle = new Circle(75, 75, 75);
        vehicleCircle.setStroke(Color.BLACK);
        vehicleCircle.setFill(Color.LIGHTGRAY);
        profileImageView.setClip(vehicleCircle);

        // Create a button to upload a profile photo of a user
        uploadProfilePhotoButton = new Button("Upload Photo");
        uploadProfilePhotoButton.setStyle("-fx-font-size: 14px;");
        uploadProfilePhotoButton.setOnAction(e -> uploadProfilePhoto());

        // Create text fields for user details
        firstNameField = new TextField(user != null ? user.getFirstName() : "");
        firstNameField.setPromptText("Enter your first name");
        firstNameField.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        lastNameField = new TextField(user != null ? user.getLastName() : "");
        lastNameField.setPromptText("Enter your last name");
        lastNameField.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        usernameField = new TextField(user != null ? user.getUsername() : "");
        usernameField.setPromptText("Enter your username");
        usernameField.setStyle("-fx-font-size: 16px;-fx-font-weight: bold;");

        emailField = new TextField(user != null ? user.getEmail() : "");
        emailField.setPromptText("Enter your email");
        emailField.setStyle("-fx-font-size: 16px;-fx-font-weight: bold;");

        mobileField = new TextField(user != null ? user.getMobileNo() : "");
        mobileField.setPromptText("Enter your mobile number");
        mobileField.setStyle("-fx-font-size: 16px;-fx-font-weight: bold;");

        addressField = new TextField(user != null ? user.getAddress() : "");
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

        // Create a layout for the profile details
        VBox detailsLayout = new VBox(10);
        detailsLayout.setAlignment(Pos.CENTER);
        detailsLayout.getChildren().addAll(
                profileImageView,
                uploadProfilePhotoButton,
                new Label("First Name:"), firstNameField,
                new Label("Last Name:"), lastNameField,
                new Label("Username:"), usernameField,
                new Label("Email:"), emailField,
                new Label("Mobile No:"), mobileField,
                new Label("Address:"), addressField,
                editButton, saveButton);
        detailsLayout.setPadding(new Insets(10, 30, 10, 30));
        detailsLayout.setMaxWidth(450); // Limit the width
        detailsLayout.setStyle("-fx-background-color: rgba(255, 255, 255, 0.7); -fx-border-color: black; -fx-border-width: 2px;"); // Semi-transparent white background
        
        // Create the main layout
        VBox mainLayout = new VBox(20);
        mainLayout.setAlignment(Pos.TOP_CENTER);
        mainLayout.getChildren().addAll(detailsLayout);
        mainLayout.setPadding(new Insets(20));
        mainLayout.setStyle("-fx-background-color: rgba(0, 0, 0, 0.7); -fx-border-color: black;"); // Semi-transparent black background
        mainLayout.setMinHeight(Screen.getPrimary().getBounds().getHeight());
        mainLayout.setMinWidth(Screen.getPrimary().getBounds().getWidth());

        // Add NavBar to the layout
        NavBar navBar = new NavBar(sceneManager);
        HBox navBarBox = navBar.createNavBar();

        // Create the overall layout
        VBox overallLayout = new VBox();
        overallLayout.getChildren().addAll(navBarBox, mainLayout);

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

        // Set editing mode to false
        setEditingMode(false);

        // Create the scene with primary screen bounds
        Scene profileScene = new Scene(overallLayout, Screen.getPrimary().getBounds().getWidth(), Screen.getPrimary().getBounds().getHeight());
        return profileScene;
    }

    private void uploadProfilePhoto() {
        Stage stage = (Stage) uploadProfilePhotoButton.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Photo");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            Image profileImage = new Image(selectedFile.toURI().toString());
            profileImageView.setImage(profileImage);
        }
    }

    private void setEditingMode(boolean isEditing) {
        firstNameField.setEditable(isEditing);
        lastNameField.setEditable(isEditing);
        usernameField.setEditable(isEditing);
        emailField.setEditable(isEditing);
        mobileField.setEditable(isEditing);
        addressField.setEditable(isEditing);
        editButton.setVisible(!isEditing);
        saveButton.setVisible(isEditing);
        uploadProfilePhotoButton.setVisible(isEditing);
    }
}
