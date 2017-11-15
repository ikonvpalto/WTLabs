package org.kvp_bld_sck.BookDatabase.dao.impl.rawfiles;

import org.apache.commons.codec.digest.DigestUtils;
import org.kvp_bld_sck.BookDatabase.dao.SessionDao;
import org.kvp_bld_sck.BookDatabase.dao.exception.DaoException;
import org.kvp_bld_sck.BookDatabase.dao.exception.SessionDaoException;
import org.kvp_bld_sck.BookDatabase.dao.exception.SessionNotFoundException;
import org.kvp_bld_sck.BookDatabase.entity.Session;
import org.kvp_bld_sck.BookDatabase.entity.User;

import java.io.*;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class SessionDaoImpl implements SessionDao {

    private static final String SESSION_FILE = ".\\resources\\session.dat";

    private List<Session> sessions;
    private boolean working;

    private void write(PrintStream out, Session session) throws DaoException {
        try {
            out.println("{");
            out.println("\t" + session.getId());
            out.println("\t" + session.getUser().getId());
            out.println("}");
        } catch (Exception e) {
            throw new SessionDaoException("cannot write to user data file", e);
        }
    }

    private Session read(Scanner in) throws DaoException {
        try {
            in.nextLine();
            String id = in.nextLine().trim();
            long userId = Long.parseLong(in.nextLine().trim());
            in.nextLine();

            return new Session(id, new User(userId));
        } catch (Exception e) {
            throw new SessionDaoException("cannot read from user data file", e);
        }
    }

    private void load() throws DaoException {
        try(Scanner in = FileUtils.getIn(SESSION_FILE)) {
            sessions = new LinkedList<>();
            while (in.hasNextLine())
                sessions.add(read(in));
        }
    }

    private void save() throws DaoException {
        try(PrintStream out = FileUtils.getOut(SESSION_FILE)) {
            for (Session session : sessions)
                write(out, session);
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
            sessions = null;
            working = false;
        }
    }

    private String generateId() {
        return DigestUtils.md5Hex(String.valueOf(Calendar.getInstance().getTime().getTime()));
    }


    @Override
    public Session createSession(User user) throws DaoException {
        try {
            startWorking();

            Session session = new Session(generateId(), user);
            sessions.add(session);

            return session;
        } finally {
            endWorking();
        }
    }

    @Override
    public Session getSession(User user) throws DaoException {
        try {
            startWorking();

            for (Session session : sessions)
                if (session.getUser().equals(user))
                    return session;
        } finally {
            endWorking();
        }
        throw new SessionNotFoundException("session not found");
    }

    @Override
    public void deleteSession(User user) throws DaoException {
        try {
            startWorking();
            sessions.remove(getSession(user));
        } finally {
            endWorking();
        }
    }

    @Override
    public void deleteSession(Session session) throws DaoException {
        try {
            startWorking();
            sessions.remove(session);
        } finally {
            endWorking();
        }
    }

    @Override
    public boolean isSessionExist(Session session) throws DaoException {
        try {
            startWorking();
            return sessions.contains(session);
        } finally {
            endWorking();
        }
    }

    @Override
    public User getUser(Session session) throws DaoException {
        try {
            startWorking();

            for (Session sesion : sessions)
                if (sesion.equals(session))
                    return sesion.getUser();
        } finally {
            endWorking();
        }
        throw new SessionNotFoundException("session not found");
    }

}
