package com.timmy.demo;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.Observable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.timmy.demo.ui.fragment.ExhibitListFragment;
import com.timmy.demo.ui.fragment.SplashFragment;
import com.timmy.demo.ui.viewmodel.MainViewModel;
import com.timmy.demo.utils.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        EventBus.getDefault().register(this);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, SplashFragment.newInstance())
                    .commitNow();
        }
        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getCompleteField().addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, ExhibitListFragment.newInstance())
                        .commitNow();
            }
        });

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRetrieveDataStatusChange(Utils.RetrieveDataStatus status) {
        if (status == Utils.RetrieveDataStatus.READ_CACHE_SUCCESS ||
                status == Utils.RetrieveDataStatus.LOAD_DATA_SUCCESS) {
            EventBus.getDefault().unregister(this);
//            mHandler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//
//                    FragmentManager fragmentManager = getSupportFragmentManager();
//                    Log.e("Timmy", "eb fm = " + fragmentManager);
//                    fragmentManager.beginTransaction()
//                    .replace(R.id.container, ExhibitListFragment.newInstance())
//                    .commitNow();
//                }
//            }, 2000);

        }
    }
}
