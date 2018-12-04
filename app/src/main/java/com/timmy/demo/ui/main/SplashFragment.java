package com.timmy.demo.ui.main;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.Observable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.timmy.demo.BR;
import com.timmy.demo.R;

public class SplashFragment extends Fragment {

    private MainViewModel mViewModel;

    public static SplashFragment newInstance() {
        return new SplashFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_splash, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);
        Log.e("Timmy", "splahs" + mViewModel);
        mViewModel.retrieveData();
        mViewModel.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                Log.e("Timmy", "splash onPropertyChanged " + propertyId + BR.exhibitPlantInfos + BR.toastMessage);
                if (propertyId == BR.exhibitPlantInfos) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getFragmentManager().beginTransaction()
                                    .replace(R.id.container, ExhibitListFragment.newInstance())
                                    .commitNow();
                        }
                    });

                }
            }
        });
    }
}
