package com.trekmate.view.trek;

import com.trekmate.manager.BookingManager;
import com.trekmate.manager.SceneManager;
import com.trekmate.model.Trek;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class MyBooking {

    private SceneManager sceneManager;

    public MyBooking(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    public Scene getScene() {
        // Title
        Label titleLabel = new Label("My Bookings");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 36));
        titleLabel.setTextFill(Color.YELLOW);

        // Booking details
        VBox bookingsBox = new VBox(10);
        bookingsBox.setAlignment(Pos.CENTER);
        bookingsBox.setPadding(new Insets(20));

        // Populate with current bookings
        for (Trek trek : BookingManager.getBookings()) {
            // Image of the fort
            String imageUrl = trek.getImageUrl().isEmpty() ? "default-image.png" : trek.getImageUrl().get(0); // Use default image if list is empty
            ImageView trekImageView = new ImageView(new Image(imageUrl));
            trekImageView.setFitHeight(150);
            trekImageView.setFitWidth(150);

            // Name of the fort
            Label trekLabel = new Label(trek.getFortName());
            trekLabel.setFont(Font.font("Arial", 24));
            trekLabel.setTextFill(Color.RED);

            // Rating stars
            HBox ratingStars = new HBox(7);
            ratingStars.setAlignment(Pos.CENTER_LEFT);

            // Array to store ToggleButtons for rating stars
            ToggleButton[] stars = new ToggleButton[5];
            for (int i = 0; i < 5; i++) {
                final int index = i; // Final variable for lambda expression
                ToggleButton star = new ToggleButton();
                star.setGraphic(new ImageView(new Image("/images/unselectstar.png"))); // Replace with your star image
                star.setPrefSize(30, 30);
                star.setOnAction(event -> {
                    // Toggle the selected state of stars
                    for (int j = 0; j <= index; j++) {
                        stars[j].setSelected(true);
                        stars[j].setGraphic(new ImageView(new Image("/images/selectstar.png"))); // Replace with your selected star image
                    }
                    for (int j = index + 1; j < 5; j++) {
                        stars[j].setSelected(false);
                        stars[j].setGraphic(new ImageView(new Image("/images/unselectstar.png"))); // Replace with your unselected star image
                    }
                });
                stars[i] = star;
                ratingStars.getChildren().add(star);
            }

            // Container for image, name, and rating
            HBox trekInfoBox = new HBox(7, trekLabel, ratingStars);
            trekInfoBox.setAlignment(Pos.CENTER_LEFT);

            // Container for image and info
            HBox trekBox = new HBox(10, trekImageView, trekInfoBox);
            trekBox.setAlignment(Pos.CENTER_LEFT);
            trekBox.setPadding(new Insets(10));
            trekBox.setStyle("-fx-border-color:black; -fx-border-width: 1px;");

            bookingsBox.getChildren().add(trekBox);
        }

        // Back button
        Button backButton = new Button("> > >");
        backButton.setStyle("-fx-background-color: aqua; -fx-text-fill: black; -fx-font-size: 16px; -fx-padding: 10px;");
        backButton.setOnAction(e -> {
            sceneManager.switchTo("HomePage"); // Navigate back to home page
        });

        // Layout
        VBox layout = new VBox(20, titleLabel, bookingsBox, backButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(10));

        // Set background image
        BackgroundImage backgroundImage = new BackgroundImage(
            new Image("/images/mybookig.jpg", 1280, 700, false, true), // Ensure the path is correct
            BackgroundRepeat.NO_REPEAT,
            BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.CENTER,
            BackgroundSize.DEFAULT
        );
        layout.setBackground(new Background(backgroundImage));

        return new Scene(layout, 2080,1080);
    }
}
