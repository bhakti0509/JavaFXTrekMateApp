package com.trekmate.firebase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import java.util.HashMap;
import java.util.Map;

public class FirebaseAuthService {

    private FirebaseAuth firebaseAuth;

    public FirebaseAuthService() {
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void createUser(String email, String password, String username, String firstName, String lastName, String role) throws FirebaseAuthException {
        // Create user with email and password
        UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                .setEmail(email)
                .setPassword(password)
                .setDisplayName(firstName + " " + lastName);

        UserRecord userRecord = firebaseAuth.createUser(request);

        // Set custom claims for the user
        setCustomUserClaims(userRecord.getUid(), username, role);
    }

    private void setCustomUserClaims(String uid, String username, String role) throws FirebaseAuthException {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", uid);
        claims.put("username", username);
        claims.put("role", role);

        firebaseAuth.setCustomUserClaims(uid, claims);
    }
}
