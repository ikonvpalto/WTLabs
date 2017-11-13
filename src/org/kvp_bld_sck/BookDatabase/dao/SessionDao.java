package org.kvp_bld_sck.BookDatabase.dao;

import org.kvp_bld_sck.BookDatabase.dao.exception.SessionDaoException;
import org.kvp_bld_sck.BookDatabase.entity.Session;
import org.kvp_bld_sck.BookDatabase.entity.User;

public interface SessionDao {

    Session createSession(User user) throws SessionDaoException;
    Session getSession(User user) throws SessionDaoException;
    void deleteSession(User user) throws SessionDaoException;
    void deleteSession(Session session) throws SessionDaoException;
    boolean isSessionExist(Session session) throws SessionDaoException;
    User getUser(Session session) throws SessionDaoException;

}
