package com.timmy.demo.model;

import android.support.annotation.Nullable;

import com.timmy.demo.model.server.result.exhibit.ExhibitResult;
import com.timmy.demo.model.server.result.plant.PlantResult;

public class ExhibitPlantInfos {
    private static ExhibitPlantInfos sEmptyInfos = new ExhibitPlantInfos();
    private ExhibitResult mExhibitResult;
    private PlantResult mPlantResult;

    public static ExhibitPlantInfos getEmptyInfos() {
        return sEmptyInfos;
    }

    public static boolean isEmptyInfos(ExhibitPlantInfos infos) {
        return sEmptyInfos.equals(infos);
    }

    private ExhibitPlantInfos() {}

    public ExhibitPlantInfos(ExhibitResult exhibitResult,
                             PlantResult plantResult) {
        mExhibitResult = exhibitResult;
        mPlantResult = plantResult;
        buildData();
    }

    private void buildData() {

    }


    public boolean updateExhibitResult(@Nullable ExhibitResult exhibitResult) {

        if (mExhibitResult == null || !mExhibitResult.equals(exhibitResult)) {
            mExhibitResult = exhibitResult;
            buildData();
            return true;
        } else {
            return false;
        }
    }

    public boolean updatePlantResult(PlantResult plantResult) {

        if (mPlantResult == null || !mPlantResult.equals(plantResult)) {
            mPlantResult = plantResult;
            buildData();
            return true;
        } else {
            return false;
        }
    }
}
