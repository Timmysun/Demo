package com.timmy.demo;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.timmy.demo.event.ExhibitAnimationCompleteEvent;
import com.timmy.demo.event.ExhibitInfoChangeEvent;
import com.timmy.demo.event.ExhibitListDisplayEvent;
import com.timmy.demo.event.PlantInfoChangeEvent;
import com.timmy.demo.event.PlantListDisplayEvent;
import com.timmy.demo.event.SplashCompleteEvent;
import com.timmy.demo.ui.fragment.ExhibitFragment;
import com.timmy.demo.ui.fragment.ExhibitListFragment;
import com.timmy.demo.ui.fragment.PlantFragment;
import com.timmy.demo.ui.fragment.PlantListFragment;
import com.timmy.demo.ui.fragment.SplashFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity {

    private Handler mHandler;
    private boolean mIsSplashCompleteDone = false;

    private ExhibitListFragment mExhibitListFragment;
    private PlantListFragment mPlantListFragment;

    private ExhibitFragment mExhibitFragment;
    private PlantFragment mPlantFragment;

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

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.container);
        if (fragment == mExhibitListFragment) {
            hideExhibitListFragment();
        } else if (fragment == mPlantListFragment) {
            hidePlantListFragment();
        } else {
            super.onBackPressed();
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    synchronized public void onSplashComplete(SplashCompleteEvent event) {
        if (!mIsSplashCompleteDone) {
            mIsSplashCompleteDone = true;
            showExhibitFragment();
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

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onPlantListDisplayStatusChange(PlantListDisplayEvent event) {
        if (event.display()) {
            showPlantListFragment();
        } else {
            hidePlantListFragment();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN_ORDERED)
    public void onPlantInfoChange(PlantInfoChangeEvent event) {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.container);
        if (fragment != mPlantFragment) {
            showPlantFragment();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN_ORDERED)
    public void onExhibitInfoChange(ExhibitInfoChangeEvent event) {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.container);
        if (fragment != mExhibitFragment) {
            showExhibitFragment();
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


    private void showPlantListFragment() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (mPlantListFragment == null) {
                    mPlantListFragment = PlantListFragment.newInstance();
                }
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.container, mPlantListFragment)
                        .commitNow();
            }
        });
    }

    private void hidePlantListFragment() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (mPlantListFragment != null) {
                    getSupportFragmentManager().beginTransaction()
                            .remove(mPlantListFragment)
                            .commitNow();
                }
            }
        });
    }

    private void showExhibitFragment() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (mExhibitFragment == null) {
                    mExhibitFragment = ExhibitFragment.newInstance();
                }
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, mExhibitFragment)
                        .commitNow();
            }
        });
    }


    private void showPlantFragment() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (mPlantFragment == null) {
                    mPlantFragment = PlantFragment.newInstance();
                }
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, mPlantFragment)
                        .commitNow();
            }
        });
    }
}
