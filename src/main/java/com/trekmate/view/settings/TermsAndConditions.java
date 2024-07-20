package com.trekmate.view.settings;

import com.trekmate.manager.SceneManager;
import com.trekmate.model.Trek;
import com.trekmate.view.trek.TrekDetailsPage;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.web.WebView;
import javafx.scene.input.MouseEvent;

public class TermsAndConditions {

    private SceneManager sceneManager;

    public TermsAndConditions(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    public Scene createScene() {
        // Create a WebView to display the terms and conditions
        WebView termsWebView = new WebView();
        termsWebView.getEngine().loadContent(getTermsAndConditionsHtml());
        termsWebView.setStyle("-fx-background-color: rgba(255, 255, 255, 0.8);");
    public void loadTrekDetails(Trek trek) {
        TrekDetailsPage trekDetailsPage = new TrekDetailsPage(trek);
        Scene trekDetailsScene = trekDetailsPage.getScene(this);
        this.addScene("TrekDetailsPage", trekDetailsScene);
        switchTo("TrekDetailsPage");
    }
        // Create a VBox for the content
        VBox contentBox = new VBox(10, termsWebView, createBackButton());
        contentBox.setStyle("-fx-background-color: #f0f0f0; -fx-padding: 20px;");

        // Create the Scene
        Scene scene = new Scene(contentBox, 2080, 1080);
        return scene;
    }

    private String getTermsAndConditionsHtml() {
        return "<html><body style='font-size:14px; line-height:1.6;'>"
                + "<h2>Terms and Conditions</h2>"
                + "<p><strong>Last updated:</strong> [17/7/2024]</p>"
                + "<p>Welcome to <strong>TrekMate</strong>!</p>"
                + "<p>These terms and conditions outline the rules and regulations for the use of <strong>TrekMate</strong>, "
                + "located at [Website URL].</p>"
                + "<p>By accessing this app, we assume you accept these terms and conditions. "
                + "Do not continue to use <strong>TrekMate</strong> if you do not agree to take all of the terms and conditions stated on this page.</p>"
                + "<h3>1. Introduction</h3>"
                + "<p><strong>1.1</strong> These terms and conditions govern your use of <strong>TrekMate</strong>.</p>"
                + "<p><strong>1.2</strong> By using this app, you agree to these terms and conditions in full. "
                + "If you disagree with these terms and conditions or any part of these terms and conditions, you must not use this app.</p>"
                + "<h3>2. Intellectual Property Rights</h3>"
                + "<p><strong>2.1</strong> Unless otherwise stated, <strong>TrekMate</strong> and/or its licensors own the intellectual property rights for all material on <strong>TrekMate</strong>. All intellectual property rights are reserved.</p>"
                + "<p><strong>2.2</strong> You may access this from <strong>TrekMate</strong> for your own personal use subject to restrictions set in these terms and conditions.</p>"
                + "<h3>3. User Responsibilities</h3>"
                + "<p><strong>3.1</strong> You must not:</p>"
                + "<ul>"
                + "<li>Republish material from <strong>TrekMate</strong>.</li>"
                + "<li>Sell, rent, or sub-license material from <strong>TrekMate</strong>.</li>"
                + "<li>Reproduce, duplicate, or copy material from <strong>TrekMate</strong>.</li>"
                + "<li>Redistribute content from <strong>TrekMate</strong>.</li>"
                + "</ul>"
                + "<p><strong>3.2</strong> You must not use <strong>TrekMate</strong> in any way that causes, or may cause, damage to the app or impairment of the availability or accessibility of <strong>TrekMate</strong>.</p>"
                + "<h3>4. Privacy Policy</h3>"
                + "<p><strong>4.1</strong> Your privacy is important to us. Please read our <a href='#'>Privacy Policy</a> for details on how we collect, use, and protect your information.</p>"
                + "<h3>5. Limitation of Liability</h3>"
                + "<p><strong>5.1</strong> <strong>TrekMate</strong> will not be liable to you in relation to the contents of, or use of, or otherwise in connection with, this app:</p>"
                + "<ul>"
                + "<li>for any indirect, special, or consequential loss; or</li>"
                + "<li>for any business losses, loss of revenue, income, profits, or anticipated savings, loss of contracts or business relationships, loss of reputation or goodwill, or loss or corruption of information or data.</li>"
                + "</ul>"
                + "<h3>6. Changes to Terms and Conditions</h3>"
                + "<p><strong>6.1</strong> We may revise these terms and conditions from time to time. Revised terms and conditions will apply to the use of this app from the date of the publication of the revised terms and conditions on this app.</p>"
                + "</body></html>";
    }

    private Button createBackButton() {
        Button backButton = new Button("> > >");
        backButton.setStyle("-fx-background-color: #CCCCCC;"); // Set initial color
        backButton.setOnMousePressed(e -> backButton.setStyle("-fx-background-color: #AAAAAA;")); // Darker color when
                                                                                                  // pressed
        backButton.setOnMouseReleased(e -> backButton.setStyle("-fx-background-color: #CCCCCC;")); // Restore color when
                                                                                                   // released
        backButton.setOnAction(e -> handleBack());
        return backButton;
    }

    private void handleBack() {
        System.out.println("Back button clicked. Navigating back to SettingsPage...");
        sceneManager.switchTo("SettingsPage");
    }
}
