package org.kvp_bld_sck.BookDatabase.controller.command.impl;

import org.kvp_bld_sck.BookDatabase.controller.command.Commands;
import org.kvp_bld_sck.BookDatabase.controller.command.Executable;
import org.kvp_bld_sck.BookDatabase.controller.exception.CannotExecuteCommandException;
import org.kvp_bld_sck.BookDatabase.controller.exception.ControllerException;
import org.kvp_bld_sck.BookDatabase.controller.session.SessionHolder;
import org.kvp_bld_sck.BookDatabase.controller.usercommunication.UserDataGetter;
import org.kvp_bld_sck.BookDatabase.controller.usercommunication.impl.UserDataGetterImpl;
import org.kvp_bld_sck.BookDatabase.entity.Profile;
import org.kvp_bld_sck.BookDatabase.service.ProfileService;
import org.kvp_bld_sck.BookDatabase.service.ServiceFabric;
import org.kvp_bld_sck.BookDatabase.service.exception.ServiceException;

import java.util.Date;

public class UpdateProfileExecute implements Executable<String> {

    private UserDataGetter userDataGetter = UserDataGetterImpl.getInstance();
    private ProfileService profileService = ServiceFabric.getFabric().getProfileService();

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
            profileService.updateProfile(profile, SessionHolder.getUserSession());
            return Commands.UPDATE_PROFILE.getSuccessMessage();
        } catch (ServiceException e) {
            throw new CannotExecuteCommandException(Commands.UPDATE_PROFILE.getFailMessage(), e);
        }
    }
}
