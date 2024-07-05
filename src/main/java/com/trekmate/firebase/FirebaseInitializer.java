package com.trekmate.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import java.io.FileInputStream;
import java.io.IOException;

public class FirebaseInitializer {

    private static FirebaseAuth firebaseAuth;
    private static Firestore firestore;

    public static void initializeFirebase(String serviceAccountKeyPath) throws IOException {
        if (FirebaseApp.getApps().isEmpty()) {
            FileInputStream serviceAccount = new FileInputStream(serviceAccountKeyPath);

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            FirebaseApp.initializeApp(options);
            firebaseAuth = FirebaseAuth.getInstance();
        }
    }

    public static FirebaseAuth getFirebaseAuth() {
        return firebaseAuth;
    }

    public static Firestore initializeFirestore(String serviceAccountKeyPath, String projectId) throws IOException {
        if (firestore == null) {
            FileInputStream serviceAccount = new FileInputStream(serviceAccountKeyPath);

            FirestoreOptions options = FirestoreOptions.newBuilder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setProjectId(projectId)
                    .build();

            firestore = options.getService();
        }
        return firestore;
    }
}
