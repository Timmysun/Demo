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
import com.timmy.demo.event.ExhibitAnimationCompleteEvent;

import org.greenrobot.eventbus.EventBus;

public class ExhibitFragment extends Fragment {

    public static ExhibitFragment newInstance() {
        return new ExhibitFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.exhibit_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {

        final Animation animation = AnimationUtils.loadAnimation(getContext(),
                enter ?  R.anim.fragment_fade_in : R.anim.fragment_fade_out);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) { }

            @Override
            public void onAnimationEnd(Animation animation) {
                EventBus.getDefault().post(new ExhibitAnimationCompleteEvent());
            }

            @Override
            public void onAnimationRepeat(Animation animation) { }
        });

        return animation;
    }
}
