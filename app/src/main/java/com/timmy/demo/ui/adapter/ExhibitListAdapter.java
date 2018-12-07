package com.timmy.demo.ui.adapter;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.timmy.demo.R;

import com.timmy.demo.databinding.ExhibitListItemBinding;
import com.timmy.demo.model.server.result.exhibit.ExhibitInfo;

import java.util.List;

public class ExhibitListAdapter extends BaseAdapter {

    private List<ExhibitInfo> mData;

    public ExhibitListAdapter(List<ExhibitInfo> data) {
        mData = data;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public ExhibitInfo getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ExhibitListItemBinding binding;
        if (convertView == null) {
            binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.exhibit_list_item, parent, false);
        } else {
            binding = DataBindingUtil.getBinding(convertView);
        }
        binding.setExhibitInfo(mData.get(position));
        return binding.getRoot();
    }
}
