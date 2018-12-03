package com.timmy.demo.model.server.result.exhibit;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class Exhibit {
    @SerializedName("result")
    private ExhibitResult mResult;

    public ExhibitResult getResult() {
        return mResult;
    }

    @NonNull
    @Override
    public String toString() {
        return mResult.toString();
    }
}
