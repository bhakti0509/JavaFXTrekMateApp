package com.trekmate.view.homePage;

import com.trekmate.firebase.FirebaseTrekService;
import com.trekmate.model.Trek;
import com.trekmate.view.components.NavBar;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class HomePage extends Application {

    private Stage primaryStage;
    private FirebaseTrekService trekService;
    private GridPane grid;
    private int trekCount = 0;
    private static final int PAGE_SIZE = 10;
    private Button loadMoreButton;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.trekService = new FirebaseTrekService();

        primaryStage.setTitle("TrekMate");

        Group root = new Group();
        Scene scene = new Scene(root, Screen.getPrimary().getBounds().getWidth(), Screen.getPrimary().getBounds().getHeight());

        NavBar navBarComponent = new NavBar(this.primaryStage);
        VBox layout = new VBox(20);
        layout.getChildren().addAll(navBarComponent.createNavBar(), createGrid());
        layout.setAlignment(Pos.TOP_CENTER);

        root.getChildren().addAll(layout);

        primaryStage.setScene(scene);
        primaryStage.show();

        loadMoreTreks();
    }

    private GridPane createGrid() {
        grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setVgap(20);
        grid.setHgap(20);
        grid.setAlignment(Pos.TOP_CENTER);
        
        loadMoreButton = new Button("Load More");
        loadMoreButton.setOnAction(e -> loadMoreTreks());

        return grid;
    }

    private void loadMoreTreks() {
        try {
            List<Trek> treks = trekService.getTreks(PAGE_SIZE, trekCount);
            if (!treks.isEmpty()) {
                addTrekTiles(treks);
            } else {
                loadMoreButton.setDisable(true);
            }
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void addTrekTiles(List<Trek> treks) {
        for (Trek trek : treks) {
            addTrekTile(trek, trekCount % 3, trekCount / 3);
            trekCount++;
        }
        grid.add(loadMoreButton, 0, (trekCount + PAGE_SIZE) / 3);
    }

    private void addTrekTile(Trek trek, int row, int col) {
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));
        vbox.setStyle("-fx-background-color: #fff; -fx-border-color: #ddd; -fx-border-radius: 5px; -fx-background-radius: 5px;");

        Image image = new Image(trek.getImageUrl().get(0)); // Assuming the first URL is the primary image
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(300);
        imageView.setFitHeight(200);
        imageView.setPreserveRatio(true);

        Label nameLabel = new Label(trek.getFortName());
        nameLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #333;");

        Label difficultyLabel = new Label("Difficulty: " + trek.getDifficultyLevel());
        difficultyLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: #666;");

        Label locationLabel = new Label("Location: " + trek.getLocation());
        locationLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: #666;");

        vbox.getChildren().addAll(imageView, nameLabel, difficultyLabel, locationLabel);
        grid.add(vbox, row, col);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
