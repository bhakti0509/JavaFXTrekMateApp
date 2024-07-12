package com.trekmate.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import java.io.FileInputStream;
import java.io.IOException;

public class FirestoreConfig {

    private static Firestore firestore;

    public static Firestore getFirestore() {
        if (firestore == null) {
            initializeFirestore();
        }
        return firestore;
    }

    private static void initializeFirestore() {
        try {
            FileInputStream serviceAccount = new FileInputStream("firebase.json");

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            FirebaseApp.initializeApp(options);
            firestore = FirestoreClient.getFirestore();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
