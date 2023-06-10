package com.Rbp.world.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Income_list_model {

    private String serial;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("username")
    @Expose
    private String username;

    @SerializedName("date")
    @Expose
    private String date;

    @SerializedName("income_Amount")
    @Expose
    private int incomeAmount;

    @SerializedName("income_type")
    @Expose
    private String incomeType;

    private  String genaration;

    public String getGenaration() {
        return genaration;
    }

    public void setGenaration(String genaration) {
        this.genaration = genaration;
    }

    public Income_list_model() {
    }

    public Income_list_model(String serial, String username, String date, int incomeAmount, String incomeType) {
        this.serial = serial;
        this.username = username;
        this.date = date;
        this.incomeAmount = incomeAmount;
        this.incomeType = incomeType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getIncomeAmount() {
        return incomeAmount;
    }

    public void setIncomeAmount(int incomeAmount) {
        this.incomeAmount = incomeAmount;
    }

    public String getIncomeType() {
        return incomeType;
    }

    public void setIncomeType(String incomeType) {
        this.incomeType = incomeType;
    }
}
