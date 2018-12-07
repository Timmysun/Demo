package com.timmy.demo.event;

/**
 * EventBus event for display
 */
public class BaseDisplayEvent {
    private boolean mDisplay;

    BaseDisplayEvent(boolean display) {
        mDisplay = display;
    }

    public boolean display() {
        return mDisplay;
    }
}
