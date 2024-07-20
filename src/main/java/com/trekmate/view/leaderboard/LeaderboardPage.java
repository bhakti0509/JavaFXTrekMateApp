package com.trekmate.view.leaderboard;

import com.trekmate.manager.SceneManager;
import com.trekmate.view.components.NavBar;

import javafx.animation.FadeTransition;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.transform.Rotate;
import javafx.stage.Screen;
import javafx.util.Duration;

import java.util.Comparator;

public class LeaderboardPage {

    private static final String BACKGROUND_IMAGE_PATH = "/images/Lb.jpg";
    private static final String LOGO_IMAGE_PATH = "/images/logo.png";
    private static final String STAR_IMAGE_PATH = "/images/Ranking.jpg";

    private SceneManager sceneManager;
    private ObservableList<Member> members;

    public LeaderboardPage(SceneManager sceneManager) {
        this.sceneManager = sceneManager;

        // Initialize member list
        members = FXCollections.observableArrayList(
                new Member("Sagar Disale ", 7),
                new Member("Sarita Jagtap", 6),
                new Member("Atul Jagtap", 8),
                new Member("Bhakti Satpute", 9),
                new Member("Sunil Bandichode", 4)
        );
    }

    public Scene createScene() {
        // Load background image
        BackgroundImage backgroundImage = new BackgroundImage(
                new Image(LeaderboardPage.class.getResourceAsStream(BACKGROUND_IMAGE_PATH)),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT, new BackgroundSize(400, 100, false, false, true, true)
        );

        // Main layout VBox
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.TOP_CENTER);

        // Navigation bar
        NavBar navBar = new NavBar(sceneManager);
        HBox navBarBox = navBar.createNavBar();
        layout.getChildren().add(navBarBox);

        // Root StackPane
        StackPane root = new StackPane();
        root.setBackground(new Background(backgroundImage));

        // Main layout VBox
        GridPane grid = createLeaderboardGrid();

        // Add layout to root StackPane
        root.getChildren().add(grid);

        // Add fade transition to grid
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(9000), grid);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();

        // Add translate transition to grid
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(8000), grid);
        translateTransition.setFromX(-150);
        translateTransition.setToX(40);
        translateTransition.play();

        // Add layout to root StackPane
        root.getChildren().add(layout);

        // Create scene
        Scene leaderboardScene = new Scene(root, Screen.getPrimary().getBounds().getWidth(), Screen.getPrimary().getBounds().getHeight());
        
        // Add scene to SceneManager
        sceneManager.addScene("LeaderboardPage", leaderboardScene);

        return leaderboardScene;
    }

    private GridPane createLeaderboardGrid() {
        GridPane grid = new GridPane();
        grid.setVgap(35);
        grid.setAlignment(Pos.CENTER); // Center the grid
        grid.setPadding(new Insets(0, 50, 50, 50)); // Add padding

        // Add logo image
        Image logoImage = new Image(LOGO_IMAGE_PATH);
        ImageView logoImageView = new ImageView(logoImage);
        logoImageView.setFitWidth(100);
        logoImageView.setFitHeight(100);

        // Add half rotation transition to logo
        RotateTransition rotateTransition = new RotateTransition(Duration.millis(5000), logoImageView);
        rotateTransition.setByAngle(180);
        rotateTransition.setAxis(Rotate.Y_AXIS);
        rotateTransition.setCycleCount(RotateTransition.INDEFINITE);
        rotateTransition.setAutoReverse(true);
        rotateTransition.play();

        // Title
        Label titleLabel = new Label("LEADERBOARD");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 40));
        titleLabel.setTextFill(Color.BLACK);

        // HBox to hold the logo and title
        HBox titleBox = new HBox(100);
        titleBox.setAlignment(Pos.CENTER);
        titleBox.getChildren().addAll(logoImageView, titleLabel);

        // Add the titleBox to the grid
        grid.add(titleBox, 0, 0, 3, 1);
        GridPane.setColumnSpan(titleBox, 3);

        // Headers
        Label rankHeader = new Label("RANK");
        rankHeader.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        rankHeader.setTextFill(Color.BLACK);
        grid.add(rankHeader, 0, 1);

        Label nameHeader = new Label("              NAME");
        nameHeader.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        nameHeader.setTextFill(Color.BLACK);
        grid.add(nameHeader, 2, 1);

        Label scoreHeader = new Label("SCORE");
        scoreHeader.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        scoreHeader.setTextFill(Color.BLACK);
        grid.add(scoreHeader, 5, 1);

        // Sort members by score in descending order
        members.sort(Comparator.comparingInt(Member::getScore).reversed());

        // Add members to the grid
        for (int i = 0; i < members.size(); i++) {
            Member member = members.get(i);

            Label rankLabel = new Label(String.valueOf(i + 1));
            rankLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
            rankLabel.setTextFill(Color.BLACK);
            grid.add(rankLabel, 0, i + 2);

            ImageView starImageView = new ImageView(new Image(STAR_IMAGE_PATH));
            starImageView.setFitWidth(40);
            starImageView.setFitHeight(40);
            Label nameLabel = new Label(member.getName());
            nameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
            nameLabel.setTextFill(Color.BLACK);
            HBox nameBox = new HBox(10);
            nameBox.getChildren().addAll(starImageView, nameLabel);
            grid.add(nameBox, 2, i + 2);

            Label scoreLabel = new Label(String.valueOf(member.getScore()));
            scoreLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
            scoreLabel.setTextFill(Color.BLACK);
            grid.add(scoreLabel, 5, i + 2);
        }

        return grid;
    }

    public void setMembers(ObservableList<Member> members) {
        this.members = members;
    }

    public void increaseScore(String name, int increment) {
        for (Member member : members) {
            if (member.getName().equals(name)) {
                member.setScore(member.getScore() + increment);
                break;
            }
        }
    }

    public static class Member {
        private String name;
        private int score;

        public Member(String name, int score) {
            this.name = name;
            this.score = score;
        }

        public String getName() {
            return name;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
    }
  }
}