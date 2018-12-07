package com.timmy.demo.model;

import android.support.annotation.Nullable;

import com.timmy.demo.model.server.result.exhibit.ExhibitInfo;
import com.timmy.demo.model.server.result.exhibit.ExhibitResult;
import com.timmy.demo.model.server.result.plant.PlantResult;

import java.util.List;

public class ExhibitPlantInfos {

    private ExhibitResult mExhibitResult;
    private PlantResult mPlantResult;

    public ExhibitPlantInfos(ExhibitResult exhibitResult,
                             PlantResult plantResult) {
        mExhibitResult = exhibitResult;
        mPlantResult = plantResult;
        buildData();
    }

    private void buildData() {

    }

    public List<ExhibitInfo> getExhibitList() {
        return mExhibitResult.getInfos();
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
