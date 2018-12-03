package com.timmy.demo.model.server.result.plant;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class Plant {
    @SerializedName("result")
    private PlantResult mResult;

    public PlantResult getResult() {
        return mResult;
    }
    @NonNull
    @Override
    public String toString() {
        return mResult.toString();
    }
}
