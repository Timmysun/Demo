package com.timmy.demo.ui.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;
import android.view.View;

import com.timmy.demo.event.ExhibitListDisplayEvent;
import com.timmy.demo.model.DataPool;
import com.timmy.demo.model.server.result.exhibit.ExhibitInfo;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class ExhibitListViewModel extends AndroidViewModel {

    public ExhibitListViewModel(@NonNull Application application) {
        super(application);
    }

    public List<ExhibitInfo> getExhibitList() {
        return DataPool.getInstance().getExhibitList();
    }

    public void setCurrentExhibit(int index) {
        DataPool.getInstance().setCurrentExhibit(index);
    }

    public void onClickView(View view) {
        EventBus.getDefault().post(ExhibitListDisplayEvent.HIDE_EXHIBIT_LIST);
    }
}
