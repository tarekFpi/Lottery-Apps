package com.Rbp.world.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Transfer_Withdraw_model {


    private String username;

    private Integer afterAmount;

    private String date;

    private Integer amount;

    private String method;

    private  String status;

    public Transfer_Withdraw_model(String username, Integer afterAmount, String date, Integer amount, String method) {
        this.username = username;
        this.afterAmount = afterAmount;
        this.date = date;
        this.amount = amount;
        this.method = method;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getAfterAmount() {
        return afterAmount;
    }

    public void setAfterAmount(Integer afterAmount) {
        this.afterAmount = afterAmount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}
