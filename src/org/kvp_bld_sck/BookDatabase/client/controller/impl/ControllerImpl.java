package org.kvp_bld_sck.BookDatabase.client.controller.impl;

import org.kvp_bld_sck.BookDatabase.client.controller.Controller;
import org.kvp_bld_sck.BookDatabase.client.controller.command.Commands;
import org.kvp_bld_sck.BookDatabase.client.controller.session.SessionHolder;
import org.kvp_bld_sck.BookDatabase.client.view.ViewFabric;

public class ControllerImpl implements Controller {

    public void close() {
        try {
            if (SessionHolder.isSessionOpen())
                ViewFabric.getFabric().getView().showMessage(Commands.SIGN_OUT.execute());
        } catch (Exception e) {
            ViewFabric.getFabric().getView().showMessage(e.getMessage());
        }

        ViewFabric.getFabric().getView().close();
    }

    public String execute(String command) {
        command = command.toLowerCase();
        try {
            return Commands.execute(command);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

}
