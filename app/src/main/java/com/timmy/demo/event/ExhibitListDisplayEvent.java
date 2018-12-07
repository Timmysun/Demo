package com.timmy.demo.event;

public class ExhibitListDisplayEvent {

    public static final ExhibitListDisplayEvent SHOW_EXHIBIT_LIST = new ExhibitListDisplayEvent(true);
    public static final ExhibitListDisplayEvent HIDE_EXHIBIT_LIST = new ExhibitListDisplayEvent(false);

    private boolean mDisplay;

    private ExhibitListDisplayEvent(boolean display) {
        mDisplay = display;
    }

    public boolean display() {
        return mDisplay;
    }
}
