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

import java.util.Date;

public class AddProfileExecute implements Executable<String> {

    private UserDataGetter userDataGetter = UserDataGetterImpl.getInstance();

    @Override
    public String execute() throws ControllerException {
        String fullName = userDataGetter.getFullName(true);
        Profile.Sex sex = userDataGetter.getSex();
        Date birthDate = userDataGetter.getBirthDate();
        String characteristics = userDataGetter.getCharacteristics(true);
        Profile.SecurityLevel securityLevel = userDataGetter.getSecurityLevel();

        Profile profile = new Profile(fullName, sex, birthDate, characteristics, securityLevel);

        try {
            int id = (Integer) TransportFabric.getFabric().getClientTransport()
                    .sendRequest("addProfile", profile, SessionHolder.getUserSession());
            return "new book id=" + id;
        } catch (TransportException e) {
            throw new CannotExecuteCommandException(Commands.ADD_PROFILE.getFailMessage(), e);
        }

    }
}
