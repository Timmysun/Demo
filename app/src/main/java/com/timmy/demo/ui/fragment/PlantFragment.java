package com.timmy.demo.ui.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.timmy.demo.R;
import com.timmy.demo.databinding.PlantFragmentBinding;
import com.timmy.demo.ui.viewmodel.PlantViewModel;

public class PlantFragment extends Fragment {


    public static PlantFragment newInstance() {
        return new PlantFragment();
    }

    private PlantViewModel mViewModel;
    private PlantFragmentBinding mBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = ViewModelProviders.of(this).get(PlantViewModel.class);
        mBinding = DataBindingUtil.inflate(inflater, R.layout.plant_fragment, container, false);
        mBinding.setViewModel(mViewModel);
        mBinding.setPlantInfo(mViewModel.getCurrentExhibitInfo());
        mBinding.setInfoText(mViewModel.getPlantMessage());
        mBinding.tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewModel.onTabChanged(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel.updatePlantInfo();
        mViewModel.onTabChanged(mBinding.tab.getSelectedTabPosition());
    }


    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return AnimationUtils.loadAnimation(getContext(),
                enter ? R.anim.fragment_fade_in : R.anim.fragment_fade_out);
    }
}
