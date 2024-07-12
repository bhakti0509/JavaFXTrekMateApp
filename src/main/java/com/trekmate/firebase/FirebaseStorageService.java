package com.trekmate.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class FirebaseStorageService {

    private final Storage storage;

    public FirebaseStorageService() {
        storage = initializeFirebaseStorage();
    }

    private Storage initializeFirebaseStorage() {
        try {
            FileInputStream serviceAccount = new FileInputStream("firebase.json");
            return StorageOptions.newBuilder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build()
                    .getService();
        } catch (IOException e) {
            throw new RuntimeException("Failed to initialize Firebase Storage", e);
        }
    }

    public String uploadImage(File file) throws InterruptedException, ExecutionException, IOException {
        String bucketName = "social-media-24dc7.appspot.com";
        String blobString = "images/" + UUID.randomUUID() + "-" + file.getName();

        BlobId blobId = BlobId.of(bucketName, blobString);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
        storage.create(blobInfo, Files.readAllBytes(file.toPath()));

        return String.format("https://storage.googleapis.com/%s/%s", bucketName, blobString);
    }
}
