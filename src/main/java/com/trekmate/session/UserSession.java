package com.trekmate.session;

import com.google.gson.Gson;
import com.trekmate.firebase.FirebaseAuthService;

import java.util.Map;
import java.util.prefs.Preferences;
import java.util.concurrent.ExecutionException;

public class UserSession {
    private static final String PREFS_NAME = "UserSessionPrefs";
    private static final String USER_DETAILS = "userDetails";
    private Preferences preferences;
    private Gson gson;
    private FirebaseAuthService firebaseAuthService;

    public UserSession() {
        preferences = Preferences.userRoot().node(PREFS_NAME);
        gson = new Gson();
        firebaseAuthService = new FirebaseAuthService();

        if (preferences.get(USER_DETAILS, null) != null){
            try {
                updateUserDetailsFromFirestore();
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void saveUserDetails(Map<String, Object> userDetails) {
        String userDetailsJson = gson.toJson(userDetails);
        preferences.put(USER_DETAILS, userDetailsJson);
    }

    public Map<String, Object> getUserDetails() {
        String userDetailsJson = preferences.get(USER_DETAILS, null);
        if (userDetailsJson != null) {
            return gson.fromJson(userDetailsJson, Map.class);
        }
        return null;
    }

    public boolean isAdmin() {
        Map<String, Object> userDetails = getUserDetails();
        if (userDetails != null) {
            return "admin".equals(userDetails.get("role"));
        }
        return false;
    }

    public void clearUserDetails() {
        preferences.remove(USER_DETAILS);
    }

    public boolean isLoggedIn() {
        return preferences.get(USER_DETAILS, null) != null;
    }

    public void updateUserDetailsFromFirestore() throws ExecutionException, InterruptedException {
        Map<String, Object> userDetails = getUserDetails();
        if (userDetails != null) {
            String email = (String) userDetails.get("email");
            Map<String, Object> updatedUserDetails = firebaseAuthService.getUserDetailsByEmail(email);
            if (updatedUserDetails != null) {
                saveUserDetails(updatedUserDetails);
            }
        }
    }
}
