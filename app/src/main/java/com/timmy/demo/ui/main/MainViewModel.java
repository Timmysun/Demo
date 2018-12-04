package com.timmy.demo.ui.main;

import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

import com.timmy.demo.model.DataPool;

public class MainViewModel extends ViewModel {

    public final ObservableField<String> mData = new ObservableField<>();

    public final ObservableBoolean isLoading = new ObservableBoolean(false);

    private DataPool mDataPool = new DataPool();

    public void refresh(final Context context) {

        isLoading.set(true);

        mDataPool.retrieveData(context);
    }
}
