package com.timmy.demo.ui.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.timmy.demo.R;
import com.timmy.demo.databinding.MainFragmentBinding;

public class ExhibitListFragment extends Fragment {

    private MainViewModel mViewModel;

    private MainFragmentBinding mBinding;

    public static ExhibitListFragment newInstance() {
        return new ExhibitListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        mBinding = DataBindingUtil.setContentView(getActivity(), R.layout.main_fragment);
//        mBinding.setViewModel(mViewModel);
    }
}
