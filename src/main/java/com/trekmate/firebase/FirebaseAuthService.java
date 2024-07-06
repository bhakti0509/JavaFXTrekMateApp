package com.trekmate.firebase;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import org.mindrot.jbcrypt.BCrypt;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class FirebaseAuthService {

    private Firestore firestore;

    public FirebaseAuthService() {
        this.firestore = FirestoreConfig.getFirestore();
    }

    public void createUser(String email, String password, String username, String firstName, String lastName, String role) throws InterruptedException, ExecutionException {
        try {
            // Check if the user already exists
            ApiFuture<QuerySnapshot> query = firestore.collection("users").whereEqualTo("email", email).get();
            List<QueryDocumentSnapshot> documents = query.get().getDocuments();
            if (!documents.isEmpty()) {
                throw new IllegalArgumentException("User with this email already exists.");
            }

            // Hash the password
            String hashedPassword = hashPassword(password);

            // Save user data in Firestore
            DocumentReference docRef = firestore.collection("users").document();
            Map<String, Object> data = new HashMap<>();
            data.put("email", email);
            data.put("password", hashedPassword);
            data.put("username", username);
            data.put("firstName", firstName);
            data.put("lastName", lastName);
            data.put("role", role);
            data.put("createdOn", Instant.now().toString());
            data.put("updatedOn", Instant.now().toString());
            data.put("lastLoggedIn", Instant.now().toString());

            ApiFuture<WriteResult> result = docRef.set(data);
            System.out.println("User created at: " + result.get().getUpdateTime());
        } catch (ExecutionException | InterruptedException e) {
            throw new ExecutionException("Error creating user: " + e.getMessage(), e);
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error: " + e.getMessage(), e);
        }
    }

    public Map<String, Object> signIn(String email, String password) throws InterruptedException, ExecutionException {
        try {
            // Retrieve the user with the given email
            System.out.println("Signing in with email: " + email);
            ApiFuture<QuerySnapshot> query = firestore.collection("users").whereEqualTo("email", email).get();
            List<QueryDocumentSnapshot> documents = query.get().getDocuments();
            if (documents.isEmpty()) {
                throw new IllegalArgumentException("Invalid email or password.");
            }

            // Verify the password
            DocumentSnapshot userDoc = documents.get(0);
            String storedHashedPassword = userDoc.getString("password");
            if (!checkPassword(password, storedHashedPassword)) {
                throw new IllegalArgumentException("Invalid email or password.");
            }

            // Update the `updatedOn` and `lastLoggedIn` fields
            DocumentReference docRef = userDoc.getReference();
            Map<String, Object> updates = new HashMap<>();
            updates.put("updatedOn", Instant.now().toString());
            updates.put("lastLoggedIn", Instant.now().toString());
            ApiFuture<WriteResult> updateResult = docRef.update(updates);
            System.out.println("User updated at: " + updateResult.get().getUpdateTime());

            // Return user data
            System.out.println("User signed in: " + userDoc.getData());
            return userDoc.getData();
        } catch (ExecutionException | InterruptedException e) {
            throw new ExecutionException("Error signing in: " + e.getMessage(), e);
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error: " + e.getMessage(), e);
        }
    }

    private String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    private boolean checkPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}
