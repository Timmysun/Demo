package com.timmy.demo.ui.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.timmy.demo.R;
import com.timmy.demo.event.RetrieveDataStatusEvent;
import com.timmy.demo.event.SplashCompleteEvent;
import com.timmy.demo.model.DataPool;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class SplashViewModel extends AndroidViewModel {

    private ObservableField<String> mLoadingMessage = new ObservableField<>();

    public SplashViewModel(@NonNull Application application) {
        super(application);
        EventBus.getDefault().register(this);
    }

    public ObservableField<String> getLoadingMessage() {
        return mLoadingMessage;
    }

    public void retrieveData() {
        DataPool.getInstance().retrieveData(getApplication().getApplicationContext());
    }

    @Subscribe(threadMode = ThreadMode.MAIN_ORDERED)
    public void onRetrieveDataStatusChange(RetrieveDataStatusEvent status) {
        switch (status) {
            case READ_CACHE:
                mLoadingMessage.set(getApplication().getResources().getString(R.string.splash_loading_read_cache));
                break;
            case READ_CACHE_FAIL:
                mLoadingMessage.set(getApplication().getResources().getString(R.string.splash_loading_cache_fail));
                break;
            case READ_CACHE_SUCCESS:
                mLoadingMessage.set(getApplication().getResources().getString(R.string.splash_loading_cache_success));
                EventBus.getDefault().post(new SplashCompleteEvent());
                break;
            case LOAD_DATA:
                mLoadingMessage.set(getApplication().getResources().getString(R.string.splash_loading_network_data));
                break;
            case LOAD_DATA_FAIL:
                mLoadingMessage.set(getApplication().getResources().getString(R.string.splash_loading_network_fail));
                break;
            case LOAD_DATA_SUCCESS:
                mLoadingMessage.set(getApplication().getResources().getString(R.string.splash_loading_network_success));
                EventBus.getDefault().post(new SplashCompleteEvent());
                break;
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        EventBus.getDefault().unregister(this);
    }
}
