package org.kvp_bld_sck.BookDatabase.controller.command.impl;

import org.apache.commons.codec.digest.DigestUtils;
import org.kvp_bld_sck.BookDatabase.controller.command.Commands;
import org.kvp_bld_sck.BookDatabase.controller.exception.CannotExecuteCommandException;
import org.kvp_bld_sck.BookDatabase.controller.exception.ControllerException;
import org.kvp_bld_sck.BookDatabase.controller.usercommunication.UserDataGetter;
import org.kvp_bld_sck.BookDatabase.controller.command.Executable;
import org.kvp_bld_sck.BookDatabase.controller.usercommunication.impl.UserDataGetterImpl;
import org.kvp_bld_sck.BookDatabase.entity.User;
import org.kvp_bld_sck.BookDatabase.service.ServiceFabric;
import org.kvp_bld_sck.BookDatabase.service.exception.ServiceException;

public class SignUpExecute implements Executable<String> {

    private UserDataGetter userDataGetter = UserDataGetterImpl.getInstance();

    @Override
    public String execute() throws ControllerException {
        String username = userDataGetter.getUsername(true);
        String email = userDataGetter.getEmail(true);
        String password = userDataGetter.getConfirmedPassword();

        password = DigestUtils.md5Hex(password);

        User user = new User(username, password, email, User.UserGroup.USER);

        try {
            ServiceFabric.getFabric().getUserService().signUp(user);
        } catch (ServiceException e) {
            throw new CannotExecuteCommandException(Commands.SIGN_UP.getFailMessage(), e);
        }

        return Commands.SIGN_UP.getSuccessMessage() + ", " + username;
    }

}
