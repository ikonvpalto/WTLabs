package org.kvp_bld_sck.BookDatabase.client.controller;

import org.kvp_bld_sck.BookDatabase.client.controller.impl.ControllerImpl;

public class ControllerFabric {

    private static ControllerFabric instance;
    public static ControllerFabric getFabric() {
        if (null == instance)
            instance = new ControllerFabric();
        return instance;
    }

    private Controller controller;

    private ControllerFabric() {
        controller = new ControllerImpl();
    }

    public Controller getController() {
        return new ControllerImpl();//controller;
    }
}