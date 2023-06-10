package com.Rbp.world.JsonResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PTeamResponse {

    @SerializedName("error")
    @Expose
    private String error;

    public PTeamResponse(String error) {
        this.error = error;
    }

    public PTeamResponse() {
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
