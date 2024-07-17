package com.trekmate.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Acl;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import io.github.cdimascio.dotenv.Dotenv;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class FirebaseStorageService {

    private final Storage storage;
    private final String bucketName;

    public FirebaseStorageService() {
        storage = initializeFirebaseStorage();
        bucketName = Dotenv.load().get("FIREBASE_BUCKET_NAME");
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
        String blobString = "images/" + UUID.randomUUID();

        BlobId blobId = BlobId.of(bucketName, blobString);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
            .setContentType("image/" + file.getName().substring(file.getName().lastIndexOf(".") + 1))
            .setAcl(new ArrayList<>(Arrays.asList(Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER))))
            .build();
        storage.create(blobInfo, Files.readAllBytes(file.toPath()));

        return String.format("https://storage.googleapis.com/%s/%s", bucketName, blobString);
    }
}
