package org.kvp_bld_sck.BookDatabase.server.transport.commands.impl;

import org.kvp_bld_sck.BookDatabase.entity.UserSession;
import org.kvp_bld_sck.BookDatabase.server.service.ProfileService;
import org.kvp_bld_sck.BookDatabase.server.service.ServiceFabric;
import org.kvp_bld_sck.BookDatabase.server.transport.commands.Command;
import org.kvp_bld_sck.BookDatabase.server.transport.exception.CannotExecuteException;

public class GetManProfileCommand implements Command {
    private ProfileService profileService = ServiceFabric.getFabric().getProfileService();

    @Override
    public Object execute(Object... params) throws CannotExecuteException {
        try {
            profileService.getProfile((String) params[0], (UserSession) params[1]);
            return true;
        } catch (Exception e) {
            throw new CannotExecuteException("cannot get man profile", e);
        }
    }
}