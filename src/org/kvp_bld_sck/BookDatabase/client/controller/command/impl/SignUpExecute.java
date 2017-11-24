package org.kvp_bld_sck.BookDatabase.client.controller.command.impl;

import org.apache.commons.codec.digest.DigestUtils;
import org.kvp_bld_sck.BookDatabase.client.controller.command.Commands;
import org.kvp_bld_sck.BookDatabase.client.controller.exception.CannotExecuteCommandException;
import org.kvp_bld_sck.BookDatabase.client.controller.exception.ControllerException;
import org.kvp_bld_sck.BookDatabase.client.controller.usercommunication.UserDataGetter;
import org.kvp_bld_sck.BookDatabase.client.controller.command.Executable;
import org.kvp_bld_sck.BookDatabase.client.controller.usercommunication.impl.UserDataGetterImpl;
import org.kvp_bld_sck.BookDatabase.entity.User;
import org.kvp_bld_sck.BookDatabase.client.transport.TransportFabric;
import org.kvp_bld_sck.BookDatabase.client.transport.exception.TransportException;

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
            TransportFabric.getFabric().getClientTransport().sendRequest("signUp", user);
        } catch (TransportException e) {
            throw new CannotExecuteCommandException(Commands.SIGN_UP.getFailMessage(), e);
        }

        return Commands.SIGN_UP.getSuccessMessage() + ", " + username;
    }

}
