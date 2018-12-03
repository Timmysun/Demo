package com.timmy.demo.model.server.result.exhibit;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;
import com.timmy.demo.model.server.result.ResultBase;

import java.util.List;
import java.util.Locale;

public class ExhibitResult extends ResultBase {

    @SerializedName("results")
    private List<ExhibitInfo> mExhibitInfos;

    public List<ExhibitInfo> getInfos() {
        return mExhibitInfos;
    }

    @NonNull
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format(Locale.getDefault(), "limit=%d offset=%d count=%d sort=%s\n",
                getLimit(), getOffset(), getCount(), getSort()));
        for (ExhibitInfo exhibitInfo : mExhibitInfos) {
            sb.append("[" + exhibitInfo.toString() + "]\n");
        }
        return sb.toString();
    }

}
