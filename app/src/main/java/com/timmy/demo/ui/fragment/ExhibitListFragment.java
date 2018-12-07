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
import com.timmy.demo.databinding.ExhibitListFragmentBinding;
import com.timmy.demo.event.ExhibitListDisplayEvent;
import com.timmy.demo.ui.adapter.ExhibitListAdapter;
import com.timmy.demo.ui.viewmodel.ExhibitListViewModel;

import org.greenrobot.eventbus.EventBus;

public class ExhibitListFragment extends Fragment {


    public static ExhibitListFragment newInstance() {
        return new ExhibitListFragment();
    }

    private ExhibitListViewModel mViewModel;
    private ExhibitListAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = ViewModelProviders.of(this).get(ExhibitListViewModel.class);
        ExhibitListFragmentBinding binding = DataBindingUtil.inflate(inflater, R.layout.exhibit_list_fragment, container, false);
        mAdapter = new ExhibitListAdapter(mViewModel.getExhibitList());
        binding.setAdapter(mAdapter);
        binding.setViewModel(mViewModel);
        binding.exhibitList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mViewModel.setCurrentExhibit(position);
                EventBus.getDefault().post(ExhibitListDisplayEvent.HIDE_EXHIBIT_LIST);
            }
        });
        return binding.getRoot();
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {

        final Animation animation = AnimationUtils.loadAnimation(getContext(),
                enter ?  R.anim.fragment_exhibit_list_move_in : R.anim.fragment_exhibit_list_move_out);
        return animation;
    }
}
