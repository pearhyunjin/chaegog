package com.example.finalprojectvegan;

import java.util.Date;

public class WriteReviewInfo {

    private int rating;
    private String review;
    private String publisher;
    private String imagePath1;
    private String imagePath2;
    private String imagePath3;
    private Date createdAt;

    public WriteReviewInfo(String review, String publisher, String imagePath1, Date createdAt) {
        this.review = review;
        this.publisher = publisher;
        this.imagePath1 = imagePath1;
        this.imagePath2 = imagePath2;
        this.imagePath3 = imagePath3;
        this.createdAt = createdAt;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getImagePath1() {
        return imagePath1;
    }

    public void setImagePath1(String imagePath1) {
        this.imagePath1 = imagePath1;
    }

    public String getImagePath2() {
        return imagePath2;
    }

    public void setImagePath2(String imagePath2) {
        this.imagePath2 = imagePath2;
    }

    public String getImagePath3() {
        return imagePath3;
    }

    public void setImagePath3(String imagePath3) {
        this.imagePath3 = imagePath3;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
