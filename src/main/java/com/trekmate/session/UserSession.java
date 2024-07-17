package com.trekmate.session;

import com.google.gson.Gson;
import com.trekmate.controller.AuthController;
import com.trekmate.model.User;

import java.util.prefs.Preferences;
import java.util.concurrent.ExecutionException;

public class UserSession {
    private static final String PREFS_NAME = "UserSessionPrefs";
    private static final String USER_DETAILS = "userDetails";

    private static Preferences preferences = Preferences.userRoot().node(PREFS_NAME);
    private static Gson gson = new Gson();
    private static AuthController authController = new AuthController();

    private UserSession() {
        // Private constructor to prevent instantiation
    }

    public static void saveUserDetails(User user) {
        String userDetailsJson = gson.toJson(user);
        preferences.put(USER_DETAILS, userDetailsJson);
    }

    public static User getUserDetails() {
        String userDetailsJson = preferences.get(USER_DETAILS, null);
        if (userDetailsJson != null) {
            return gson.fromJson(userDetailsJson, User.class);
        }
        return null;
    }

    public static boolean isAdmin() {
        User user = getUserDetails();
        return user != null && "admin".equals(user.getRole());
    }

    public static boolean isLoggedIn() {
        return preferences.get(USER_DETAILS, null) != null;
    }

    public static void updateUserDetailsFromAuthController() throws ExecutionException, InterruptedException {
        User user = getUserDetails();
        if (user != null) {
            String email = user.getEmail();
            User updatedUser = authController.getUserDetailsByEmail(email);
            if (updatedUser != null) {
                saveUserDetails(updatedUser);
            }
        }
    }

    public static void logout() {
        preferences.remove(USER_DETAILS);
    }
}
