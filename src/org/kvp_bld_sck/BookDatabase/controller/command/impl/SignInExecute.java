package org.kvp_bld_sck.BookDatabase.controller.command.impl;

import org.apache.commons.codec.digest.DigestUtils;
import org.kvp_bld_sck.BookDatabase.controller.command.Commands;
import org.kvp_bld_sck.BookDatabase.controller.exception.ControllerException;
import org.kvp_bld_sck.BookDatabase.controller.usercommunication.UserDataGetter;
import org.kvp_bld_sck.BookDatabase.controller.command.Executable;
import org.kvp_bld_sck.BookDatabase.controller.session.SessionHolder;
import org.kvp_bld_sck.BookDatabase.controller.usercommunication.impl.UserDataGetterImpl;
import org.kvp_bld_sck.BookDatabase.entity.User;

public class SignInExecute implements Executable<String> {

    private UserDataGetter userDataGetter = UserDataGetterImpl.getInstance();

    @Override
    public String execute() throws ControllerException {
        if (SessionHolder.isSessionOpen())
            return "there is user had sign in";

        String username = userDataGetter.getUsername();
        String password = userDataGetter.getPassword();

        password = DigestUtils.md5Hex(password);

        User user = new User(username, password);

        SessionHolder.openSession(user);

        return Commands.SIGN_IN.getSuccessMessage() + ", " + user.getUsername();
    }
}
