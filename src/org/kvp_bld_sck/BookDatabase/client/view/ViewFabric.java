package org.kvp_bld_sck.BookDatabase.client.view;

import org.kvp_bld_sck.BookDatabase.client.view.impl.ConsoleView;

public class ViewFabric {

    private static ViewFabric instance;
    public static ViewFabric getFabric() {
        if (null == instance)
            instance = new ViewFabric();
        return instance;
    }

    private View view;

    private ViewFabric() {
        view = new ConsoleView();
    }

    public View getView() {
        return view;
    }
}
