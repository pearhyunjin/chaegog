package com.example.finalprojectvegan.Model;

import com.google.gson.annotations.SerializedName;

public class RecipeData {
    @SerializedName("RCP_NM")
    private String recipeName;
    @SerializedName("RCP_PAT2")
    private String recipeType;
    @SerializedName("RCP_PARTS_DTLS")
    private String recipeIngredient;
    @SerializedName("RCP_WAY2")
    private String recipeWay;
    @SerializedName("HASH_TAG")
    private String recipeHashTag;
    @SerializedName("ATT_FILE_NO_MK")
    private String recipeImageMK;
    @SerializedName("ATT_FILE_NO_MAIN")
    private String recipeImageMAIN;
    @SerializedName("MANUAL_IMG01")
    private String manualImage01;
    @SerializedName("MANUAL_IMG02")
    private String manualImage02;
    @SerializedName("MANUAL_IMG03")
    private String manualImage03;
    @SerializedName("MANUAL_IMG04")
    private String manualImage04;
    @SerializedName("MANUAL_IMG05")
    private String manualImage05;
    @SerializedName("MANUAL_IMG06")
    private String manualImage06;
    @SerializedName("MANUAL01")
    private String manual01;
    @SerializedName("MANUAL02")
    private String manual02;
    @SerializedName("MANUAL03")
    private String manual03;
    @SerializedName("MANUAL04")
    private String manual04;
    @SerializedName("MANUAL05")
    private String manual05;
    @SerializedName("MANUAL06")
    private String manual06;

    public String getRecipeName() {
        return recipeName;
    }

    public String getRecipeType() {
        return recipeType;
    }

    public String getRecipeIngredient() {
        return recipeIngredient;
    }

    public String getRecipeWay() {
        return recipeWay;
    }

    public String getRecipeHashTag() {
        return recipeHashTag;
    }

    public String getRecipeImageMK() {
        return recipeImageMK;
    }

    public String getRecipeImageMAIN() {
        return recipeImageMAIN;
    }

    public String getManualImage01() {
        return manualImage01;
    }

    public String getManualImage02() {
        return manualImage02;
    }

    public String getManualImage03() {
        return manualImage03;
    }

    public String getManualImage04() {
        return manualImage04;
    }

    public String getManualImage05() {
        return manualImage05;
    }

    public String getManualImage06() {
        return manualImage06;
    }

    public String getManual01() {
        return manual01;
    }

    public String getManual02() {
        return manual02;
    }

    public String getManual03() {
        return manual03;
    }

    public String getManual04() {
        return manual04;
    }

    public String getManual05() {
        return manual05;
    }

    public String getManual06() {
        return manual06;
    }
}
