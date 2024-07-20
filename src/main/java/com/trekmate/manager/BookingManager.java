package com.trekmate.manager;

import com.trekmate.model.Trek;
import java.util.ArrayList;
import java.util.List;

public class BookingManager {
    private static List<Trek> bookings = new ArrayList<>();

    public static void addBooking(Trek trek) {
        bookings.add(trek);
    }

    public static List<Trek> getBookings() {
        return new ArrayList<>(bookings); // Return a copy to avoid external modification
    }
}