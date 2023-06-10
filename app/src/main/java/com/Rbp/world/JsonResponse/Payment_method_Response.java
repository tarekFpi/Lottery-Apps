package com.Rbp.world.JsonResponse;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Payment_method_Response {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("method_name")
    @Expose
    private String methodName;

    public Payment_method_Response(Integer id, String methodName) {
        this.id = id;
        this.methodName = methodName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    @NonNull
    @Override
    public String toString() {
        return methodName;
    }
}
