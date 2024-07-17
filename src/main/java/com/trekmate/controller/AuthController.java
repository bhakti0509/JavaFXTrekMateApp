package com.trekmate.controller;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.trekmate.firebase.FirestoreConfig;
import com.trekmate.model.User;
import org.mindrot.jbcrypt.BCrypt;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class AuthController {

    private final Firestore firestore;

    public AuthController() {
        this.firestore = FirestoreConfig.getFirestore();
    }

    public void signUp(User user) throws InterruptedException, ExecutionException {
        try {
            // Check if the user already exists
            ApiFuture<QuerySnapshot> query = firestore.collection("users").whereEqualTo("email", user.getEmail()).get();
            List<QueryDocumentSnapshot> documents = query.get().getDocuments();
            if (!documents.isEmpty()) {
                throw new IllegalArgumentException("User with this email already exists.");
            }

            // Hash the password
            String hashedPassword = hashPassword(user.getPassword());

            // Save user data in Firestore
            DocumentReference docRef = firestore.collection("users").document();
            user.setPassword(hashedPassword); // Set hashed password
            ApiFuture<WriteResult> result = docRef.set(user);
            System.out.println("User created at: " + result.get().getUpdateTime());
        } catch (ExecutionException | InterruptedException e) {
            throw new ExecutionException("Error creating user: " + e.getMessage(), e);
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error: " + e.getMessage(), e);
        }
    }

    public User signIn(String email, String password) throws InterruptedException, ExecutionException {
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
            User user = userDoc.toObject(User.class);
			if (user == null) {
				throw new IllegalArgumentException("Invalid email or password.");
				
			}
            if (!checkPassword(password, user.getPassword())) {
                throw new IllegalArgumentException("Invalid email or password.");
            }

            // Update the `updatedOn` and `lastLoggedIn` fields
            DocumentReference docRef = userDoc.getReference();
            user.setUpdatedOn(Instant.now().toString());
            user.setLastLoggedIn(Instant.now().toString());
            ApiFuture<WriteResult> updateResult = docRef.set(user);
            System.out.println("User updated at: " + updateResult.get().getUpdateTime());

            // Return user profile
            System.out.println("User signed in: " + user);
            return user;
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

    public User getUserDetailsByEmail(String email) throws ExecutionException, InterruptedException {
        ApiFuture<QuerySnapshot> query = firestore.collection("users").whereEqualTo("email", email).get();
        List<QueryDocumentSnapshot> documents = query.get().getDocuments();
        if (!documents.isEmpty()) {
            return documents.get(0).toObject(User.class);
        }
        return null;
    }
}
