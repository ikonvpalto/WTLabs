package org.kvp_bld_sck.BookDatabase.dao.impl;

import org.kvp_bld_sck.BookDatabase.dao.UserDao;
import org.kvp_bld_sck.BookDatabase.dao.exception.UserDaoException;
import org.kvp_bld_sck.BookDatabase.dao.exception.UserDataFileNotFoundException;
import org.kvp_bld_sck.BookDatabase.dao.exception.UserNotFoundException;
import org.kvp_bld_sck.BookDatabase.entity.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.LinkedList;
import java.util.List;

public class UserDaoImpl implements UserDao {


    private static final String USER_FILE = ".\\resources\\user.dat";
    private static final String USER_ID_FILE = ".\\resources\\userIds.dat";

    private List<User> users;
    private RandomAccessFile file;
    private boolean working;

    private void openUserDataFile() throws UserDaoException {
        File dataFile = new File(USER_FILE);
        if (!dataFile.exists())
            try {
                dataFile.createNewFile();
            } catch (IOException e) {
                throw new UserDataFileNotFoundException("cannot create user data file", e);
            }
        try {
            file = new RandomAccessFile(dataFile, "rw");
        } catch (FileNotFoundException e) {
            throw new UserDaoException("cannot open user data file", e);
        }
    }

    private void write(User user) throws UserDaoException {
        try {
            file.writeUTF("{\n");
            file.writeUTF("\t" + user.getId() + "\n");
            file.writeUTF("\t" + user.getUsername() + "\n");
            file.writeUTF("\t" + user.getPassword() + "\n");
            file.writeUTF("\t" + user.getEmail() + "\n");
            file.writeUTF("\t" + user.getUserGroup().toString() + "\n");
            file.writeUTF("}\n");
        } catch (IOException e) {
            throw new UserDaoException("cannot write to user data file", e);
        }
    }

    private User read() throws UserDaoException {
        try {
            file.readLine();
            long id = Long.parseLong(file.readLine().trim());
            String username = file.readLine().trim();
            String password = file.readLine().trim();
            String email = file.readLine().trim();
            User.UserGroup userGroup = User.UserGroup.getByName(file.readLine().trim());
            file.readLine();

            return new User(id, username, password, email, userGroup);
        } catch (IOException e) {
            throw new UserDaoException("cannot write to user data file", e);
        }
    }

    private List<User> load() throws UserDaoException {
        try {
            users = new LinkedList<>();
            while (file.getFilePointer() < file.length())
                users.add(read());
            return users;
        } catch (IOException e) {
            throw new UserDaoException("cannot close user data file", e);
        }
    }

    private void save() throws UserDaoException {
        try {
            file.setLength(0);
            for (User user : users)
                write(user);
        } catch (IOException e) {
            throw new UserDaoException("cannot close or sizing user data file", e);
        }
    }

    private void startWorking() throws UserDaoException {
        if (!working) {
            openUserDataFile();
            load();
            working = true;
        }
    }

    private void endWorking() throws UserDaoException {
        if (working) {
            save();
            users = null;
            try {
                file.close();
            } catch (IOException e) {
                throw new UserDaoException("cannot close user data file", e);
            }
            working = false;
        }
    }

    private long generateId() throws UserDaoException {
        File idFile = new File(USER_ID_FILE);
        if (!idFile.exists())
            try {
                idFile.createNewFile();
            } catch (IOException e) {
                throw new UserDataFileNotFoundException("cannot create user ids data file", e);
            }
        try (RandomAccessFile file = new RandomAccessFile(idFile, "rw")) {
            long id = 1;
            if (file.getFilePointer() < file.length())
                id += file.readLong();
            file.seek(0);
            file.writeLong(id);
            return id;
        } catch (FileNotFoundException e) {
            throw new UserDaoException("cannot open user ids data file", e);
        } catch (IOException e) {
            throw new UserDaoException("cannot close or seek in user ids data file", e);
        }
    }


    @Override
    public long saveUser(User user) throws UserDaoException {
        try {
            startWorking();

            user.setId(generateId());
            users.add(user);
            return user.getId();
        } finally {
            endWorking();
        }
    }

    @Override
    public User getUser(String username) throws UserDaoException {
        try {
            startWorking();

            for (User user : users)
                if (user.getUsername() == username)
                    return user;
        } finally {
            endWorking();
        }
        throw new UserNotFoundException("user " + username + " not found");
    }

    @Override
    public User getUser(long id) throws UserDaoException {
        try {
            startWorking();

            for (User user : users)
                if (user.getId() == id)
                    return user;
        } finally {
            endWorking();
        }
        throw new UserNotFoundException("user with id " + id + " not found");
    }
}
