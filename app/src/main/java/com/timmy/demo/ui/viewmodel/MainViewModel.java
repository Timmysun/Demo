package com.timmy.demo.ui.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.timmy.demo.utils.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainViewModel extends AndroidViewModel {

    private ObservableField<Boolean> mSplashComplete = new ObservableField<>(false);

    public MainViewModel(@NonNull Application application) {
        super(application);
        EventBus.getDefault().register(this);
    }

    public ObservableField<Boolean> getCompleteField() {
        return mSplashComplete;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRetrieveDataStatusChange(Utils.RetrieveDataStatus status) {
        switch (status) {
            case READ_CACHE_SUCCESS:
                mSplashComplete.set(true);
                break;
            case LOAD_DATA_SUCCESS:
                if (!mSplashComplete.get()) {
                    mSplashComplete.set(true);
                }
                break;
            default:
                break;
        }
    }
}
