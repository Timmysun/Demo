package com.timmy.demo.model.server.result;

import com.google.gson.annotations.SerializedName;

public class ResultBase {

    @SerializedName("limit")
    private int mLimit;

    @SerializedName("offset")
    private int mOffset;

    @SerializedName("count")
    private int mCount;

    @SerializedName("sort")
    private String mSort;

    public int getLimit() {
        return mLimit;
    }

    public int getOffset() {
        return mOffset;
    }

    public int getCount() {
        return mCount;
    }

    public String getSort() {
        return mSort;
    }
}
