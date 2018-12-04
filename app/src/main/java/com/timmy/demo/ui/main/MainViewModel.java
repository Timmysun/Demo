package com.timmy.demo.ui.main;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.databinding.Observable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.timmy.demo.model.DataPool;

public class MainViewModel extends AndroidViewModel {

    public final ObservableField<String> mData = new ObservableField<>();

    public final ObservableBoolean isLoading = new ObservableBoolean(false);

    private DataPool mDataPool = new DataPool();

    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    public void addOnPropertyChangedCallback(Observable.OnPropertyChangedCallback callback) {
        mDataPool.addOnPropertyChangedCallback(callback);
    }

    public void retrieveData() {


        isLoading.set(true);

        mDataPool.retrieveData(getApplication().getApplicationContext());
    }
}
