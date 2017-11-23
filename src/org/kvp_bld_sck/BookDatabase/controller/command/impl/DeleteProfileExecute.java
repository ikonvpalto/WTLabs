package org.kvp_bld_sck.BookDatabase.controller.command.impl;

import org.kvp_bld_sck.BookDatabase.controller.command.Commands;
import org.kvp_bld_sck.BookDatabase.controller.command.Executable;
import org.kvp_bld_sck.BookDatabase.controller.exception.CannotExecuteCommandException;
import org.kvp_bld_sck.BookDatabase.controller.exception.ControllerException;
import org.kvp_bld_sck.BookDatabase.controller.session.SessionHolder;
import org.kvp_bld_sck.BookDatabase.controller.usercommunication.UserDataGetter;
import org.kvp_bld_sck.BookDatabase.controller.usercommunication.impl.UserDataGetterImpl;
import org.kvp_bld_sck.BookDatabase.service.ProfileService;
import org.kvp_bld_sck.BookDatabase.service.ServiceFabric;
import org.kvp_bld_sck.BookDatabase.service.exception.ServiceException;

public class DeleteProfileExecute implements Executable<String> {

    private UserDataGetter userDataGetter = UserDataGetterImpl.getInstance();
    private ProfileService profileService = ServiceFabric.getFabric().getProfileService();

    @Override
    public String execute() throws ControllerException {
        long id = userDataGetter.getId();

        try {
            profileService.deleteProfile(id, SessionHolder.getUserSession());
            return Commands.DELETE_PROFILE.getSuccessMessage();
        } catch (ServiceException e) {
            throw new CannotExecuteCommandException(Commands.DELETE_PROFILE.getFailMessage(), e);
        }
    }
}
