package com.Rbp.world.JsonResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Home_page_Response {
    @SerializedName("main_picture_Url")
    @Expose
    private String mainPictureUrl;
    @SerializedName("main_balance")
    @Expose
    private String mainBalance;
    @SerializedName("user_instant_income")
    @Expose
    private int userInstantIncome;
    @SerializedName("user_team_db")
    @Expose
    private int userTeamDb;
    @SerializedName("user_center_sel_c")
    @Expose
    private int userCenterSelC;
    @SerializedName("daily_income")
    @Expose
    private int dailyIncome;
    @SerializedName("total_income")
    @Expose
    private int totalIncome;
    @SerializedName("total_withdraw")
    @Expose
    private int totalWithdraw;
    @SerializedName("currency")
    @Expose
    private String currency;

    public Home_page_Response(String mainPictureUrl, String mainBalance, int userInstantIncome, int userTeamDb, int userCenterSelC, int dailyIncome, int totalIncome, int totalWithdraw, String currency) {
        this.mainPictureUrl = mainPictureUrl;
        this.mainBalance = mainBalance;
        this.userInstantIncome = userInstantIncome;
        this.userTeamDb = userTeamDb;
        this.userCenterSelC = userCenterSelC;
        this.dailyIncome = dailyIncome;
        this.totalIncome = totalIncome;
        this.totalWithdraw = totalWithdraw;
        this.currency = currency;
    }

    public String getMainPictureUrl() {
        return mainPictureUrl;
    }

    public void setMainPictureUrl(String mainPictureUrl) {
        this.mainPictureUrl = mainPictureUrl;
    }

    public String getMainBalance() {
        return mainBalance;
    }

    public void setMainBalance(String mainBalance) {
        this.mainBalance = mainBalance;
    }

    public int getUserInstantIncome() {
        return userInstantIncome;
    }

    public void setUserInstantIncome(int userInstantIncome) {
        this.userInstantIncome = userInstantIncome;
    }

    public int getUserTeamDb() {
        return userTeamDb;
    }

    public void setUserTeamDb(int userTeamDb) {
        this.userTeamDb = userTeamDb;
    }

    public int getUserCenterSelC() {
        return userCenterSelC;
    }

    public void setUserCenterSelC(int userCenterSelC) {
        this.userCenterSelC = userCenterSelC;
    }

    public int getDailyIncome() {
        return dailyIncome;
    }

    public void setDailyIncome(int dailyIncome) {
        this.dailyIncome = dailyIncome;
    }

    public int getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(int totalIncome) {
        this.totalIncome = totalIncome;
    }

    public int getTotalWithdraw() {
        return totalWithdraw;
    }

    public void setTotalWithdraw(int totalWithdraw) {
        this.totalWithdraw = totalWithdraw;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
