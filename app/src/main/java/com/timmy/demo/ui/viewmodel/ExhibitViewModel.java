package com.timmy.demo.ui.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.view.View;

import com.timmy.demo.event.ExhibitInfoChangeEvent;
import com.timmy.demo.event.ExhibitListDisplayEvent;
import com.timmy.demo.event.PlantListDisplayEvent;
import com.timmy.demo.model.DataPool;
import com.timmy.demo.model.server.result.exhibit.ExhibitInfo;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class ExhibitViewModel extends AndroidViewModel {

    private ObservableField<ExhibitInfo> mExhibitInfo = new ObservableField<>();

    public ExhibitViewModel(@NonNull Application application) {
        super(application);
        EventBus.getDefault().register(this);
    }

    public ObservableField<ExhibitInfo> getCurrentExhibitInfo() {
        return mExhibitInfo;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        EventBus.getDefault().unregister(this);
    }

    public void updateExhibitInfo() {
        ExhibitInfo exhibitInfo = DataPool.getInstance().getCurrentExhibitInfo();
        if (exhibitInfo != null) {
            mExhibitInfo.set(exhibitInfo);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN_ORDERED)
    public void onExhibitInfoChange(ExhibitInfoChangeEvent event) {
        mExhibitInfo.set(event.getExhibitInfo());
    }

    public void onExhibitListClick(View view) {
        EventBus.getDefault().post(ExhibitListDisplayEvent.SHOW_EXHIBIT_LIST);
    }

    public void onPlantListClick(View view) {
        EventBus.getDefault().post(PlantListDisplayEvent.SHOW_PLANT_LIST);
    }


}
