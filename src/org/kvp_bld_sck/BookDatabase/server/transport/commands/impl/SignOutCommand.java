package org.kvp_bld_sck.BookDatabase.server.transport.commands.impl;

import org.kvp_bld_sck.BookDatabase.entity.UserSession;
import org.kvp_bld_sck.BookDatabase.server.service.ServiceFabric;
import org.kvp_bld_sck.BookDatabase.server.service.UserService;
import org.kvp_bld_sck.BookDatabase.server.transport.commands.Command;
import org.kvp_bld_sck.BookDatabase.server.transport.exception.CannotExecuteException;

public class SignOutCommand implements Command {
    private UserService userService = ServiceFabric.getFabric().getUserService();

    @Override
    public Object execute(Object... params) throws CannotExecuteException {
        try {
            userService.signOut((UserSession) params[0]);
            return true;
        } catch (Exception e) {
            throw new CannotExecuteException("cannot sign out", e);
        }
    }
}