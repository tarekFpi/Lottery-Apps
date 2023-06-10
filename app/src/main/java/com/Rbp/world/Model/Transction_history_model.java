package com.Rbp.world.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Transction_history_model {
    String serial;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("user_Id")
    @Expose
    private String userId;
    @SerializedName("to_User_Id")
    @Expose
    private String toUserId;
    @SerializedName("amount")
    @Expose
    private int amount;
    @SerializedName("note")
    @Expose
    private String note;
    @SerializedName("transction_Type")
    @Expose
    private String transctionType;
    @SerializedName("method")
    @Expose
    private String method;
    @SerializedName("after_amount")
    @Expose
    private int afterAmount;

    public Transction_history_model() {
    }

    public Transction_history_model(String serial, String username, String date, int amount, String note, String transctionType, String method, int afterAmount) {
        this.serial = serial;
        this.username = username;
        this.date = date;
        this.amount = amount;
        this.note = note;
        this.transctionType = transctionType;
        this.method = method;
        this.afterAmount = afterAmount;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getToUserId() {
        return toUserId;
    }

    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getTransctionType() {
        return transctionType;
    }

    public void setTransctionType(String transctionType) {
        this.transctionType = transctionType;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public int getAfterAmount() {
        return afterAmount;
    }

    public void setAfterAmount(int afterAmount) {
        this.afterAmount = afterAmount;
    }
}
