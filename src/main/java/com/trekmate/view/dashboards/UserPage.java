package com.trekmate.view.dashboards;

import com.trekmate.session.UserSession;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Point3D;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.Map;

public class UserPage extends Application {

    private UserSession userSession = new UserSession();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("TrekMate");

        Group root = new Group();
        Scene scene = new Scene(root, 800, 600);

        HBox navBar = createNavBar();
        GridPane grid = createGrid();

        VBox layout = new VBox(20);
        layout.getChildren().addAll(navBar, grid);
        layout.setAlignment(Pos.TOP_CENTER);

        root.getChildren().addAll(layout);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Text createAppName() {
        Text appName = new Text( "TrekMate");
        appName.setFill(Color.GREEN);
        appName.setStyle("-fx-font-size: 40px;-fx-font-weight: Bold;");
        appName.setRotationAxis(Point3D.ZERO);

        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(Color.AQUAMARINE);
        dropShadow.setOffsetX(5);
        dropShadow.setOffsetY(5);
        appName.setEffect(dropShadow);

        return appName;
    }

    private ImageView createLogoView() {
        ImageView logoView = new ImageView(new Image("images/logo.jpg")); // Placeholder image
        logoView.setFitHeight(60);
        logoView.setFitWidth(60);
        logoView.setClip(new javafx.scene.shape.Circle(30, 30, 30));
        return logoView;
    }

    private HBox createNavBar() {
        HBox navBar = new HBox(10);
        navBar.setAlignment(Pos.CENTER_LEFT);
        navBar.setPrefSize(Screen.getPrimary().getBounds().getWidth(), 20);
        navBar.setStyle("-fx-background-color: #333;");
        navBar.setPadding(new Insets(10));

        Text appName = createAppName();
        ImageView logoView = createLogoView();
        Button myBookingsButton = createNavButton("My Bookings", 200);
        Button leaderboardButton = createNavButton("Leaderboard", 200);
        

        if (userSession.isLoggedIn()) {
            Map<String, Object> userDetails = userSession.getUserDetails();
            String firstName = (String) userDetails.get("firstName");
            String lastName = (String) userDetails.get("lastName");
            Label userNameLabel = new Label(firstName + " " + lastName);
            userNameLabel.setStyle("-fx-font-size: 20px;-fx-text-fill: white;-fx-font-weight: bold;");

            ImageView profileImage = new ImageView(new Image("images/logo.jpg")); // Placeholder image
            profileImage.setFitHeight(40);
            profileImage.setFitWidth(40);
            profileImage.setClip(new javafx.scene.shape.Circle(20, 20, 20));

            Button profileButton = createNavButton("Profile", 150);
            Button settingsButton = createNavButton("Settings", 150);

            HBox userBox = new HBox(10);
            userBox.setAlignment(Pos.CENTER);
            userBox.getChildren().addAll(profileImage, userNameLabel);

            navBar.getChildren().addAll(logoView, appName, myBookingsButton, leaderboardButton, profileButton, settingsButton, userBox);
        } else {
            Button loginButton = createNavButton("Login", 150);
            Button signUpButton = createNavButton("Sign Up", 150);

            loginButton.setOnAction(event -> {
                // Handle login action
            });

            navBar.getChildren().addAll(logoView, appName, myBookingsButton, leaderboardButton, loginButton, signUpButton);
        }

        return navBar;
    }

    private Button createNavButton(String text, int width) {
        Button button = new Button(text);
        button.setPrefSize(width, 20);
        button.setStyle("-fx-font-size: 20px;-fx-background-color: #333;-fx-text-fill: white; -fx-padding: 10;-fx-font-weight: bold;");
        button.setOnMouseEntered(event -> button.setStyle("-fx-font-size: 20px;-fx-background-color: #777;-fx-text-fill: white; -fx-padding: 10;-fx-font-weight: bold;"));
        button.setOnMouseExited(event -> button.setStyle("-fx-font-size: 20px;-fx-background-color: #333;-fx-text-fill: white; -fx-padding: 10;-fx-font-weight: bold;"));
        return button;
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
