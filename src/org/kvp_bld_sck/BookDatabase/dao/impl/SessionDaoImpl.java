package org.kvp_bld_sck.BookDatabase.dao.impl;

import org.apache.commons.codec.digest.DigestUtils;
import org.kvp_bld_sck.BookDatabase.dao.SessionDao;
import org.kvp_bld_sck.BookDatabase.dao.exception.SessionDaoException;
import org.kvp_bld_sck.BookDatabase.dao.exception.SessionDataFileNotFoundException;
import org.kvp_bld_sck.BookDatabase.dao.exception.SessionNotFoundException;
import org.kvp_bld_sck.BookDatabase.entity.Session;
import org.kvp_bld_sck.BookDatabase.entity.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

public class SessionDaoImpl implements SessionDao {

    private static final String SESSION_FILE = ".\\resources\\session.dat";

    private List<Session> sessions;
    private RandomAccessFile file;
    private boolean working;

    private void openSessionDataFile() throws SessionDaoException {
        File dataFile = new File(SESSION_FILE);
        if (!dataFile.exists())
            try {
                dataFile.createNewFile();
            } catch (IOException e) {
                throw new SessionDataFileNotFoundException("cannot create session data file", e);
            }
        try {
            file = new RandomAccessFile(dataFile, "rw");
        } catch (FileNotFoundException e) {
            throw new SessionDaoException("cannot open session data file", e);
        }
    }

    private void write(Session session) throws SessionDaoException {
        try {
            file.writeUTF("{\n");
            file.writeUTF("\t" + session.getId() + "\n");
            file.writeUTF("\t" + session.getUser().getId() + "\n");
            file.writeUTF("}\n");
        } catch (IOException e) {
            throw new SessionDaoException("cannot write to session data file", e);
        }
    }

    private Session read() throws SessionDaoException {
        try {
            file.readLine();
            String id = file.readLine().trim();
            long userId = Long.parseLong(file.readLine().trim());
            file.readLine();

            return new Session(id, new User(userId));
        } catch (IOException e) {
            throw new SessionDaoException("cannot write to session data file", e);
        }
    }

    private List<Session> load() throws SessionDaoException {
        try {
            sessions = new LinkedList<>();
            while (file.getFilePointer() < file.length())
                sessions.add(read());
            return sessions;
        } catch (IOException e) {
            throw new SessionDaoException("cannot close session data file", e);
        }
    }

    private void save() throws SessionDaoException {
        try {
            file.setLength(0);
            for (Session session : sessions)
                write(session);
        } catch (IOException e) {
            throw new SessionDaoException("cannot close or sizing session data file", e);
        }
    }

    private void startWorking() throws SessionDaoException {
        if (!working) {
            openSessionDataFile();
            load();
            working = true;
        }
    }

    private void endWorking() throws SessionDaoException {
        if (working) {
            save();
            sessions = null;
            try {
                file.close();
            } catch (IOException e) {
                throw new SessionDaoException("cannot close session data file", e);
            }
            working = false;
        }
    }

    private String generateId() {
        return DigestUtils.md5Hex(String.valueOf(Calendar.getInstance().getTime().getTime()));
    }


    @Override
    public Session createSession(User user) throws SessionDaoException {
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
    public Session getSession(User user) throws SessionDaoException {
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
    public void deleteSession(User user) throws SessionDaoException {
        try {
            startWorking();
            sessions.remove(getSession(user));
        } finally {
            endWorking();
        }
    }

    @Override
    public void deleteSession(Session session) throws SessionDaoException {
        try {
            startWorking();
            sessions.remove(session);
        } finally {
            endWorking();
        }
    }

    @Override
    public boolean isSessionExist(Session session) throws SessionDaoException {
        try {
            startWorking();
            return sessions.contains(session);
        } finally {
            endWorking();
        }
    }

    @Override
    public User getUser(Session session) throws SessionDaoException {
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
