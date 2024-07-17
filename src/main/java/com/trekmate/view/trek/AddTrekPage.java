package com.trekmate.view.trek;

import com.trekmate.firebase.FirebaseStorageService;
import com.trekmate.firebase.FirebaseTrekService;
import com.trekmate.manager.SceneManager;
import com.trekmate.model.Trek;
import com.trekmate.view.components.NavBar;
import javafx.concurrent.Task;
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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class AddTrekPage {

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
    private HBox secondaryImageBox;
    private ImageView qrCodeImageView;

    private ProgressIndicator progressIndicator;
    private Label progressLabel;

    private SceneManager sceneManager;
    private FirebaseStorageService firebaseStorageService;
    private FirebaseTrekService firebaseTrekService;

    public AddTrekPage(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
        this.firebaseStorageService = new FirebaseStorageService();
        this.firebaseTrekService = new FirebaseTrekService();
    }

    public Scene createScene() {
        initializeFields();
        GridPane gridPane = setupGridPane();
        addFormFieldsToGrid(gridPane);
        setupPrimaryImageSection(gridPane);
        setupSecondaryImageSection(gridPane);
        setupQRCodeSection(gridPane);
        setupSaveButton(gridPane);
    
        VBox container = createContainer(gridPane);

        NavBar navBarComponent = new NavBar(sceneManager);
        VBox layout = new VBox(20);
        layout.getChildren().addAll(navBarComponent.createNavBar(), container);
        layout.setAlignment(Pos.TOP_CENTER);

        StackPane root = new StackPane();
        root.getChildren().addAll(layout);
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, Screen.getPrimary().getBounds().getWidth(), Screen.getPrimary().getBounds().getHeight());
        scene.getStylesheets().add(getClass().getResource("/trekForm.css").toExternalForm());

        return scene;
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

        progressIndicator = new ProgressIndicator();
        progressIndicator.setVisible(false);

        progressLabel = new Label();
        progressLabel.setVisible(false);
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

    private void setupPrimaryImageSection(GridPane gridPane) {
        Button addPrimaryImageButton = createSelectImageButton("Add Poster Image", primaryImageFile, primaryImageView);
        HBox primaryImageBox = new HBox(10, addPrimaryImageButton, primaryImageView);
        gridPane.add(new Label("Primary Image*:"), 0, 9);
        gridPane.add(primaryImageBox, 1, 9);
    }

    private void setupSecondaryImageSection(GridPane gridPane) {
        secondaryImageBox = new HBox(10);
        Button addSecondaryImagesButton = createSelectImageButton("Add trek Images", secondaryImageFiles, secondaryImageBox);
        gridPane.add(new Label("Secondary Images:"), 0, 10);
        gridPane.add(addSecondaryImagesButton, 1, 10);
        gridPane.add(secondaryImageBox, 1, 11);
    }

    private void setupQRCodeSection(GridPane gridPane) {
        Button addQRCodeButton = createButton("Add Payment QR Code", qrCodeFile, qrCodeImageView);
        HBox qrCodeBox = new HBox(10, addQRCodeButton, qrCodeImageView);
        gridPane.add(new Label("Payment QR Code*:"), 0, 12);
        gridPane.add(qrCodeBox, 1, 12);
    }

    private Button createSelectImageButton(String buttonText, List<File> imageFiles, ImageView imageView) {
        Button button = new Button(buttonText);
        button.setPadding(new Insets(10));
        button.setOnAction(e -> {
            Stage stage = (Stage) button.getScene().getWindow();
            File selectedFile = showFileChooser(stage, buttonText);
            if (selectedFile != null) {
                Image image = new Image(selectedFile.toURI().toString());
                imageFiles.clear();
                imageFiles.add(selectedFile);
                imageView.setImage(image);
            }
        });
        return button;
    }

    private Button createSelectImageButton(String buttonText, List<File> imageFiles, HBox imageBox) {
        Button button = new Button(buttonText);
        button.setPadding(new Insets(10));
        button.setOnAction(e -> {
            Stage stage = (Stage) button.getScene().getWindow();
            List<File> selectedFiles = showFileChooserForMultipleFiles(stage, buttonText);
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

    private Button createButton(String buttonText, File qrCodeFile, ImageView qrCodeImageView) {
        Button button = new Button(buttonText);
        button.setPadding(new Insets(10));
        button.setOnAction(e -> {
            Stage stage = (Stage) button.getScene().getWindow();
            File selectedFile = showFileChooser(stage, buttonText);
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
        HBox saveButtonBox = new HBox(saveButton, progressIndicator, progressLabel);
        saveButtonBox.setSpacing(10);
        saveButtonBox.setAlignment(Pos.CENTER);
        gridPane.add(saveButtonBox, 0, 13, 2, 1);
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
        // Check required fields
        if (isInvalidInput()) {
            showAlert(Alert.AlertType.ERROR, "Please fill in all required fields.");
            return;
        }

        // Show progress indicators
        progressIndicator.setVisible(true);
        progressLabel.setText("Uploading images...");
        progressLabel.setVisible(true);

        Task<Void> uploadTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    // Upload images and get URLs
                    List<String> imageUrls = new ArrayList<>();
                    imageUrls.add(uploadImage(primaryImageFile.get(0)));
                    for (File imageFile : secondaryImageFiles) {
                        imageUrls.add(uploadImage(imageFile));
                    }
                    String qrCodeUrl = uploadImage(qrCodeFile);

                    // Add trek details to Firestore
                    Trek trek = new Trek(fortName.getText(), description.getText(), fortLocation.getText(),
                            contact.getText(),trekDuration.getText(), difficultyLevel.getText(), openingTime.getText(),
                            closingTime.getText(), duration.getText(), cost.getText(), imageUrls, 0, 0, null, qrCodeUrl);


                    firebaseTrekService.addTrek(trek);
                } catch (IOException | ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void succeeded() {
                super.succeeded();
                progressIndicator.setVisible(false);
                progressLabel.setVisible(false);
                showAlert(Alert.AlertType.INFORMATION, "Trek details saved successfully.");
                clearFormFields();
            }

            @Override
            protected void failed() {
                super.failed();
                progressIndicator.setVisible(false);
                progressLabel.setVisible(false);
                showAlert(Alert.AlertType.ERROR, "Failed to save trek details. Please try again.");
            }
        };

        new Thread(uploadTask).start();
    }

    private boolean isInvalidInput() {
        return fortName.getText().isEmpty() || description.getText().isEmpty() ||
                fortLocation.getText().isEmpty() || contact.getText().isEmpty() ||
                primaryImageFile.isEmpty() || qrCodeFile == null;
    }

    private void showAlert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearFormFields() {
        fortName.clear();
        description.clear();
        fortLocation.clear();
        contact.clear();
        difficultyLevel.clear();
        openingTime.clear();
        closingTime.clear();
        duration.clear();
        cost.clear();
        primaryImageFile.clear();
        primaryImageView.setImage(null);
        secondaryImageFiles.clear();
        secondaryImageBox.getChildren().clear();
        qrCodeFile = null;
        qrCodeImageView.setImage(null);
    }

    private String uploadImage(File imageFile) throws IOException, ExecutionException, InterruptedException {
        return firebaseStorageService.uploadImage(imageFile);
    }
}
