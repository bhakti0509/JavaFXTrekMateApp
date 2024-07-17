package com.trekmate.firebase;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.trekmate.model.Trek;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class FirebaseTrekService {

    private Firestore firestore;

    public FirebaseTrekService() {
        this.firestore = FirestoreConfig.getFirestore();
    }

    public void addTrek(Trek trek) throws InterruptedException, ExecutionException {
        try {
            DocumentReference docRef = firestore.collection("trips").document();
            Map<String, Object> data = new HashMap<>();
            data.put("fortName", trek.getFortName());
            data.put("description", trek.getDescription());
            data.put("location", trek.getLocation());
            data.put("contact", trek.getContact());
            data.put("trekDuration", trek.getTrekDuration());
            data.put("difficultyLevel", trek.getDifficultyLevel());
            data.put("openingTime", trek.getOpeningTime());
            data.put("closingTime", trek.getClosingTime());
            data.put("duration", trek.getDuration());
            data.put("cost", trek.getCost());
            data.put("imageUrl", trek.getImageUrl());
            data.put("qrCodeUrl", trek.getQrCodeUrl());
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

    public List<Trek> getTreks(int limit, int trekCount) throws ExecutionException, InterruptedException {
        List<Trek> treks = new ArrayList<>();
        ApiFuture<QuerySnapshot> query;
        
        if (trekCount == 0) {
            query = firestore.collection("trips")
                             .orderBy("createdOn")
                             .limit(limit)
                             .get();
        } else {
            query = firestore.collection("trips")
                             .orderBy("createdOn")
                             .offset(trekCount)
                             .limit(limit)
                             .get();
        }

        List<QueryDocumentSnapshot> documents = query.get().getDocuments();
        for (QueryDocumentSnapshot document : documents) {
            treks.add(document.toObject(Trek.class));
        }
        return treks;
    }
}
