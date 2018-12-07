package com.timmy.demo.event;

import com.timmy.demo.model.server.result.exhibit.ExhibitInfo;

public class ExhibitInfoChangeEvent {
    private ExhibitInfo mExhibitInfo;

    public ExhibitInfoChangeEvent(ExhibitInfo info) {
        mExhibitInfo = info;
    }

    public ExhibitInfo getExhibitInfo() {
        return mExhibitInfo;
    }
}
