package com.timmy.demo.ui.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.view.View;

import com.timmy.demo.R;
import com.timmy.demo.event.ExhibitListDisplayEvent;
import com.timmy.demo.event.PlantInfoChangeEvent;
import com.timmy.demo.event.PlantListDisplayEvent;
import com.timmy.demo.model.DataPool;
import com.timmy.demo.model.server.result.plant.PlantInfo;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class PlantViewModel extends AndroidViewModel {

    private ObservableField<PlantInfo> mPlantInfo = new ObservableField<>();
    private ObservableField<String> mPlantMessage = new ObservableField<>();

    public PlantViewModel(@NonNull Application application) {
        super(application);
        EventBus.getDefault().register(this);
    }

    public ObservableField<PlantInfo> getCurrentExhibitInfo() {
        return mPlantInfo;
    }
    public ObservableField<String> getPlantMessage() {
        return mPlantMessage;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        EventBus.getDefault().unregister(this);
    }

    public void updatePlantInfo() {
        PlantInfo plantInfo = DataPool.getInstance().getCurrentPlantInfo();
        if (plantInfo != null) {
            mPlantInfo.set(plantInfo);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN_ORDERED)
    public void onPlantInfoChange(PlantInfoChangeEvent event) {
        mPlantInfo.set(event.getPlantInfo());
    }

    public void onExhibitListClick(View view){
        EventBus.getDefault().post(ExhibitListDisplayEvent.SHOW_EXHIBIT_LIST);
    }

    public void onPlantListClick(View view){
        EventBus.getDefault().post(PlantListDisplayEvent.SHOW_PLANT_LIST);

    }

    public void onTabChanged(int position) {
        switch (position) {
            case 0:
                String message = getApplication().getResources().getString(R.string.tab_intro_message,
                        mPlantInfo.get().getFamily(), mPlantInfo.get().getGenus(),
                        mPlantInfo.get().getEnName(), mPlantInfo.get().getLatinName(),
                        mPlantInfo.get().getAlsoKnown(), mPlantInfo.get().getUpdate());
                mPlantMessage.set(message);
                break;
            case 1:
                mPlantMessage.set(mPlantInfo.get().getBrief());
                break;
            case 2:
                mPlantMessage.set(mPlantInfo.get().getFeature());
                break;
            case 3:
                mPlantMessage.set(mPlantInfo.get().getApplication());
                break;
        }
    }
}
