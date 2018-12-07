package com.timmy.demo.event;

public class ExhibitListDisplayEvent extends DisplayEvent {

    public static final ExhibitListDisplayEvent SHOW_EXHIBIT_LIST = new ExhibitListDisplayEvent(true);
    public static final ExhibitListDisplayEvent HIDE_EXHIBIT_LIST = new ExhibitListDisplayEvent(false);

    private ExhibitListDisplayEvent(boolean display) {
        super(display);
    }
}
