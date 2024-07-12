package com.trekmate.view.homePage;

import com.trekmate.view.components.NavBar;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class HomePage extends Application {

    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
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
    }

    private GridPane createGrid() {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(300, 30, 10, 80));
        grid.setVgap(50);
        grid.setHgap(100);

        addTrekTile(grid, "Rajgad Fort", "images/RajgadFort.jpg", 0, 0);
        addTrekTile(grid, "Sinhgad Fort", "images/SinhgadFort.jpg", 0, 1);
        addTrekTile(grid, "Purandar Fort", "images/PurandarFort.jpg", 0, 2);

        return grid;
    }

    private void addTrekTile(GridPane grid, String trekTitle, String imagePath, int row, int col) {
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));

        Image image = new Image(imagePath);
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(500);
        imageView.setFitHeight(500);

        Label titleLabel = new Label(trekTitle);
        titleLabel.setStyle("-fx-font-size: 36px; -fx-font-weight: bold;");

        vbox.getChildren().addAll(imageView, titleLabel);
        grid.add(vbox, col, row);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
