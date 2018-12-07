package com.timmy.demo.ui.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.timmy.demo.R;
import com.timmy.demo.event.ExhibitInfoChangeEvent;
import com.timmy.demo.event.ExhibitListDisplayEvent;
import com.timmy.demo.model.server.result.exhibit.ExhibitInfo;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class ExhibitViewModel extends AndroidViewModel {

    private ObservableField<ExhibitInfo> mExhibitInfo = new ObservableField<>();
    public ExhibitViewModel(@NonNull Application application) {
        super(application);
        EventBus.getDefault().register(this);
    }

    public ObservableField<ExhibitInfo> getCurrentExhibitInfo() {
        //return DataPool.getInstance().getCurrentExhibitInfo();
        return mExhibitInfo;
    }

    @Subscribe(threadMode = ThreadMode.MAIN_ORDERED)
    public void onExhibitInfoChange(ExhibitInfoChangeEvent event) {
        mExhibitInfo.set(event.getExhibitInfo());
    }

    public void onExhibitListClick(View view){
        EventBus.getDefault().post(ExhibitListDisplayEvent.SHOW_EXHIBIT_LIST);
    }

    public void onPlantListClick(View view){
        //EventBus.getDefault().post(ExhibitListDisplayEvent.HIDE_EXHIBIT_LIST);
    }

    @BindingAdapter({"app:imageSrc"})
    public static void loadImage(final ImageView imageView, String imageUrl) {
        Picasso.get()
                .load(imageUrl)
                .error(R.drawable.transparent_drawable)
                .into(imageView);
    }
}
