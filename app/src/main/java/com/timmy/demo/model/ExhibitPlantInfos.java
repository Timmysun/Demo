package com.timmy.demo.model;

import android.support.annotation.Nullable;
import android.support.v4.util.Pair;

import com.timmy.demo.model.server.result.exhibit.ExhibitInfo;
import com.timmy.demo.model.server.result.exhibit.ExhibitResult;
import com.timmy.demo.model.server.result.plant.PlantInfo;
import com.timmy.demo.model.server.result.plant.PlantResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExhibitPlantInfos {

    private ExhibitResult mExhibitResult;
    private PlantResult mPlantResult;

    private ArrayList<Pair<ExhibitInfo, ArrayList<PlantInfo>>> mExhibitToPlant = new ArrayList<>();

    public ExhibitPlantInfos(ExhibitResult exhibitResult,
                             PlantResult plantResult) {
        mExhibitResult = exhibitResult;
        mPlantResult = plantResult;
        buildData();
    }

    private void buildData() {
        mExhibitToPlant.clear();

        HashMap<String, Pair<ExhibitInfo, ArrayList<PlantInfo>>> exhibitMap = new HashMap<>();
        for (ExhibitInfo exhibitInfo : mExhibitResult.getInfos()) {
            ArrayList<PlantInfo> arrayList = new ArrayList<>();
            mExhibitToPlant.add(Pair.create(exhibitInfo, arrayList));
            exhibitMap.put(exhibitInfo.getName(), Pair.create(exhibitInfo, arrayList));
        }

        for (PlantInfo plantInfo : mPlantResult.getInfos()) {
            for (String location : plantInfo.getLocationList()) {
                for (Map.Entry<String, Pair<ExhibitInfo, ArrayList<PlantInfo>>> entry : exhibitMap.entrySet()) {
                    if (entry.getKey().contains(location)) {
                        Pair<ExhibitInfo, ArrayList<PlantInfo>> info = entry.getValue();
                        if (info != null) {
                            if (info.second != null) {
                                info.second.add(plantInfo);
                            }
                        }
                    }
                }
            }
        }
    }

    List<PlantInfo> getExhibitPlants(int exhibitIndex) {
        return exhibitIndex >= 0 && exhibitIndex < mExhibitToPlant.size() ?
                mExhibitToPlant.get(exhibitIndex).second : null;
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
