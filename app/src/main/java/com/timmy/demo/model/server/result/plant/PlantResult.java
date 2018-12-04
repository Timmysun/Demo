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
//        for (PlantInfo plantInfo : mPlantInfos) {
//            sb.append("[" + plantInfo.toString() + "]\n");
//        }
        return sb.toString();
    }

}
