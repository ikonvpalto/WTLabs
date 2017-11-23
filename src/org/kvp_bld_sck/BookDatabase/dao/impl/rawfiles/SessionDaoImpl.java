package org.kvp_bld_sck.BookDatabase.dao.impl.rawfiles;

import org.apache.commons.codec.digest.DigestUtils;
import org.kvp_bld_sck.BookDatabase.dao.SessionDao;
import org.kvp_bld_sck.BookDatabase.dao.exception.DaoException;
import org.kvp_bld_sck.BookDatabase.dao.exception.SessionDaoException;
import org.kvp_bld_sck.BookDatabase.dao.exception.SessionNotFoundException;
import org.kvp_bld_sck.BookDatabase.entity.UserSession;
import org.kvp_bld_sck.BookDatabase.entity.User;

import java.io.*;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class SessionDaoImpl implements SessionDao {

    private static final String SESSION_FILE = ".\\resources\\session.dat";

    private List<UserSession> userSessions;
    private boolean working;

    private void write(PrintStream out, UserSession userSession) throws DaoException {
        try {
            out.println("{");
            out.println("\t" + userSession.getId());
            out.println("\t" + userSession.getUser().getId());
            out.println("}");
        } catch (Exception e) {
            throw new SessionDaoException("cannot write to user data file", e);
        }
    }

    private UserSession read(Scanner in) throws DaoException {
        try {
            in.nextLine();
            String id = in.nextLine().trim();
            long userId = Long.parseLong(in.nextLine().trim());
            in.nextLine();

            return new UserSession(id, new User(userId));
        } catch (Exception e) {
            throw new SessionDaoException("cannot read from user data file", e);
        }
    }

    private void load() throws DaoException {
        try(Scanner in = FileUtils.getIn(SESSION_FILE)) {
            userSessions = new LinkedList<>();
            while (in.hasNextLine())
                userSessions.add(read(in));
        }
    }

    private void save() throws DaoException {
        try(PrintStream out = FileUtils.getOut(SESSION_FILE)) {
            for (UserSession userSession : userSessions)
                write(out, userSession);
        }
    }

    private void startWorking() throws DaoException {
        if (!working) {
            load();
            working = true;
        }
    }

    private void endWorking() throws DaoException {
        if (working) {
            save();
            userSessions = null;
            working = false;
        }
    }

    private String generateId() {
        return DigestUtils.md5Hex(String.valueOf(Calendar.getInstance().getTime().getTime()));
    }


    @Override
    public UserSession createSession(User user) throws DaoException {
        try {
            startWorking();

            UserSession userSession = new UserSession(generateId(), user);
            userSessions.add(userSession);

            return userSession;
        } finally {
            endWorking();
        }
    }

    @Override
    public UserSession getSession(User user) throws DaoException {
        try {
            startWorking();

            for (UserSession userSession : userSessions)
                if (userSession.getUser().equals(user))
                    return userSession;
        } finally {
            endWorking();
        }
        throw new SessionNotFoundException("session not found");
    }

    @Override
    public void deleteSession(User user) throws DaoException {
        try {
            startWorking();
            userSessions.remove(getSession(user));
        } finally {
            endWorking();
        }
    }

    @Override
    public void deleteSession(UserSession userSession) throws DaoException {
        try {
            startWorking();
            userSessions.remove(userSession);
        } finally {
            endWorking();
        }
    }

    @Override
    public boolean isSessionExist(UserSession userSession) throws DaoException {
        try {
            startWorking();
            return userSessions.contains(userSession);
        } finally {
            endWorking();
        }
    }

    @Override
    public User getUser(UserSession userSession) throws DaoException {
        try {
            startWorking();

            for (UserSession sesion : userSessions)
                if (sesion.equals(userSession))
                    return sesion.getUser();
        } finally {
            endWorking();
        }
        throw new SessionNotFoundException("userSession not found");
    }

}
