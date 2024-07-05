package com.trekmate.view.dashboards;

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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class UserPage extends Application{

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("TrekMate");
        primaryStage.setFullScreen(true);

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(300, 30, 10, 80));
        grid.setVgap(50);
        grid.setHgap(100);

        Text appName = new Text(180, 110, "TrekMate");
        appName.setFill(Color.GREEN);
        appName.setStyle("-fx-font-size: 60px;-fx-font-weight: Bold;");
        appName.setRotationAxis(Point3D.ZERO);

        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(Color.AQUAMARINE);
        dropShadow.setOffsetX(5);
        dropShadow.setOffsetY(5);
        appName.setEffect(dropShadow);
        
        Button myBookingsButton= new Button("My Bookings");
        
        Button loginButton = new Button("Login");

        Button signUpButton = new Button("Sign Up");

        Button profileButton = new Button("Profile");

        Button settingsButton = new Button("Settings");

        Button leaderboardButton = new Button("Leaderboard");


        myBookingsButton.setPrefSize(200, 20);
        myBookingsButton.setStyle("-fx-font-size: 20px;-fx-background-color: White; -fx-padding: 10;-fx-font-weight: bold;");

        loginButton.setPrefSize(150, 20);
        loginButton.setStyle("-fx-font-size: 20px;-fx-background-color: White; -fx-padding: 10;-fx-font-weight: bold;");

        signUpButton.setPrefSize(150, 20);
        signUpButton.setStyle("-fx-font-size: 20px;-fx-background-color: White; -fx-padding: 10;-fx-font-weight: bold;");

        profileButton.setPrefSize(150, 20);
        profileButton.setStyle("-fx-font-size: 20px;-fx-background-color: White; -fx-padding: 10;-fx-font-weight: bold;");

        settingsButton.setPrefSize(150, 20);
        settingsButton.setStyle("-fx-font-size: 20px;-fx-background-color: White; -fx-padding: 10;-fx-font-weight: bold;");

        leaderboardButton.setPrefSize(200, 20);
        leaderboardButton.setStyle("-fx-font-size: 20px;-fx-background-color: White; -fx-padding: 10;-fx-font-weight: bold;");

        loginButton.setOnAction(event ->{

                

        });
       
        Image logo = new Image("images/logo.jpg");
        ImageView logoView = new ImageView(logo);
        logoView.setFitHeight(150);
        logoView.setFitWidth(150);
        logoView.setLayoutX(20);
        logoView.setLayoutY(20);
        logoView.setPreserveRatio(true);

        HBox navBar = new HBox(10); // 10 is the spacing between buttons
        navBar.getChildren().addAll(myBookingsButton,leaderboardButton,loginButton,signUpButton,profileButton,settingsButton);
        navBar.setPadding(new Insets(10));
        navBar.setAlignment(Pos.CENTER);
        navBar.setPrefSize(2700, 20);

        VBox layout = new VBox(10);
        layout.getChildren().addAll(navBar);
        layout.setPadding(new Insets(30));
        layout.setAlignment(Pos.TOP_CENTER);

        // Create course tiles
        addTrekTile(grid, "Rajgad Fort","images/RajgadFort.jpg",  0, 0);
        addTrekTile(grid, "Sinhgad Fort", "images/SinhgadFort.jpg", 0, 1);
        addTrekTile(grid, "Purandar Fort", "images/PurandarFort.jpg", 0, 2);


       
        Group group = new Group(logoView,appName,layout,grid);

        Scene scene = new Scene(group,800,600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void addTrekTile(GridPane grid, String trekTitle, String imagePath, int row, int col) {
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));

        // Trek image
        Image image = new Image(imagePath);
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(500);
        imageView.setFitHeight(500);

        // Trek title
        Label titleLabel = new Label(trekTitle);
        titleLabel.setStyle("-fx-font-size: 36px; -fx-font-weight: bold;");
        
        vbox.getChildren().addAll(imageView, titleLabel);
        grid.add(vbox, col, row);
    }

}

