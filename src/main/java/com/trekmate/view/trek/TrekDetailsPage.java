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
import javafx.scene.control.ScrollPane;
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
        fortNameBox.setMaxWidth(700);

        // Description
        Label descriptionLabel = new Label("Description:");
        descriptionLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #FFFFFF;");
        Label description = new Label(trek.getDescription());
        description.setWrapText(true);
        description.setStyle("-fx-font-size: 24px; -fx-text-fill: #FFFFFF;");

        // Description layout
        HBox descriptionBox = new HBox(10, descriptionLabel, description);
        descriptionBox.setMaxWidth(700);

        // Details
        Label locationLabel = new Label("Location:");
        locationLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #FFFFFF;");
        Label location = new Label(trek.getLocation());
        location.setStyle("-fx-font-size: 20px; -fx-text-fill: #FFFFFF;");

        // Location layout
        HBox locationBox = new HBox(10, locationLabel, location);
        locationBox.setMaxWidth(700);

        Label contactLabel = new Label("Contact:");
        contactLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #FFFFFF;");
        Label contact = new Label(trek.getContact());
        contact.setStyle("-fx-font-size: 20px; -fx-text-fill: #FFFFFF;");

        // Contact layout
        HBox contactBox = new HBox(10, contactLabel, contact);
        contactBox.setMaxWidth(700);

        Label durationLabel = new Label("Duration:");
        durationLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #FFFFFF;");
        Label duration = new Label(trek.getTrekDuration());
        duration.setStyle("-fx-font-size: 20px; -fx-text-fill: #FFFFFF;");

        // Duration layout
        HBox durationBox = new HBox(10, durationLabel, duration);
        durationBox.setMaxWidth(700);

        Label difficultyLabel = new Label("Difficulty Level:");
        difficultyLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #FFFFFF;");
        Label difficulty = new Label(trek.getDifficultyLevel());
        difficulty.setStyle("-fx-font-size: 20px; -fx-text-fill: #FFFFFF;");

        // Difficulty layout
        HBox difficultyBox = new HBox(10, difficultyLabel, difficulty);
        difficultyBox.setMaxWidth(700);

        Label openingLabel = new Label("Opening Time:");
        openingLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #FFFFFF;");
        Label opening = new Label(trek.getOpeningTime());
        opening.setStyle("-fx-font-size: 20px; -fx-text-fill: #FFFFFF;");

        // Opening layout
        HBox openingBox = new HBox(10, openingLabel, opening);
        openingBox.setMaxWidth(700);

        Label closingLabel = new Label("Closing Time:");
        closingLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #FFFFFF;");
        Label closing = new Label(trek.getClosingTime());
        closing.setStyle("-fx-font-size: 20px; -fx-text-fill: #FFFFFF;");

        // Closing layout
        HBox closingBox = new HBox(10, closingLabel, closing);
        closingBox.setMaxWidth(700);

        Label costLabel = new Label("Cost:");
        costLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #FFFFFF;");
        Label cost = new Label(trek.getCost());
        cost.setStyle("-fx-font-size: 20px; -fx-text-fill: #FFFFFF;");

        // Cost layout
        HBox costBox = new HBox(10, costLabel, cost);
        costBox.setMaxWidth(700);

        // Book Button
        Button bookButton = new Button("Book Now");
        bookButton.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-border-color: white; -fx-border-width: 2px;");
        bookButton.setOnAction(event -> {
            // Implement booking functionality here
        });

        // Book button layout
        HBox bookButtonBox = new HBox(10, bookButton);
        bookButtonBox.setMaxWidth(700);
        bookButton.setAlignment(Pos.CENTER);


        // Navigation bar
        NavBar navBar = new NavBar(sceneManager);

        VBox mainLayout = new VBox(10, navBar.createNavBar(), pageTitleBox, imageCarousel, fortNameBox, descriptionBox, locationBox, contactBox, durationBox, difficultyBox, openingBox, closingBox, costBox, bookButtonBox);
        mainLayout.setStyle("-fx-background-color: rgba(0, 0, 0, 0.8);");
        mainLayout.setAlignment(Pos.TOP_CENTER);
        mainLayout.setPadding(new Insets(0, 0, 70, 0));


        ScrollPane scrollPane = new ScrollPane(mainLayout);
        scrollPane.setBackground(new Background(backgroundImage));

        // Create the scene and return it
        return new Scene(scrollPane, Screen.getPrimary().getBounds().getWidth(), Screen.getPrimary().getBounds().getHeight());
    }
}
