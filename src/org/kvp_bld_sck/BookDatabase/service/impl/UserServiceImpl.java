package org.kvp_bld_sck.BookDatabase.service.impl;

import org.kvp_bld_sck.BookDatabase.dao.DaoFactory;
import org.kvp_bld_sck.BookDatabase.dao.SessionDao;
import org.kvp_bld_sck.BookDatabase.dao.UserDao;
import org.kvp_bld_sck.BookDatabase.dao.exception.DaoException;
import org.kvp_bld_sck.BookDatabase.dao.exception.SessionDaoException;
import org.kvp_bld_sck.BookDatabase.dao.exception.UserDaoException;
import org.kvp_bld_sck.BookDatabase.entity.Session;
import org.kvp_bld_sck.BookDatabase.entity.User;
import org.kvp_bld_sck.BookDatabase.service.UserService;
import org.kvp_bld_sck.BookDatabase.service.exception.InvalidDataException;
import org.kvp_bld_sck.BookDatabase.service.exception.ServiceException;
import org.kvp_bld_sck.BookDatabase.service.exception.UserServiceException;

import java.util.List;

public class UserServiceImpl implements UserService {

    private SessionDao sessionDao;
    private UserDao userDao;

    public UserServiceImpl() {
        userDao = DaoFactory.getFactory().getUserDao();
        sessionDao = DaoFactory.getFactory().getSessionDao();
    }

    @Override
    public Session signIn(User user) throws ServiceException{
        if ((null == user)
            || (null == user.getUsername())
            || (null == user.getPassword()))
            throw new InvalidDataException("user data not correct");

        try {
            String password = user.getPassword();
            user = userDao.getUser(user.getUsername());
            if (!user.getPassword().equals(password))
                throw new InvalidDataException("wrong password");
        } catch (DaoException e) {
            throw new InvalidDataException(user.getUsername() + " not found");
        }

        try {
            if (null != sessionDao.getSession(user))
                throw new UserServiceException(user.getUsername() + " already signed in");
        } catch (DaoException ignored) {}

        try {
            return sessionDao.createSession(user);
        } catch (DaoException e) {
            throw new UserServiceException("cannot create session", e);
        }
    }

    @Override
    public void signOut(Session session) throws ServiceException{
        if ((null == session) || (null == session.getId()))
            throw new InvalidDataException("session id not correct");

        try {
            sessionDao.deleteSession(session);
        } catch (DaoException e) {
            throw new UserServiceException("cannot sign out");
        }
    }

    @Override
    public void signUp(User user) throws ServiceException{
        if ((null == user)
                || (null == user.getUsername())
                || (null == user.getPassword())
                || (null == user.getEmail()))
            throw new InvalidDataException("user data not correct");

        user.setUserGroup(User.UserGroup.USER);

        try {
            if (null != userDao.getUser(user.getUsername()))
                throw new InvalidDataException("user with username " + user.getUsername() + " already exists");
        } catch (DaoException ignored) {}

        try {
            userDao.saveUser(user);
        } catch (DaoException e) {
            throw new UserServiceException("cannot sign up user", e);
        }
    }

    @Override
    public User getSignedInUser(Session session) throws ServiceException{
        if ((null == session) || (null == session.getId()))
            throw new InvalidDataException("session id not correct");

        try {
            return sessionDao.getUser(session);
        } catch (DaoException e) {
            throw new UserServiceException("session " + session.getId() + "not found");
        }
    }

    @Override
    public void sendEmail(String text, List<User> receivers) throws ServiceException{

    }

    @Override
    public void sendEmailForAll(String text) throws ServiceException {

    }

}
