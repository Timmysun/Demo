package com.timmy.demo;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.timmy.demo.ui.fragment.SplashFragment;
import com.timmy.demo.utils.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity {

    Handler mHandler;
    Fragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        EventBus.getDefault().register(this);
        if (savedInstanceState == null) {
            fragment = SplashFragment.newInstance();
            FragmentManager fragmentManager = getSupportFragmentManager();
            Log.e("Timmy", "fm = " + fragmentManager);
            fragmentManager.beginTransaction()
                    .replace(R.id.container, fragment)
                    .commitNow();
        }
        mHandler = new Handler();
//        mHandler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//                FragmentManager fragmentManager = getSupportFragmentManager();
//                Log.e("Timmy", "fm = " + fragmentManager);
//                fragmentManager.beginTransaction()
//                        .replace(R.id.container, ExhibitListFragment.newInstance())
//                        .commitNow();
//            }
//        }, 10000);

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRetrieveDataStatusChange(Utils.RetrieveDataStatus status) {
        Log.e("Timmy", "fragment " + status);
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
