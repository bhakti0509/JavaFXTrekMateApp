package com.trekmate.view.dashboards;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class AdminPage extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Admin Page");

        // Creating the text fields
        TextField fortName = new TextField();
        fortName.setPromptText("Enter Fort Name");

        TextField description = new TextField();
        description.setPromptText("Description");
        
        TextField fortLocation = new TextField();
        fortLocation.setPromptText("Location");
        
        TextField owner = new TextField();
        owner.setPromptText("Owner");

        TextField trekDue = new TextField();
        trekDue.setPromptText("Trek Duration");
        
        TextField difficultyLevel = new TextField();
        difficultyLevel.setPromptText("Difficulty Level");
        
        TextField openingTime = new TextField();
        openingTime.setPromptText("Opening Time");

        TextField closingTime = new TextField();
        closingTime.setPromptText("Closing Time");

        TextField duration = new TextField();
        duration.setPromptText("Trek Duration");

        TextField cost = new TextField();
        cost.setPromptText("Total Cost Per Person");

        // Creating the image view and button for adding image
        ImageView fortImageView = new ImageView();
        fortImageView.setFitWidth(500);
        fortImageView.setFitHeight(500);

        Button addImageButton = new Button("Add Image");
        addImageButton.setPadding(new Insets(10));
        addImageButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            File selectedFile = fileChooser.showOpenDialog(primaryStage);
            if (selectedFile != null) {
                Image fortImage = new Image(selectedFile.toURI().toString());
                fortImageView.setImage(fortImage);
            }
        });

        // Creating the save button
        Button saveButton = new Button("Save");
        saveButton.setPadding(new Insets(10));
        saveButton.setOnAction(e -> {
            // Logic to save the data
            System.out.println("Fort Name: " + fortName.getText());
            System.out.println("Description: " + description.getText());
            System.out.println("Location: " + fortLocation.getText());
            System.out.println("Owner: " + owner.getText());
            System.out.println("Difficulty Level: " + difficultyLevel.getText());
            System.out.println("Opening Time: " + openingTime.getText());
            System.out.println("Closing Time: "+closingTime.getText());
            System.out.println("Trek Duration: " + duration.getText());
            System.out.println("Total Cost Per Person(INR): " + cost.getText());
        
            // Additional logic to save the image if needed
        });

        // Creating the logout button
        Button logoutButton = new Button("Logout");

        // Arranging all elements in a grid pane
        GridPane gridPane = new GridPane();
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.setPadding(new Insets(20, 20, 20, 20));
        gridPane.setAlignment(Pos.CENTER);
        
        gridPane.add(new Label("Fort Name:"), 0, 0);
        gridPane.add(fortName, 1, 0);
        
        gridPane.add(new Label("Description:"), 0, 1);
        gridPane.add(description, 1, 1);
        
        gridPane.add(new Label("Location:"), 0, 2);
        gridPane.add(fortLocation, 1, 2);
        
        gridPane.add(new Label("Owner:"), 0, 3);
        gridPane.add(owner, 1, 3);
        
        gridPane.add(new Label("Difficulty Level:"), 0, 4);
        gridPane.add(difficultyLevel, 1, 4);
        
        gridPane.add(new Label("Opening Time:"), 0, 5);
        gridPane.add(openingTime, 1, 5);

        gridPane.add(new Label("Closing Time:"), 0, 6);
        gridPane.add(closingTime, 1, 6);

        gridPane.add(new Label("Trek Duration:"), 0,7 );
        gridPane.add(duration, 1, 7);

        gridPane.add(new Label("Total Cost Per Person(INR):"), 0, 8);
        gridPane.add(cost, 1, 8);

        gridPane.add(addImageButton, 0, 9);
        gridPane.add(fortImageView, 1, 9);
        
        gridPane.add(saveButton, 0, 10);
        gridPane.add(logoutButton, 1, 10);

        Image backgroundImage = new Image("images/AdminPageBGImage.jpg");

        // Create a BackgroundImage
        BackgroundImage background = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(400, 100, false, false, true, true)
        );
        gridPane.setBackground(new Background(background));

        // Creating and setting the scene
        Scene scene = new Scene(gridPane, primaryStage.getWidth(), primaryStage.getHeight() );
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}

