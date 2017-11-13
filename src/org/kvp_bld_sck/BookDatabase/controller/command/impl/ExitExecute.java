package org.kvp_bld_sck.BookDatabase.controller.command.impl;

import org.kvp_bld_sck.BookDatabase.controller.ControllerFabric;
import org.kvp_bld_sck.BookDatabase.controller.command.Commands;
import org.kvp_bld_sck.BookDatabase.controller.command.Executable;

public class ExitExecute implements Executable<String> {

    @Override
    public String execute() {
        ControllerFabric.getFabric().getController().close();
        return Commands.EXIT.getSuccessMessage();
    }

}
