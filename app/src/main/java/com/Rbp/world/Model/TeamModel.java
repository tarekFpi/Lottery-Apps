package com.Rbp.world.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TeamModel {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("carry1")
    @Expose
    private String carry1;
    @SerializedName("carry2")
    @Expose
    private String carry2;
    @SerializedName("carry3")
    @Expose
    private String carry3;
    @SerializedName("username")
    @Expose
    private String referenceId;

    public TeamModel(String name, String username, String carry1, String carry2, String carry3, String referenceId) {
        this.name = name;
        this.username = username;
        this.carry1 = carry1;
        this.carry2 = carry2;
        this.carry3 = carry3;
        this.referenceId = referenceId;
    }

    public TeamModel() {
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

    public String getCarry1() {
        return carry1;
    }

    public void setCarry1(String carry1) {
        this.carry1 = carry1;
    }

    public String getCarry2() {
        return carry2;
    }

    public void setCarry2(String carry2) {
        this.carry2 = carry2;
    }

    public String getCarry3() {
        return carry3;
    }

    public void setCarry3(String carry3) {
        this.carry3 = carry3;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }
}
