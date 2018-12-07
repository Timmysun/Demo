package com.timmy.demo.event;

public class PlantListDisplayEvent extends BaseDisplayEvent {

    public static final PlantListDisplayEvent SHOW_PLANT_LIST = new PlantListDisplayEvent(true);
    public static final PlantListDisplayEvent HIDE_PLANT_LIST = new PlantListDisplayEvent(false);

    private PlantListDisplayEvent(boolean display) {
        super(display);
    }
}
