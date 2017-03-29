package org.springframework.android.showcase.rest;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by PK on 2017-03-25.
 */

public class DrawersList {


    @JsonProperty("Drawers")
    private List<Drawer_Container> drawers;

    public DrawersList() {
    }

    public DrawersList(List<Drawer_Container> states) {
        this.drawers = states;
    }

    public List<Drawer_Container> getDrawers() {
        return this.drawers;
    }

    public void setDrawers(List<Drawer_Container> states) {
        this.drawers = states;
    }

}
