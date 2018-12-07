package com.timmy.demo.model.server.result.plant;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;
import com.timmy.demo.model.server.result.ResultBase;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;

public class PlantResult extends ResultBase implements Serializable {

    private static final long serialVersionUID = 876323262645176355L;

    @SerializedName("results")
    private List<PlantInfo> mPlantInfos;

    public List<PlantInfo> getInfos() {
        return mPlantInfos;
    }

    @NonNull
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format(Locale.getDefault(), "limit=%d offset=%d count=%d sort=%s\n",
                getLimit(), getOffset(), getCount(), getSort()));
        return sb.toString();
    }

    public boolean equals(PlantResult result) {
        if (result == null || getInfos() == null || result.getInfos() == null ||
                getInfos().size() != result.getInfos().size()) {
            return false;
        }
        for (int index = 0; index < getInfos().size(); index++) {
            if (!getInfos().get(index).equals(result.getInfos().get(index))) {
                return false;
            }
        }
        return true;
    }
}
