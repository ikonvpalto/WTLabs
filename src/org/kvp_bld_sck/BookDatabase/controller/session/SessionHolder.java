package org.kvp_bld_sck.BookDatabase.controller.session;

import org.kvp_bld_sck.BookDatabase.controller.command.Commands;
import org.kvp_bld_sck.BookDatabase.controller.exception.CannotExecuteCommandException;
import org.kvp_bld_sck.BookDatabase.controller.exception.ControllerException;
import org.kvp_bld_sck.BookDatabase.controller.exception.SessionNotOpenException;
import org.kvp_bld_sck.BookDatabase.controller.exception.SessionUnclosedException;
import org.kvp_bld_sck.BookDatabase.entity.Session;
import org.kvp_bld_sck.BookDatabase.entity.User;
import org.kvp_bld_sck.BookDatabase.service.ServiceFabric;
import org.kvp_bld_sck.BookDatabase.service.exception.ServiceException;

public class SessionHolder {

    private static Session session;

    public static void openSession(User user) throws ControllerException {
        if (null != session)
            throw new SessionUnclosedException("old session is not closed");

        try {
            session = ServiceFabric.getFabric().getUserService().signIn(user);
        } catch (ServiceException e) {
            throw new CannotExecuteCommandException(Commands.SIGN_IN.getFailMessage(), e);
        }
    }

    public static void closeSession() throws ControllerException {
        if (null == session)
            throw new SessionNotOpenException("there is no session had open");

        try {
            ServiceFabric.getFabric().getUserService().signOut(session);
        } catch (ServiceException e) {
            throw new CannotExecuteCommandException(Commands.SIGN_OUT.getFailMessage(), e);
        }

        session = null;
    }

    public static boolean isSessionOpen() {
        return null != session;
    }

    public static Session getSession() throws ControllerException {
        if (null == session)
            throw new SessionNotOpenException("there is no session had open");
        return session;
    }

}
