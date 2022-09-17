package com.example.finalprojectvegan.Model;

public class User {

    private String id;
    private String userName;
    private String userEmail;
    private String imageurl;
    private String bio;

    public User(String id, String userName, String userEmail, String imageurl, String bio) {
        this.id = id;
        this.userName = userName;
        this.userEmail = userEmail;
        this.imageurl = imageurl;
        this.bio = bio;
    }

    public User() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
