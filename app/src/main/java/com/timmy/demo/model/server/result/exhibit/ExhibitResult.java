package com.timmy.demo.model.server.result.exhibit;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;
import com.timmy.demo.model.server.result.ResultBase;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;

public class ExhibitResult extends ResultBase implements Serializable {

    private static final long serialVersionUID = 876323262645176354L;

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
        if (mExhibitInfos != null) {
            for (ExhibitInfo exhibitInfo : mExhibitInfos) {
                sb.append("[" + exhibitInfo.toString() + "]\n");
            }
        }
        return sb.toString();
    }

    public boolean equals(ExhibitResult result) {
        if (result == null) {
            return false;
        }

        return getInfos() != null && result.getInfos() != null && getInfos().size() == result.getInfos().size();
    }

}
