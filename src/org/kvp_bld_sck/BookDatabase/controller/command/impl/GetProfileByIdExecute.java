package org.kvp_bld_sck.BookDatabase.controller.command.impl;

import org.kvp_bld_sck.BookDatabase.controller.command.Commands;
import org.kvp_bld_sck.BookDatabase.controller.command.Executable;
import org.kvp_bld_sck.BookDatabase.controller.exception.CannotExecuteCommandException;
import org.kvp_bld_sck.BookDatabase.controller.exception.ControllerException;
import org.kvp_bld_sck.BookDatabase.controller.session.SessionHolder;
import org.kvp_bld_sck.BookDatabase.controller.usercommunication.UserDataGetter;
import org.kvp_bld_sck.BookDatabase.controller.usercommunication.impl.UserDataGetterImpl;
import org.kvp_bld_sck.BookDatabase.entity.Profile;
import org.kvp_bld_sck.BookDatabase.transport.TransportFabric;
import org.kvp_bld_sck.BookDatabase.transport.exception.TransportException;

public class GetProfileByIdExecute implements Executable<String> {

    private UserDataGetter userDataGetter = UserDataGetterImpl.getInstance();

    @Override
    public String execute() throws ControllerException {
        long id = userDataGetter.getId();

        try {
            Profile profile = (Profile) TransportFabric.getFabric().getClientTransport()
                    .sendRequest("getProfileById", id, SessionHolder.getUserSession());
            return profile.toString();
        } catch (TransportException e) {
            throw new CannotExecuteCommandException(Commands.GET_PROFILE_BY_ID.getFailMessage(), e);
        }
    }
}
