package org.kvp_bld_sck.BookDatabase.client.controller.command.impl;

import org.kvp_bld_sck.BookDatabase.client.controller.command.Commands;
import org.kvp_bld_sck.BookDatabase.client.controller.command.Executable;
import org.kvp_bld_sck.BookDatabase.client.controller.exception.ControllerException;
import org.kvp_bld_sck.BookDatabase.client.controller.session.SessionHolder;

public class SignOutExecute implements Executable {

    @Override
    public String execute() throws ControllerException {
        String username = SessionHolder.getUserSession().getUser().getUsername();
        SessionHolder.closeSession();
        return Commands.SIGN_OUT.getSuccessMessage() + ", " + username;
    }

}
