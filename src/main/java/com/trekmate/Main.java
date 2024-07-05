package com.trekmate;

import javafx.application.Application;
import com.trekmate.firebase.FirebaseInitializer;
import com.trekmate.view.auth.LoginPage;

public class Main {
    public static void main(String[] args){

        try {
            FirebaseInitializer.initializeFirebase("firebase.json");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Application.launch(LoginPage.class,args);
    }
  
}