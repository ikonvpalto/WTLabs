package org.kvp_bld_sck.BookDatabase.client.controller.command.impl;

import org.kvp_bld_sck.BookDatabase.client.controller.command.Commands;
import org.kvp_bld_sck.BookDatabase.client.controller.command.Executable;
import org.kvp_bld_sck.BookDatabase.client.controller.exception.CannotExecuteCommandException;
import org.kvp_bld_sck.BookDatabase.client.controller.exception.ControllerException;
import org.kvp_bld_sck.BookDatabase.client.controller.session.SessionHolder;
import org.kvp_bld_sck.BookDatabase.client.controller.usercommunication.UserDataGetter;
import org.kvp_bld_sck.BookDatabase.client.controller.usercommunication.impl.UserDataGetterImpl;
import org.kvp_bld_sck.BookDatabase.entity.Profile;
import org.kvp_bld_sck.BookDatabase.client.transport.TransportFabric;
import org.kvp_bld_sck.BookDatabase.client.transport.exception.TransportException;

import java.util.Date;

public class UpdateProfileExecute implements Executable<String> {

    private UserDataGetter userDataGetter = UserDataGetterImpl.getInstance();

    @Override
    public String execute() throws ControllerException {
        long id = userDataGetter.getId();
        String fullName = userDataGetter.getFullName(true);
        Profile.Sex sex = userDataGetter.getSex();
        Date birthDate = userDataGetter.getBirthDate();
        String characteristics = userDataGetter.getCharacteristics(true);
        Profile.SecurityLevel securityLevel = userDataGetter.getSecurityLevel();
        Profile profile = new Profile(id, fullName, sex, birthDate, characteristics, securityLevel);

        try {
            TransportFabric.getFabric().getClientTransport()
                    .sendRequest("updateProfile", profile, SessionHolder.getUserSession());
            return Commands.UPDATE_PROFILE.getSuccessMessage();
        } catch (TransportException e) {
            throw new CannotExecuteCommandException(Commands.UPDATE_PROFILE.getFailMessage(), e);
        }
    }
}
