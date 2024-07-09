package com.trekmate.session;

import com.google.gson.Gson;
import java.util.Map;
import java.util.prefs.Preferences;

public class UserSession {
    private static final String PREFS_NAME = "UserSessionPrefs";
    private static final String USER_DETAILS = "userDetails";
    private Preferences preferences;
    private Gson gson;

    public UserSession() {
        preferences = Preferences.userRoot().node(PREFS_NAME);
        gson = new Gson();
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

    public void clearUserDetails() {
        preferences.remove(USER_DETAILS);
    }

    public boolean isLoggedIn() {
        return preferences.get(USER_DETAILS, null) != null;
    }
}
