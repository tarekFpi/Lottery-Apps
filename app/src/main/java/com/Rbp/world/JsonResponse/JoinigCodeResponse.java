package com.Rbp.world.JsonResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JoinigCodeResponse {
    @SerializedName("error")
    @Expose
    private String error;
    @SerializedName("point")
    @Expose
    private String point;



    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }
}
