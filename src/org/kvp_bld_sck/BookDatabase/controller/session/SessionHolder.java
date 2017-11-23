package org.kvp_bld_sck.BookDatabase.controller.session;

import org.kvp_bld_sck.BookDatabase.controller.command.Commands;
import org.kvp_bld_sck.BookDatabase.controller.exception.CannotExecuteCommandException;
import org.kvp_bld_sck.BookDatabase.controller.exception.ControllerException;
import org.kvp_bld_sck.BookDatabase.controller.exception.SessionNotOpenException;
import org.kvp_bld_sck.BookDatabase.controller.exception.SessionUnclosedException;
import org.kvp_bld_sck.BookDatabase.entity.UserSession;
import org.kvp_bld_sck.BookDatabase.entity.User;
import org.kvp_bld_sck.BookDatabase.service.ServiceFabric;
import org.kvp_bld_sck.BookDatabase.service.exception.ServiceException;

public class SessionHolder {

    private static UserSession userSession;

    public static void openSession(User user) throws ControllerException {
        if (null != userSession)
            throw new SessionUnclosedException("old userSession is not closed");

        try {
            userSession = ServiceFabric.getFabric().getUserService().signIn(user);
        } catch (ServiceException e) {
            throw new CannotExecuteCommandException(Commands.SIGN_IN.getFailMessage(), e);
        }
    }

    public static void closeSession() throws ControllerException {
        if (null == userSession)
            throw new SessionNotOpenException("there is no userSession had open");

        try {
            ServiceFabric.getFabric().getUserService().signOut(userSession);
        } catch (ServiceException e) {
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
