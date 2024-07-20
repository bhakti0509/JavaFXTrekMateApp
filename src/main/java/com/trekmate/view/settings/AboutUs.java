package com.trekmate.view.settings;

import com.trekmate.manager.SceneManager;
import com.trekmate.model.User;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;

public class AboutUs {

    private SceneManager sceneManager;

    public AboutUs(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    public Scene createScene() {
        // Fetch user details from session (mocking user details for About Us page)
        User user = new User();
        user.setFirstName("Bug");
        user.setLastName("Optimizers");
        user.setEmail("treakmate@gmail.com");

        // Creating labels with information
        Label welcomeLabel = new Label("Welcome to TrekMate!");
        welcomeLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        welcomeLabel.setTextFill(Color.YELLOW);

        Label missionLabel = new Label("About Our App :");
        missionLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        missionLabel.setTextFill(Color.WHITE);

        Label missionText = new Label("At TrekMate, we are passionate about adventure and connecting people with unforgettable trekking experiences.\n Whether you're a seasoned trekker or new to the trail, TrekMate is here to guide you every step of the way.\nOur mission is to simplify your trekking journey by providing detailed information on various trekking routes, insightful tips on gear\n and preparation, and a community of fellow adventurers to share experiences with.\n With TrekMate, embark on journeys that take you through breathtaking landscapes, discover hidden trails, and forge lasting memories.");
        missionText.setFont(Font.font(17));
        missionText.setTextFill(Color.WHITE);

        // About Us section
        Label aboutLabel = new Label("About Us");
        aboutLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        aboutLabel.setTextFill(Color.WHITE);

        Label groupNameLabel = new Label("Group Name: " + user.getFirstName() + " " + user.getLastName());
        groupNameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        groupNameLabel.setTextFill(Color.WHITE);

        Label membersLabel = new Label("Group Members:\n- Bhakti Satpute\n- Sarita Disale \n- Priyanka Karmalkar");
        membersLabel.setFont(Font.font(20));
        membersLabel.setTextFill(Color.WHITE);

        Label conceptsLabel = new Label("Concepts Used:\n- Inheritance\n- Polymorphism\n- Abstraction\n- Interface\n- Exception Handling\n- JavaFX\n- Maven\n- MVC Pattern\n- Firestore ");
        conceptsLabel.setFont(Font.font(17));
        conceptsLabel.setTextFill(Color.WHITE);

        // Create images for social media icons (mocking images)
        Image instagramIcon = new Image("images/instagram.png");
        ImageView instagramView = new ImageView(instagramIcon);
        instagramView.setFitWidth(70);
        instagramView.setPreserveRatio(true);

        Image linkedinIcon = new Image("images/linkedin.png");
        ImageView linkedinView = new ImageView(linkedinIcon);
        linkedinView.setFitWidth(70);
        linkedinView.setPreserveRatio(true);

        Image facebookIcon = new Image("images/facebook.png");
        ImageView facebookView = new ImageView(facebookIcon);
        facebookView.setFitWidth(70);
        facebookView.setPreserveRatio(true);

        // Create an HBox for social media icons
        HBox socialMediaBox = new HBox(10);
        socialMediaBox.getChildren().addAll(instagramView, linkedinView, facebookView);
        socialMediaBox.setAlignment(Pos.CENTER);

        // Create a VBox to hold the labels
        VBox vbox = new VBox(20);
        vbox.setPadding(new Insets(20));
        vbox.getChildren().addAll(
                welcomeLabel, missionLabel, missionText, aboutLabel,
                groupNameLabel, membersLabel, conceptsLabel, socialMediaBox
        );

        // Create the back button
        Button backButton = new Button(">>>");
        backButton.setOnAction(event -> sceneManager.switchTo("SettingsPage"));
        backButton.setStyle("-fx-background-color: #ff0000; -fx-text-fill: white; -fx-font-weight: bold;");

        // Create an AnchorPane to position the back button in the bottom right corner
        AnchorPane anchorPane = new AnchorPane();
        AnchorPane.setBottomAnchor(backButton, 20.0);
        AnchorPane.setRightAnchor(backButton, 20.0);
        anchorPane.getChildren().add(backButton);

        // Create the main layout
        VBox mainLayout = new VBox(20);
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.getChildren().addAll(vbox, anchorPane);
        mainLayout.setPadding(new Insets(20));
        mainLayout.setStyle("-fx-background-color: rgba(0, 0, 0, 0.7); -fx-border-color: black;"); // Semi-transparent black background
        mainLayout.setMinHeight(Screen.getPrimary().getBounds().getHeight());
        mainLayout.setMinWidth(Screen.getPrimary().getBounds().getWidth());

        // Set background image for the main layout
        Image backgroundImage = new Image("images/aboutus1.jpg");
        BackgroundImage background = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true)
        );
        mainLayout.setBackground(new Background(background));

        // Wrap the main layout in a ScrollPane
        ScrollPane scrollPane = new ScrollPane(mainLayout);
        scrollPane.setFitToWidth(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        // Create the scene with primary screen bounds
        Scene aboutUsScene = new Scene(scrollPane, Screen.getPrimary().getBounds().getWidth(), Screen.getPrimary().getBounds().getHeight());
        return aboutUsScene;
    }
}