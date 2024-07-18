package com.trekmate.view.components;

import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.util.List;

public class ImageCarousel extends StackPane {

    private final List<String> imageUrls;
    private int currentIndex = 0;
    private final ImageView imageView;

    public ImageCarousel(List<String> imageUrls) {
        this.imageUrls = imageUrls;
        this.imageView = new ImageView();
        this.imageView.setPreserveRatio(true);
        this.imageView.setFitWidth(700);
        this.imageView.setFitHeight(550);

        updateImage();

        setMinSize(700, 550);
        setMaxSize(700, 550);

        Button prevButton = new Button("<");
        prevButton.setOnAction(event -> showPreviousImage());

        Button nextButton = new Button(">");
        nextButton.setOnAction(event -> showNextImage());

        HBox buttonContainer = new HBox(10, prevButton, nextButton);
        buttonContainer.setAlignment(Pos.BOTTOM_CENTER);

        StackPane.setAlignment(imageView, Pos.CENTER);
        StackPane.setAlignment(buttonContainer, Pos.BOTTOM_CENTER);
        StackPane.setMargin(buttonContainer, new Insets(0, 0, 80, 0));
    

        getChildren().addAll(imageView, buttonContainer);
    }

    private void updateImage() {
        if (!imageUrls.isEmpty()) {
            imageView.setImage(new Image(imageUrls.get(currentIndex)));
        }
    }

    private void showPreviousImage() {
        currentIndex = (currentIndex - 1 + imageUrls.size()) % imageUrls.size();
        animateTransition(imageView);
        updateImage();
    }

    private void showNextImage() {
        currentIndex = (currentIndex + 1) % imageUrls.size();
        animateTransition(imageView);
        updateImage();
    }

    private void animateTransition(Node node) {
        TranslateTransition tt = new TranslateTransition(Duration.millis(500), node);
        tt.setFromX(-50);
        tt.setToX(0);
        tt.setCycleCount(1);
        tt.setAutoReverse(false);
        tt.play();
    }
}
