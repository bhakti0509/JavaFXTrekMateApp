package com.trekmate.view.homePage;

import com.trekmate.firebase.FirebaseTrekService;
import com.trekmate.manager.SceneManager;
import com.trekmate.model.Trek;
import com.trekmate.view.components.NavBar;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class HomePage {

    private FirebaseTrekService trekService;
    private GridPane grid;
    private int trekCount = 0;
    private static final int PAGE_SIZE = 6;
    private Button loadMoreButton;
    private SceneManager sceneManager;
    private ProgressIndicator loadingIndicator;
    private Label loadingLabel;

    public HomePage(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
        this.trekService = new FirebaseTrekService();
        this.loadingIndicator = new ProgressIndicator();
        this.loadingLabel = new Label("Loading Treks...");
    }

    public Scene getScene(Stage primaryStage) {
        // Load the background image
        Image backgroundImage = new Image("images/Hiking.jpg");
        ImageView backgroundImageView = new ImageView(backgroundImage);
        backgroundImageView.setFitWidth(Screen.getPrimary().getBounds().getWidth());
        backgroundImageView.setFitHeight(Screen.getPrimary().getBounds().getHeight());
        backgroundImageView.setPreserveRatio(true);

        // Create the StackPane and set its background
        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(backgroundImageView);

        Rectangle overlay = new Rectangle(Screen.getPrimary().getBounds().getWidth(), Screen.getPrimary().getBounds().getHeight() / 1.6);
        overlay.setFill(javafx.scene.paint.Color.rgb(0,0,0,0.4)); // Adjust the opacity as needed
        stackPane.getChildren().add(overlay);

        // Create the VBox layout
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.TOP_CENTER);
        layout.setStyle("-fx-background-color: rgba(255, 255, 255, 0.8); -fx-background-radius: 10;");
        layout.setMinSize(800, 600);

        NavBar navBarComponent = new NavBar(sceneManager);
        layout.getChildren().add(navBarComponent.createNavBar());

        // Add the tagline label
        Label taglineLabel = new Label("Explore the Best Treks with TrekMate");
        taglineLabel.setStyle("-fx-font-size: 40px; -fx-font-weight: bold; -fx-text-fill: #333;");
        layout.getChildren().add(taglineLabel);

        // Add the "Upcoming Treks" label
        Label upcomingTreksLabel = new Label("Upcoming Treks");
        upcomingTreksLabel.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #333;");
        layout.getChildren().add(upcomingTreksLabel);

        // Add the GridPane for the treks
        layout.getChildren().add(createGrid());

        // Add loading indicator and label to layout
        HBox loadingBox = new HBox(10);
        loadingBox.setAlignment(Pos.CENTER);
        loadingBox.getChildren().addAll(loadingIndicator, loadingLabel);
        layout.getChildren().add(loadingBox);
        loadingIndicator.setVisible(false);
        loadingLabel.setVisible(false);

        // Add layout to the StackPane
        stackPane.getChildren().add(layout);

        // Set the scene
        Scene scene = new Scene(stackPane, Screen.getPrimary().getBounds().getWidth(), Screen.getPrimary().getBounds().getHeight());

        // Add a listener to the scene property of the stage
        primaryStage.sceneProperty().addListener((observable, oldScene, newScene) -> {
            if (newScene == scene) {
                loadTreks(true);
            }
        });

        return scene;
    }

    private GridPane createGrid() {
        grid = new GridPane();
        grid.setPadding(new Insets(100, 20, 20, 20));
        grid.setVgap(20);
        grid.setHgap(20);
        grid.setAlignment(Pos.TOP_CENTER);

        loadMoreButton = new Button("Load More");
        loadMoreButton.setStyle("-fx-font-size: 16px; -fx-background-color: #0066cc; -fx-text-fill: white; -fx-background-radius: 5;");
        loadMoreButton.setOnAction(e -> loadTreks(false));

        return grid;
    }

    private void loadTreks(boolean clearExisting) {
        // Show loading indicator and label
        Platform.runLater(() -> {
            loadingIndicator.setVisible(true);
            loadingLabel.setVisible(true);
        });

        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws ExecutionException, InterruptedException {
                List<Trek> treks = trekService.getTreks(PAGE_SIZE, trekCount);

                Platform.runLater(() -> {
                    try {
                        if (clearExisting) {
                            grid.getChildren().clear();
                            trekCount = 0;
                        }

                        if (!treks.isEmpty()) {
                            addTrekTiles(treks);
                        } else {
                            loadMoreButton.setDisable(true);
                        }
                    } catch (Exception e) {
                        System.out.println("Error loading treks: " + e.getMessage());
                    }
                });

                return null;
            }

            @Override
            protected void succeeded() {
                super.succeeded();
                // Hide the loading indicator and label
                Platform.runLater(() -> {
                    loadingIndicator.setVisible(false);
                    loadingLabel.setVisible(false);
                });
            }

            @Override
            protected void failed() {
                super.failed();
                // Hide the loading indicator and label
                Platform.runLater(() -> {
                    loadingIndicator.setVisible(false);
                    loadingLabel.setVisible(false);
                });
                showAlert("Loading Failed", "Unable to load treks. Please try again later.");
            }
        };

        // Run the task on a new thread
        new Thread(task).start();
    }

    private void addTrekTiles(List<Trek> treks) {
        System.out.println("Adding trek tiles");
        for (Trek trek : treks) {
            addTrekTile(trek, trekCount % 3, trekCount / 3);
            trekCount++;
        }
        grid.add(loadMoreButton, 0, (trekCount + PAGE_SIZE) / 3);
    }

    private void addTrekTile(Trek trek, int row, int col) {
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));
        vbox.setStyle("-fx-background-color: #fff; -fx-border-color: #ddd; -fx-border-radius: 10px; -fx-background-radius: 10px;");

        Image image = new Image(trek.getImageUrl().get(0)); // Assuming the first URL is the primary image
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(400);
        imageView.setFitHeight(250);
        imageView.setPreserveRatio(true);
        imageView.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.5), 10, 0, 0, 10);");

        Label nameLabel = new Label(trek.getFortName());
        nameLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #333;");

        Label difficultyLabel = new Label("Difficulty: " + trek.getDifficultyLevel());
        difficultyLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: #666;");

        Label locationLabel = new Label("Location: " + trek.getLocation());
        locationLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: #666;");

        Button viewButton = new Button("View");
        viewButton.setStyle("-fx-background-color: #007bff; -fx-text-fill: white;");
        viewButton.setOnAction(event -> sceneManager.loadTrekDetails(trek));

        vbox.getChildren().addAll(imageView, nameLabel, difficultyLabel, locationLabel, viewButton);
        grid.add(vbox, row, col);
    }

    private void showAlert(String title, String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }
}