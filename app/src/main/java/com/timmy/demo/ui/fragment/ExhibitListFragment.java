package com.timmy.demo.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.timmy.demo.R;

public class ExhibitListFragment extends Fragment {


    public static ExhibitListFragment newInstance() {
        return new ExhibitListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.exhibit_list_fragment, container, false);
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {

        final Animation animation = AnimationUtils.loadAnimation(getContext(),
                enter ?  R.anim.fragment_exhibit_list_move_in : R.anim.fragment_exhibit_list_move_out);
        return animation;
    }
}
