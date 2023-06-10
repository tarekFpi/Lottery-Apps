package com.Rbp.world.JsonResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class LoginResponse {

    @SerializedName("error")
    @Expose
    private String error;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("user_id")
    @Expose
    private String userId;

    public LoginResponse() {
    }

    public LoginResponse(String error, String message, String userId) {
        this.error = error;
        this.message = message;
        this.userId = userId;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
