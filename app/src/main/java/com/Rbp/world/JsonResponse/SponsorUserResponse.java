package com.Rbp.world.JsonResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SponsorUserResponse {
    @SerializedName("error")
    @Expose
    private String error;
    @SerializedName("name")
    @Expose
    private String name;

    public SponsorUserResponse() {
    }

    public SponsorUserResponse(String error, String name) {
        this.error = error;
        this.name = name;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
