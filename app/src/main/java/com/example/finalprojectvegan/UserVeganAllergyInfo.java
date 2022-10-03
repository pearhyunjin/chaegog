package com.example.finalprojectvegan;

public class UserVeganAllergyInfo {

    private String userAllergy;

    public UserVeganAllergyInfo(String userAllergy) {
        this.userAllergy = userAllergy;
    }

    public String getuserAllergy() {
        return userAllergy;
    }

    public void setuserAllergy(String userAllergy) {
        this.userAllergy = userAllergy;
    }

}
