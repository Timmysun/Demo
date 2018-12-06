package com.timmy.demo;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.Observable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.timmy.demo.ui.fragment.ExhibitListFragment;
import com.timmy.demo.ui.fragment.SplashFragment;
import com.timmy.demo.ui.viewmodel.MainViewModel;

public class MainActivity extends AppCompatActivity {

    private Handler mHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        mHandler = new Handler();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, SplashFragment.newInstance())
                    .commitNow();
        }

        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getCompleteField().addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                //delay 1s for display smooth.
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getSupportFragmentManager().beginTransaction()
                                .setCustomAnimations(R.anim.fragment_fade_in, R.anim.fragment_fade_out)
                                .replace(R.id.container, ExhibitListFragment.newInstance())
                                .commitNow();
                    }
                }, 1000);
            }
        });
    }
}
