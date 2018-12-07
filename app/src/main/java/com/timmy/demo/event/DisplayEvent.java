package com.timmy.demo.event;

public class DisplayEvent {
    private boolean mDisplay;

    DisplayEvent(boolean display) {
        mDisplay = display;
    }

    public boolean display() {
        return mDisplay;
    }
}
