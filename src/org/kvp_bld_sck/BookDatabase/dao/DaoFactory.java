package org.kvp_bld_sck.BookDatabase.dao;

import org.kvp_bld_sck.BookDatabase.dao.impl.*;

public class DaoFactory {

    private UserDao userDao;
    private BookDao bookDao;
    private SessionDao sessionDao;

    private DaoFactory() {
        sessionDao = new SessionDaoImpl();
        userDao = new UserDaoImpl();
        bookDao = new BookDaoImpl();
    }
    private static DaoFactory instance;
    public static DaoFactory getFactory() {
        if (null == instance)
            instance = new DaoFactory();
        return instance;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public BookDao getBookDao() {
        return bookDao;
    }

    public SessionDao getSessionDao() {
        return sessionDao;
    }
}
