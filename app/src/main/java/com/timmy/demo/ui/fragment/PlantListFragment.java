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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;

import com.timmy.demo.R;
import com.timmy.demo.databinding.PlantListFragmentBinding;
import com.timmy.demo.event.PlantListDisplayEvent;
import com.timmy.demo.ui.adapter.PlantListAdapter;
import com.timmy.demo.ui.viewmodel.PlantListViewModel;

import org.greenrobot.eventbus.EventBus;

public class PlantListFragment extends Fragment {


    public static PlantListFragment newInstance() {
        return new PlantListFragment();
    }

    private PlantListViewModel mViewModel;
    private PlantListAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = ViewModelProviders.of(this).get(PlantListViewModel.class);
        PlantListFragmentBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.plant_list_fragment, container, false);
        mAdapter = new PlantListAdapter(mViewModel.getPlantList());
        binding.setAdapter(mAdapter);
        binding.setViewModel(mViewModel);
        binding.plantList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mViewModel.setCurrentPlant(position);
                EventBus.getDefault().post(PlantListDisplayEvent.HIDE_PLANT_LIST);
            }
        });
        return binding.getRoot();
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        final Animation animation = AnimationUtils.loadAnimation(getContext(),
                enter ? R.anim.fragment_plant_list_move_in : R.anim.fragment_plant_list_move_out);
        return animation;
    }
}
