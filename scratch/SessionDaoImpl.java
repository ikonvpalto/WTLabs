package org.kvp_bld_sck.BookDatabase.dao.impl;

import org.apache.commons.codec.digest.DigestUtils;
import org.kvp_bld_sck.BookDatabase.dao.SessionDao;
import org.kvp_bld_sck.BookDatabase.dao.exception.SessionDaoException;
import org.kvp_bld_sck.BookDatabase.dao.exception.SessionDataFileNotFoundException;
import org.kvp_bld_sck.BookDatabase.dao.exception.SessionNotFoundException;
import org.kvp_bld_sck.BookDatabase.entity.User;

import java.io.*;
import java.util.Calendar;
import java.util.Scanner;

public class SessionDaoImpl implements SessionDao {

    private static final String SESSION_DATA_FILE_PATH = "./resources/sessions.dat";



    private File getSessionDataFile() throws SessionDaoException {
        File dataFile = new File(SESSION_DATA_FILE_PATH);
        if (!dataFile.exists())
            try {
                dataFile.createNewFile();
            } catch (IOException e) {
                throw new SessionDataFileNotFoundException("cannot create session data file", e);
            }
        return dataFile;
    }

    @Override
    public String saveSession(User user) throws SessionDaoException {
        if (isSessionExist(user))
            throw new SessionDaoException("user " + user.getUsername() + " already have open session");
        String sessionId = DigestUtils.md5Hex(user.getUsername() + Calendar.getInstance().getTimeInMillis());
        try (PrintStream out = new PrintStream(new FileOutputStream(getSessionDataFile(), true))) {
            out.println("{");
            out.println("\t" + user.getUsername());
            out.println("\t" + sessionId);
            out.println("}");
        } catch (FileNotFoundException ignored) {}
        return sessionId;
    }

    @Override
    public String getSession(User user) throws SessionDaoException {
        try (Scanner in = new Scanner(new FileInputStream(getSessionDataFile()))) {
            in.nextLine();
            String username = in.nextLine().trim();
            String sessionId = in.nextLine().trim();
            in.nextLine();
            if (username.equals(user.getUsername()))
                return sessionId;
        } catch (FileNotFoundException ignored) {}
        throw new SessionDaoException(user.getUsername() + "'s session not found");
    }

    @Override
    public void deleteSession(User user) throws SessionDaoException {
        long deletingSessionPos = -1;
        long lastSessionPos = 0;
        String username = "";
        String sessionId = "";

        try (RandomAccessFile file = new RandomAccessFile(getSessionDataFile(), "rw")) {
            for (long pos = file.getFilePointer(); pos < file.length(); pos = file.getFilePointer()) {
                file.readLine();
                username = file.readLine().trim();
                sessionId = file.readLine();
                file.readLine();
                if (username.equals(user.getUsername()))
                    deletingSessionPos = pos;
                lastSessionPos = pos;
            }
            if (-1 == deletingSessionPos)
                throw new SessionNotFoundException(user.getUsername() + "'s session not found");

            file.setLength(lastSessionPos);
            if (deletingSessionPos < file.length()) {
                file.seek(deletingSessionPos);
                file.writeUTF("{\n");
                file.writeUTF("\t" + username);
                file.writeUTF("\t" + sessionId);
                file.writeUTF("}\n");
            }
        } catch (IOException e) {
            throw new SessionDaoException("hz", e);
        }
    }

    @Override
    public boolean isSessionExist(String sessionId) throws SessionDaoException {
        try (Scanner in = new Scanner(new FileInputStream(getSessionDataFile()))) {
            in.nextLine();
            in.nextLine();
            String session = in.nextLine().trim();
            in.nextLine();
            if (session.equals(sessionId))
                return true;
        } catch (FileNotFoundException ignored) {}
        throw new SessionNotFoundException("session " + sessionId + " not found");
    }

    @Override
    public boolean isSessionExist(User user) throws SessionDaoException {
        try (Scanner in = new Scanner(new FileInputStream(getSessionDataFile()))) {
            in.nextLine();
            String username = in.nextLine().trim();
            in.nextLine();
            in.nextLine();
            if (username.equals(user.getUsername()))
                return true;
        } catch (FileNotFoundException ignored) {}
        throw new SessionNotFoundException(user.getUsername() + "'s session not found");
    }

}
