/*package com.trekmate.firebaseConfig;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.google.cloud.firestore.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class Dataservice {
    
    private static Firestore db;

    static {
        try{
            initializeFirebase();
        }catch(IOException e){
            e.printStackTrace();
        }

    }
    private static void initializeFirebase() throws IOException{
        FileInputStream serviceAccount =
        new FileInputStream("\\src\\main\\resources\\javafxtrekmatefirebase.json");
        
        FirebaseOptions options = new FirebaseOptions.Builder()
          .setCredentials(GoogleCredentials.fromStream(serviceAccount))
          .build();
        
        FirebaseApp.initializeApp(options);
        db = FirestoreClient.getFirestore();
    }

    public void addData(String collection, String document, Map<String, Object>data)
throws ExecutionException,InterruptedException{

        DocumentReference docRef = db.collection(collection).document(document);
    }
}
*/