package org.kvp_bld_sck.BookDatabase.controller.impl;

import org.kvp_bld_sck.BookDatabase.controller.Controller;
import org.kvp_bld_sck.BookDatabase.controller.command.Commands;
import org.kvp_bld_sck.BookDatabase.controller.exception.ControllerException;
import org.kvp_bld_sck.BookDatabase.controller.session.SessionHolder;
import org.kvp_bld_sck.BookDatabase.view.ViewFabric;

public class ControllerImpl implements Controller {

    public void close() {
        try {
            if (SessionHolder.isSessionOpen())
                ViewFabric.getFabric().getView().showMessage(Commands.SIGN_OUT.execute());
        } catch (ControllerException e) {
            ViewFabric.getFabric().getView().showMessage(e.getMessage());
        }

        ViewFabric.getFabric().getView().close();
    }

    public String execute(String command) {
        command = command.toLowerCase();
        try {
            return Commands.execute(command);
        } catch (ControllerException e) {
            return e.getMessage();
        }
    }

}
