package com.trekmate.firebase;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.trekmate.model.Trek;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class FirebaseTrekService {

    private Firestore firestore;

    public FirebaseTrekService() {
        this.firestore = FirestoreConfig.getFirestore();
    }

    public void addTrip(Trek trek) throws InterruptedException, ExecutionException {
        try {
            DocumentReference docRef = firestore.collection("trips").document();
            Map<String, Object> data = new HashMap<>();
            data.put("fortName", trek.getFortName());
            data.put("description", trek.getDescription());
            data.put("location", trek.getLocation());
            data.put("owner", trek.getOwner());
            data.put("trekDuration", trek.getTrekDuration());
            data.put("difficultyLevel", trek.getDifficultyLevel());
            data.put("openingTime", trek.getOpeningTime());
            data.put("closingTime", trek.getClosingTime());
            data.put("duration", trek.getDuration());
            data.put("cost", trek.getCost());
            data.put("imageUrl", trek.getImageUrl());
            data.put("likes", trek.getLikes());
            data.put("comments", trek.getComments());
            data.put("bookings", trek.getBookings());
            data.put("createdOn", trek.getCreatedOn());
            data.put("updatedOn", trek.getUpdatedOn());

            ApiFuture<WriteResult> result = docRef.set(data);
            System.out.println("Trip added at: " + result.get().getUpdateTime());
        } catch (ExecutionException | InterruptedException e) {
            throw new ExecutionException("Error adding trip: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error: " + e.getMessage(), e);
        }
    }
}
