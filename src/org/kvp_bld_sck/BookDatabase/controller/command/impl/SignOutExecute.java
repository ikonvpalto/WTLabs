package org.kvp_bld_sck.BookDatabase.controller.command.impl;

import org.kvp_bld_sck.BookDatabase.controller.command.Commands;
import org.kvp_bld_sck.BookDatabase.controller.command.Executable;
import org.kvp_bld_sck.BookDatabase.controller.exception.ControllerException;
import org.kvp_bld_sck.BookDatabase.controller.session.SessionHolder;

public class SignOutExecute implements Executable {

    @Override
    public String execute() throws ControllerException {
        String username = SessionHolder.getUserSession().getUser().getUsername();
        SessionHolder.closeSession();
        return Commands.SIGN_OUT.getSuccessMessage() + ", " + username;
    }

}
