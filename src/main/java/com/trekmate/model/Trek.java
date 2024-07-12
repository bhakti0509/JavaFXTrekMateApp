package com.trekmate.model;

import java.time.Instant;
import java.util.List;

public class Trek {
    private String fortName;
    private String description;
    private String location;
    private String contact;
    private String trekDuration;
    private String difficultyLevel;
    private String openingTime;
    private String closingTime;
    private String duration;
    private String cost;
    private List<String> imageUrl;
    private String qrCodeUrl;
    private int likes;
    private int comments;
    private List<String> bookings;
    private String createdOn;
    private String updatedOn;

    public Trek(String fortName, String description, String location, String contact, String trekDuration, String difficultyLevel, String openingTime, String closingTime, String duration, String cost, List<String> imageUrl, int likes, int comments, List<String> bookings, String qrCodeUrl) {
        this.fortName = fortName;
        this.description = description;
        this.location = location;
        this.contact = contact;
        this.trekDuration = trekDuration;
        this.difficultyLevel = difficultyLevel;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
        this.duration = duration;
        this.cost = cost;
        this.imageUrl = imageUrl;
        this.likes = likes;
        this.comments = comments;
        this.bookings = bookings;
        this.createdOn = Instant.now().toString();
        this.updatedOn = Instant.now().toString();
        this.qrCodeUrl = qrCodeUrl;
    }

    public Trek() {
    }

    // Getters and setters for all fields (generated automatically or manually)
    
    public String getQrCodeUrl() {
        return qrCodeUrl;
    }

    public void setQrCodeUrl(String qrCodeUrl) {
        this.qrCodeUrl = qrCodeUrl;
    }

    public String getFortName() {
        return fortName;
    }

    public void setFortName(String fortName) {
        this.fortName = fortName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getTrekDuration() {
        return trekDuration;
    }

    public void setTrekDuration(String trekDuration) {
        this.trekDuration = trekDuration;
    }

    public String getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(String difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public String getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(String openingTime) {
        this.openingTime = openingTime;
    }

    public String getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(String closingTime) {
        this.closingTime = closingTime;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public List<String> getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(List<String> imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    public List<String> getBookings() {
        return bookings;
    }

    public void setBookings(List<String> bookings) {
        this.bookings = bookings;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(String updatedOn) {
        this.updatedOn = updatedOn;
    }

}
