package com.trekmate.view.trek;

import com.trekmate.manager.SceneManager;
import com.trekmate.model.Trek;
import com.trekmate.view.components.ImageCarousel;
import com.trekmate.view.components.NavBar;
import com.trekmate.view.settings.SettingsPage;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;

public class TrekDetailsPage {

    private static final String BACKGROUND_IMAGE_PATH = "/images/ProfPageBg.jpg";
    private final Trek trek;

    public TrekDetailsPage(Trek trek) {
        this.trek = trek;
    }

    public Scene getScene(SceneManager sceneManager) {

        // Load background image
        BackgroundImage backgroundImage = new BackgroundImage(
                new Image(SettingsPage.class.getResourceAsStream(BACKGROUND_IMAGE_PATH)),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT, new BackgroundSize(100, 100, true, true, true, true)
        );

        // Root StackPane
        StackPane root = new StackPane();
        root.setBackground(new Background(backgroundImage));

        // Title
        Label pageTitleLabel = new Label("Trek Details");
        pageTitleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 36));
        pageTitleLabel.setTextFill(Color.WHITE);
        HBox pageTitleBox = new HBox(10, pageTitleLabel);
        pageTitleBox.setAlignment(Pos.CENTER);

        // Load the images
        ImageCarousel imageCarousel = new ImageCarousel(trek.getImageUrl());

        // Fort name label
        Label fortNameLabel = new Label("Fort: ");
        fortNameLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #FFFFFF;");
        Label fortName = new Label(trek.getFortName());
        fortName.setStyle("-fx-font-size: 20px; -fx-text-fill: #FFFFFF;");

        // Fort name layout
        HBox fortNameBox = new HBox(10, fortNameLabel, fortName);

        // Description
        Label descriptionLabel = new Label("Description:");
        descriptionLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #FFFFFF;");
        Label description = new Label(trek.getDescription());
        description.setWrapText(true);
        description.setStyle("-fx-font-size: 24px; -fx-text-fill: #FFFFFF;");

        // Description layout
        VBox descriptionBox = new VBox(10, descriptionLabel, description);

        // Details
        Label locationLabel = new Label("Location:");
        locationLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #FFFFFF;");
        Label location = new Label(trek.getLocation());
        location.setStyle("-fx-font-size: 20px; -fx-text-fill: #FFFFFF;");

        Label contactLabel = new Label("Contact:");
        contactLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #FFFFFF;");
        Label contact = new Label(trek.getContact());
        contact.setStyle("-fx-font-size: 20px; -fx-text-fill: #FFFFFF;");

        Label durationLabel = new Label("Duration:");
        durationLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #FFFFFF;");
        Label duration = new Label(trek.getTrekDuration());
        duration.setStyle("-fx-font-size: 20px; -fx-text-fill: #FFFFFF;");

        Label difficultyLabel = new Label("Difficulty Level:");
        difficultyLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #FFFFFF;");
        Label difficulty = new Label(trek.getDifficultyLevel());
        difficulty.setStyle("-fx-font-size: 20px; -fx-text-fill: #FFFFFF;");

        Label openingLabel = new Label("Opening Time:");
        openingLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #FFFFFF;");
        Label opening = new Label(trek.getOpeningTime());
        opening.setStyle("-fx-font-size: 20px; -fx-text-fill: #FFFFFF;");

        Label closingLabel = new Label("Closing Time:");
        closingLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #FFFFFF;");
        Label closing = new Label(trek.getClosingTime());
        closing.setStyle("-fx-font-size: 20px; -fx-text-fill: #FFFFFF;");

        Label costLabel = new Label("Cost:");
        costLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #FFFFFF;");
        Label cost = new Label(trek.getCost());
        cost.setStyle("-fx-font-size: 20px; -fx-text-fill: #FFFFFF;");

        Label likesLabel = new Label("Likes:");
        likesLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #FFFFFF;");
        Label likes = new Label(String.valueOf(trek.getLikes()));
        likes.setStyle("-fx-font-size: 20px; -fx-text-fill: #FFFFFF;");

        Label commentsLabel = new Label("Comments:");
        commentsLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #FFFFFF;");
        Label comments = new Label(String.valueOf(trek.getComments()));
        comments.setStyle("-fx-font-size: 20px; -fx-text-fill: #FFFFFF;");

        // Book Button
        Button bookButton = new Button("Book Now");
        bookButton.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-border-color: white; -fx-border-width: 2px;");
        bookButton.setOnAction(event -> {
            // Implement booking functionality here
            // For example: open a booking form or send booking request to server
        });

        // Details layout
        VBox detailsBox = new VBox(10, locationLabel, location, contactLabel, contact, durationLabel, duration, difficultyLabel, difficulty, openingLabel, opening, closingLabel, closing, costLabel, cost, likesLabel, likes, commentsLabel, comments, bookButton);
        detailsBox.setAlignment(Pos.TOP_LEFT);
        detailsBox.setPadding(new Insets(20));

        // Title Layout
        VBox titleBox = new VBox(10);
        titleBox.setAlignment(Pos.TOP_LEFT);
        titleBox.getChildren().addAll(imageCarousel, fortNameBox, descriptionBox);

        // Main layout with image on the left and details on the right
        HBox trekDetailsLayout = new HBox(20, titleBox, detailsBox);
        trekDetailsLayout.setAlignment(Pos.TOP_CENTER);
        trekDetailsLayout.setPadding(new Insets(20));


        // Navigation bar
        NavBar navBar = new NavBar(sceneManager);

        VBox mainLayout = new VBox(10, navBar.createNavBar(), pageTitleBox, trekDetailsLayout);
        mainLayout.setStyle("-fx-background-color: rgba(0, 0, 0, 0.8);");
        

        // Root layout
        root.getChildren().add(mainLayout);

        // Create the scene and return it
        return new Scene(root, Screen.getPrimary().getBounds().getWidth(), Screen.getPrimary().getBounds().getHeight());
    }
}
