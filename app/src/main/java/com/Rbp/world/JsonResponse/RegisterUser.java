package com.Rbp.world.JsonResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegisterUser {

    @SerializedName("security_error")
    @Expose
    private String securityError;
    @SerializedName("full_name")
    @Expose
    private String fullName;
    @SerializedName("user_Name")
    @Expose
    private String userName;
    @SerializedName("mobile_number")
    @Expose
    private String mobileNumber;
    @SerializedName("Joining_code")
    @Expose
    private String joiningCode;
    @SerializedName("sponser_u_id")
    @Expose
    private String sponserUId;
    @SerializedName("placement_u_id")
    @Expose
    private String placementUId;
    @SerializedName("team_name")
    @Expose
    private String teamName;

    public RegisterUser() {
    }

    public RegisterUser(String securityError, String fullName, String userName, String mobileNumber, String joiningCode, String sponserUId, String placementUId, String teamName) {
        this.securityError = securityError;
        this.fullName = fullName;
        this.userName = userName;
        this.mobileNumber = mobileNumber;
        this.joiningCode = joiningCode;
        this.sponserUId = sponserUId;
        this.placementUId = placementUId;
        this.teamName = teamName;
    }

    public String getSecurityError() {
        return securityError;
    }

    public void setSecurityError(String securityError) {
        this.securityError = securityError;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getJoiningCode() {
        return joiningCode;
    }

    public void setJoiningCode(String joiningCode) {
        this.joiningCode = joiningCode;
    }

    public String getSponserUId() {
        return sponserUId;
    }

    public void setSponserUId(String sponserUId) {
        this.sponserUId = sponserUId;
    }

    public String getPlacementUId() {
        return placementUId;
    }

    public void setPlacementUId(String placementUId) {
        this.placementUId = placementUId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }
}
