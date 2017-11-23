package org.kvp_bld_sck.BookDatabase.transport.commands.impl;

import org.kvp_bld_sck.BookDatabase.entity.User;
import org.kvp_bld_sck.BookDatabase.service.ServiceFabric;
import org.kvp_bld_sck.BookDatabase.service.UserService;
import org.kvp_bld_sck.BookDatabase.service.exception.ServiceException;
import org.kvp_bld_sck.BookDatabase.transport.commands.Command;
import org.kvp_bld_sck.BookDatabase.transport.exception.CannotExecuteException;

public class SignUpCommand implements Command {
    private UserService userService = ServiceFabric.getFabric().getUserService();

    @Override
    public Object execute(Object... params) throws CannotExecuteException {
        try {
            userService.signUp((User) params[0]);
            return true;
        } catch (Exception e) {
            throw new CannotExecuteException("cannot sign up", e);
        }
    }
}
