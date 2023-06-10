package com.Rbp.world.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DailyTaskHistory_model {
    @SerializedName("income_Amount")
    @Expose
    private int incomeAmount;
    @SerializedName("income_Type")
    @Expose
    private String incomeType;
    @SerializedName("date")
    @Expose
    private String date;

    public DailyTaskHistory_model(int incomeAmount, String incomeType, String date) {
        this.incomeAmount = incomeAmount;
        this.incomeType = incomeType;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
