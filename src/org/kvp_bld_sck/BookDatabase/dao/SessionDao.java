package org.kvp_bld_sck.BookDatabase.dao;

import org.kvp_bld_sck.BookDatabase.dao.exception.DaoException;
import org.kvp_bld_sck.BookDatabase.entity.Session;
import org.kvp_bld_sck.BookDatabase.entity.User;

public interface SessionDao {

    Session createSession(User user) throws DaoException;
    Session getSession(User user) throws DaoException;
    void deleteSession(User user) throws DaoException;
    void deleteSession(Session session) throws DaoException;
    boolean isSessionExist(Session session) throws DaoException;
    User getUser(Session session) throws DaoException;

}
