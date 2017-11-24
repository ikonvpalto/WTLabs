package org.kvp_bld_sck.BookDatabase.client.controller.session;

import org.kvp_bld_sck.BookDatabase.client.controller.command.Commands;
import org.kvp_bld_sck.BookDatabase.client.controller.exception.CannotExecuteCommandException;
import org.kvp_bld_sck.BookDatabase.client.controller.exception.ControllerException;
import org.kvp_bld_sck.BookDatabase.client.controller.exception.SessionNotOpenException;
import org.kvp_bld_sck.BookDatabase.client.controller.exception.SessionUnclosedException;
import org.kvp_bld_sck.BookDatabase.entity.UserSession;
import org.kvp_bld_sck.BookDatabase.entity.User;
import org.kvp_bld_sck.BookDatabase.client.transport.TransportFabric;
import org.kvp_bld_sck.BookDatabase.client.transport.exception.TransportException;

public class SessionHolder {

    private static UserSession userSession;

    public static void openSession(User user) throws ControllerException {
        if (null != userSession)
            throw new SessionUnclosedException("old userSession is not closed");

        try {
            userSession = TransportFabric.getFabric().getClientTransport().sendRequest("signIn", user);
        } catch (TransportException e) {
            throw new CannotExecuteCommandException(Commands.SIGN_IN.getFailMessage(), e);
        }
    }

    public static void closeSession() throws ControllerException {
        if (null == userSession)
            throw new SessionNotOpenException("there is no userSession had open");

        try {
            userSession = TransportFabric.getFabric().getClientTransport().sendRequest("signIn", userSession);
        } catch (TransportException e) {
            throw new CannotExecuteCommandException(Commands.SIGN_OUT.getFailMessage(), e);
        }

        userSession = null;
    }

    public static boolean isSessionOpen() {
        return null != userSession;
    }

    public static UserSession getUserSession() throws ControllerException {
        if (null == userSession)
            throw new SessionNotOpenException("there is no userSession had open");
        return userSession;
    }

}
