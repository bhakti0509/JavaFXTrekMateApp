package com.trekmate.view.trek;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;
import com.trekmate.firebase.FirebaseTrekService;
import com.trekmate.firebase.FirebaseStorageService;
import com.trekmate.model.Trek;
import com.trekmate.view.components.NavBar;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class AddTrekPage extends Application {

    private List<File> primaryImageFile = new ArrayList<>();
    private List<File> secondaryImageFiles = new ArrayList<>();
    private File qrCodeFile;

    private TextField fortName;
    private TextField description;
    private TextField fortLocation;
    private TextField contact;
    private TextField trekDuration;
    private TextField difficultyLevel;
    private TextField openingTime;
    private TextField closingTime;
    private TextField duration;
    private TextField cost;

    private ImageView primaryImageView;
    private ImageView qrCodeImageView;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Admin Page");

        GridPane gridPane = createFormGridPane(primaryStage);
        VBox container = createContainer(gridPane);

        NavBar navBarComponent = new NavBar(primaryStage);
        VBox layout = new VBox(20);
        layout.getChildren().addAll(navBarComponent.createNavBar(), container);
        layout.setAlignment(Pos.TOP_CENTER);

        StackPane root = new StackPane();
        root.getChildren().addAll(layout);
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, Screen.getPrimary().getBounds().getWidth(), Screen.getPrimary().getBounds().getHeight());
        scene.getStylesheets().add(getClass().getResource("/trekForm.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private GridPane createFormGridPane(Stage primaryStage) {
        initializeFields();
        GridPane gridPane = setupGridPane();
        addFormFieldsToGrid(gridPane);
        setupPrimaryImageSection(primaryStage, gridPane);
        setupSecondaryImageSection(primaryStage, gridPane);
        setupQRCodeSection(primaryStage, gridPane);
        setupSaveButton(gridPane);
        setupLogoutButton(gridPane);

        return gridPane;
    }

    private void initializeFields() {
        fortName = createTextField("Enter Fort Name", "This field is necessary");
        description = createTextField("Description", "This field is necessary");
        fortLocation = createTextField("Location", "This field is necessary");
        contact = createTextField("Contact*", "This field is necessary");
        trekDuration = new TextField();
        trekDuration.setPromptText("Trek Duration");
        difficultyLevel = new TextField();
        difficultyLevel.setPromptText("Difficulty Level");
        openingTime = new TextField();
        openingTime.setPromptText("Opening Time");
        closingTime = new TextField();
        closingTime.setPromptText("Closing Time");
        duration = new TextField();
        duration.setPromptText("Trek Duration");
        cost = new TextField();
        cost.setPromptText("Total Cost Per Person");

        primaryImageView = new ImageView();
        primaryImageView.setFitWidth(100);
        primaryImageView.setFitHeight(100);

        qrCodeImageView = new ImageView();
        qrCodeImageView.setFitWidth(100);
        qrCodeImageView.setFitHeight(100);
    }

    private TextField createTextField(String promptText, String tooltipText) {
        TextField textField = new TextField();
        textField.setPromptText(promptText);
        textField.setTooltip(new Tooltip(tooltipText));
        return textField;
    }

    private GridPane setupGridPane() {
        GridPane gridPane = new GridPane();
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.setPadding(new Insets(20));
        gridPane.setAlignment(Pos.CENTER);
        return gridPane;
    }

    private void addFormFieldsToGrid(GridPane gridPane) {
        gridPane.add(new Label("Fort Name:"), 0, 0);
        gridPane.add(fortName, 1, 0);

        gridPane.add(new Label("Description:"), 0, 1);
        gridPane.add(description, 1, 1);

        gridPane.add(new Label("Location:"), 0, 2);
        gridPane.add(fortLocation, 1, 2);

        gridPane.add(new Label("Contact*:"), 0, 3);
        gridPane.add(contact, 1, 3);

        gridPane.add(new Label("Difficulty Level:"), 0, 4);
        gridPane.add(difficultyLevel, 1, 4);

        gridPane.add(new Label("Opening Time:"), 0, 5);
        gridPane.add(openingTime, 1, 5);

        gridPane.add(new Label("Closing Time:"), 0, 6);
        gridPane.add(closingTime, 1, 6);

        gridPane.add(new Label("Trek Duration:"), 0, 7);
        gridPane.add(duration, 1, 7);

        gridPane.add(new Label("Total Cost Per Person (INR):"), 0, 8);
        gridPane.add(cost, 1, 8);
    }

    private void setupPrimaryImageSection(Stage primaryStage, GridPane gridPane) {
        Button addPrimaryImageButton = createButton("Add Poster Image", primaryStage, primaryImageFile, primaryImageView);
        HBox primaryImageBox = new HBox(10, addPrimaryImageButton, primaryImageView);
        gridPane.add(new Label("Primary Image*:"), 0, 9);
        gridPane.add(primaryImageBox, 1, 9);
    }

    private void setupSecondaryImageSection(Stage primaryStage, GridPane gridPane) {
        VBox secondaryImageBox = new VBox(10);
        Button addSecondaryImagesButton = createButton("Add trek Images", primaryStage, secondaryImageFiles, secondaryImageBox);
        gridPane.add(new Label("Secondary Images:"), 0, 10);
        gridPane.add(addSecondaryImagesButton, 1, 10);
        gridPane.add(secondaryImageBox, 1, 11);
    }

    private void setupQRCodeSection(Stage primaryStage, GridPane gridPane) {
        Button addQRCodeButton = createButton("Add Payment QR Code", primaryStage, qrCodeFile, qrCodeImageView);
        HBox qrCodeBox = new HBox(10, addQRCodeButton, qrCodeImageView);
        gridPane.add(new Label("Payment QR Code*:"), 0, 12);
        gridPane.add(qrCodeBox, 1, 12);
    }

    private Button createButton(String buttonText, Stage primaryStage, List<File> imageFiles, ImageView imageView) {
        Button button = new Button(buttonText);
        button.setPadding(new Insets(10));
        button.setOnAction(e -> {
            File selectedFile = showFileChooser(primaryStage, buttonText);
            if (selectedFile != null) {
                Image image = new Image(selectedFile.toURI().toString());
                imageFiles.clear();
                imageFiles.add(selectedFile);
                imageView.setImage(image);
            }
        });
        return button;
    }

    private Button createButton(String buttonText, Stage primaryStage, List<File> imageFiles, VBox imageBox) {
        Button button = new Button(buttonText);
        button.setPadding(new Insets(10));
        button.setOnAction(e -> {
            List<File> selectedFiles = showFileChooserForMultipleFiles(primaryStage, buttonText);
            if (selectedFiles != null) {
                imageFiles.addAll(selectedFiles);
                imageBox.getChildren().clear();
                for (File file : imageFiles) {
                    ImageView imageView = new ImageView(new Image(file.toURI().toString()));
                    imageView.setFitWidth(100);
                    imageView.setFitHeight(100);
                    imageBox.getChildren().add(imageView);
                }
            }
        });
        return button;
    }

    private Button createButton(String buttonText, Stage primaryStage, File qrCodeFile, ImageView qrCodeImageView) {
        Button button = new Button(buttonText);
        button.setPadding(new Insets(10));
        button.setOnAction(e -> {
            File selectedFile = showFileChooser(primaryStage, buttonText);
            if (selectedFile != null) {
                Image image = new Image(selectedFile.toURI().toString());
                this.qrCodeFile = selectedFile;
                qrCodeImageView.setImage(image);
            }
        });
        return button;
    }

    private File showFileChooser(Stage primaryStage, String title) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(title);
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );
        return fileChooser.showOpenDialog(primaryStage);
    }

    private List<File> showFileChooserForMultipleFiles(Stage primaryStage, String title) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(title);
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );
        return fileChooser.showOpenMultipleDialog(primaryStage);
    }

    private void setupSaveButton(GridPane gridPane) {
        Button saveButton = new Button("Save");
        saveButton.setPadding(new Insets(10));
        saveButton.setOnAction(e -> saveTrekDetails());
        gridPane.add(saveButton, 0, 13);
    }

    private void setupLogoutButton(GridPane gridPane) {
        Button logoutButton = new Button("Logout");
        gridPane.add(logoutButton, 1, 13);
    }

    private VBox createContainer(GridPane gridPane) {
        VBox container = new VBox(20, gridPane);
        container.setAlignment(Pos.CENTER);
        container.setStyle(
                "-fx-background-color: rgba(0, 0, 0, 0.6);" +
                        "-fx-background-insets: 20;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.75), 15, 0, 0, 1);" +
                        "-fx-padding: 40;");
        return container;
    }

    private void saveTrekDetails() {
        if (fortName.getText().isEmpty() || description.getText().isEmpty() || fortLocation.getText().isEmpty()
                || contact.getText().isEmpty() || primaryImageFile.isEmpty() || qrCodeFile == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please fill in all necessary fields", ButtonType.OK);
            alert.showAndWait();
            return;
        }

        try {
            FirebaseStorageService storageService = new FirebaseStorageService();
            List<String> imageUrls = uploadImages(storageService);
            String qrCodeUrl = uploadQRCode(storageService);

            Trek trek = createTrek(imageUrls, qrCodeUrl);
            saveTrekToFirebase(trek);
        } catch (InterruptedException | ExecutionException | IOException ex) {
            ex.printStackTrace();
            showErrorAlert("Error saving trip details.");
        }
    }

    private List<String> uploadImages(FirebaseStorageService storageService) throws InterruptedException, ExecutionException, IOException {
        List<String> imageUrls = new ArrayList<>();
        if (!primaryImageFile.isEmpty()) {
            imageUrls.add(storageService.uploadImage(primaryImageFile.get(0)));
        }
        for (File file : secondaryImageFiles) {
            imageUrls.add(storageService.uploadImage(file));
        }
        return imageUrls;
    }

    private String uploadQRCode(FirebaseStorageService storageService) throws InterruptedException, ExecutionException, IOException {
        return qrCodeFile != null ? storageService.uploadImage(qrCodeFile) : "";
    }

    private Trek createTrek(List<String> imageUrls, String qrCodeUrl) {
        String name = fortName.getText();
        String desc = description.getText();
        String location = fortLocation.getText();
        String trekContact = contact.getText();
        String trekDur = trekDuration.getText();
        String difficulty = difficultyLevel.getText();
        String openTime = openingTime.getText();
        String closeTime = closingTime.getText();
        String trekDurationText = duration.getText();
        String trekCost = cost.getText();

        List<String> bookings = new ArrayList<>(); // Assuming bookings are empty initially

        Trek trek = new Trek(name, desc, location, trekContact, trekDur, difficulty, openTime, closeTime, trekDurationText, trekCost, imageUrls, 0, 0, bookings, qrCodeUrl);
        trek.setQrCodeUrl(qrCodeUrl);

        return trek;
    }

    private void saveTrekToFirebase(Trek trek) throws InterruptedException, ExecutionException {
        FirebaseTrekService trekService = new FirebaseTrekService();
        trekService.addTrip(trek);
        showConfirmationAlert("Trip details saved successfully.");
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.showAndWait();
    }

    private void showConfirmationAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK);
        alert.showAndWait();
    }
}
