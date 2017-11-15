package org.kvp_bld_sck.BookDatabase.dao.impl.rawfiles;

import org.kvp_bld_sck.BookDatabase.dao.UserDao;
import org.kvp_bld_sck.BookDatabase.dao.exception.DaoException;
import org.kvp_bld_sck.BookDatabase.dao.exception.UserDaoException;
import org.kvp_bld_sck.BookDatabase.dao.exception.UserNotFoundException;
import org.kvp_bld_sck.BookDatabase.entity.User;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class UserDaoImpl implements UserDao {


    private static final String USER_FILE = ".\\resources\\user.dat";
    private static final String USER_ID_FILE = ".\\resources\\userIds.dat";

    private List<User> users;
    private boolean working;

    private void write(PrintStream out, User user) throws DaoException {
        try {
            out.println("{");
            out.println("\t" + user.getId());
            out.println("\t" + user.getUsername());
            out.println("\t" + user.getPassword());
            out.println("\t" + user.getEmail());
            out.println("\t" + user.getUserGroup().toString());
            out.println("}");
        } catch (Exception e) {
            throw new UserDaoException("cannot write to user data file", e);
        }
    }

    private User read(Scanner in) throws DaoException {
        try {
            in.nextLine();
            long id = Long.parseLong(in.nextLine().trim());
            String username = in.nextLine().trim();
            String password = in.nextLine().trim();
            String email = in.nextLine().trim();
            User.UserGroup userGroup = User.UserGroup.getByName(in.nextLine().trim());
            in.nextLine();

            return new User(id, username, password, email, userGroup);
        } catch (Exception e) {
            throw new UserDaoException("cannot read from user data file", e);
        }
    }

    private void load() throws DaoException {
        try(Scanner in = FileUtils.getIn(USER_FILE)) {
            users = new LinkedList<>();
            while (in.hasNextLine())
                users.add(read(in));
        }
    }

    private void save() throws DaoException {
        try(PrintStream out = FileUtils.getOut(USER_FILE)) {
            for (User user : users)
                write(out, user);
//        } catch (IOException e) {
//            throw new UserDaoException("cannot close or sizing user data file", e);
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
            users = null;
            working = false;
        }
    }

    private long generateId() throws DaoException {
        long id = 1;
        try (Scanner in = FileUtils.getIn(USER_ID_FILE)) {
            if (in.hasNextLong())
                id += in.nextLong();
        }
        try (PrintStream out = FileUtils.getOut(USER_ID_FILE)) {
            out.println(id);
        }
        return id;
    }


    @Override
    public long saveUser(User user) throws DaoException {
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
    public User getUser(String username) throws DaoException {
        try {
            startWorking();

            for (User user : users)
                if (user.getUsername().equals(username))
                    return user;
        } finally {
            endWorking();
        }
        throw new UserNotFoundException("user " + username + " not found");
    }

    @Override
    public User getUser(long id) throws DaoException {
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
