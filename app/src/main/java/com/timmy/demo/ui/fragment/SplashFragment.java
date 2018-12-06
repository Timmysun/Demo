package com.timmy.demo.ui.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.timmy.demo.R;
import com.timmy.demo.databinding.SplashFragmentBinding;
import com.timmy.demo.ui.viewmodel.SplashViewModel;

public class SplashFragment extends Fragment {

    public static SplashFragment newInstance() {
        return new SplashFragment();
    }

    private SplashViewModel mViewModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = ViewModelProviders.of(this).get(SplashViewModel.class);
        SplashFragmentBinding binding = DataBindingUtil.inflate(inflater, R.layout.splash_fragment, container, false);
        binding.setLoadingMessage(mViewModel.getLoadingMessage());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel.retrieveData();
    }
}