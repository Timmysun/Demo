package com.timmy.demo.event;

import com.timmy.demo.model.server.result.plant.PlantInfo;

public class PlantInfoChangeEvent {
    private PlantInfo mPlantInfo;

    public PlantInfoChangeEvent(PlantInfo info) {
        mPlantInfo = info;
    }

    public PlantInfo getPlantInfo() {
        return mPlantInfo;
    }
}
