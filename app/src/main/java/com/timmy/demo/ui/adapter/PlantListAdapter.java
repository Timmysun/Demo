package com.timmy.demo.ui.adapter;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.timmy.demo.R;
import com.timmy.demo.databinding.PlantListItemBinding;
import com.timmy.demo.model.server.result.plant.PlantInfo;

import java.util.List;

public class PlantListAdapter extends BaseAdapter {

    private List<PlantInfo> mData;

    public PlantListAdapter(List<PlantInfo> data) {
        mData = data;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public PlantInfo getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PlantListItemBinding binding;
        if (convertView == null) {
            binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                    R.layout.plant_list_item, parent, false);
        } else {
            binding = DataBindingUtil.getBinding(convertView);
        }
        binding.setPlantInfo(mData.get(position));
        return binding.getRoot();
    }
}
