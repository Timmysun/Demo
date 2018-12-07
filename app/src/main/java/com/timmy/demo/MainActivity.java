package com.timmy.demo;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.timmy.demo.event.ExhibitAnimationCompleteEvent;
import com.timmy.demo.event.ExhibitListDisplayEvent;
import com.timmy.demo.event.SplashCompleteEvent;
import com.timmy.demo.ui.fragment.ExhibitFragment;
import com.timmy.demo.ui.fragment.ExhibitListFragment;
import com.timmy.demo.ui.fragment.SplashFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity {

    private Handler mHandler;
    private boolean mIsSplashCompleteDone = false;

    private ExhibitListFragment mExhibitListFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        EventBus.getDefault().register(this);
        mHandler = new Handler();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, SplashFragment.newInstance())
                    .commitNow();
        }
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    synchronized public void onSplashComplete(SplashCompleteEvent event) {
        if (!mIsSplashCompleteDone) {
            mIsSplashCompleteDone = true;
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, ExhibitFragment.newInstance())
                            .commitNow();
                }
            }, 1000);
        }
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onExhibitAnimationComplete(ExhibitAnimationCompleteEvent event) {
        showExhibitListFragment();
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onExhibitListDisplayStatusChange(ExhibitListDisplayEvent event) {
        if (event.display()) {
            showExhibitListFragment();
        } else {
            hideExhibitListFragment();
        }
    }

    private void showExhibitListFragment() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (mExhibitListFragment == null) {
                    mExhibitListFragment = ExhibitListFragment.newInstance();
                }
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.container, mExhibitListFragment)
                        .commitNow();
            }
        });
    }

    private void hideExhibitListFragment() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (mExhibitListFragment != null) {
                    getSupportFragmentManager().beginTransaction()
                            .remove(mExhibitListFragment)
                            .commitNow();
                }
            }
        });
    }
}
