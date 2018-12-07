package com.timmy.demo.ui.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;
import android.view.View;

import com.timmy.demo.event.PlantListDisplayEvent;
import com.timmy.demo.model.DataPool;
import com.timmy.demo.model.server.result.plant.PlantInfo;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class PlantListViewModel extends AndroidViewModel {

    public PlantListViewModel(@NonNull Application application) {
        super(application);
    }

    public List<PlantInfo> getPlantList() {
        return DataPool.getInstance().getExhibitPlantsList();
    }

    public void setCurrentPlant(int index) {
        DataPool.getInstance().setCurrentPlant(index);
    }

    public void onClickView(View view){
        EventBus.getDefault().post(PlantListDisplayEvent.HIDE_PLANT_LIST);
    }
}
