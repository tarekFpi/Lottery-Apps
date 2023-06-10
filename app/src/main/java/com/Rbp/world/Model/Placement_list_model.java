package com.Rbp.world.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Placement_list_model {
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("join_Date")
    @Expose
    private String joinDate;

    @SerializedName("placement_User_n")
    @Expose
    private Object placementUserN;
    @SerializedName("sponsor_User_n")
    @Expose
    private String sponsorUserN;
    @SerializedName("user_Id")
    @Expose
    private String userId;
    @SerializedName("carry1")
    @Expose
    private int carry1;
    @SerializedName("carry2")
    @Expose
    private int carry2;
    @SerializedName("carry3")
    @Expose
    private int carry3;

    public Placement_list_model(String username, String joinDate, String sponsorUserN, int carry1, int carry2, int carry3) {
        this.username = username;
        this.joinDate = joinDate;
        this.sponsorUserN = sponsorUserN;
        this.carry1 = carry1;
        this.carry2 = carry2;
        this.carry3 = carry3;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(String joinDate) {
        this.joinDate = joinDate;
    }

    public Object getPlacementUserN() {
        return placementUserN;
    }

    public void setPlacementUserN(Object placementUserN) {
        this.placementUserN = placementUserN;
    }

    public String getSponsorUserN() {
        return sponsorUserN;
    }

    public void setSponsorUserN(String sponsorUserN) {
        this.sponsorUserN = sponsorUserN;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getCarry1() {
        return carry1;
    }

    public void setCarry1(int carry1) {
        this.carry1 = carry1;
    }

    public int getCarry2() {
        return carry2;
    }

    public void setCarry2(int carry2) {
        this.carry2 = carry2;
    }

    public int getCarry3() {
        return carry3;
    }

    public void setCarry3(int carry3) {
        this.carry3 = carry3;
    }
}
