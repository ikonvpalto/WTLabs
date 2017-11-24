package org.kvp_bld_sck.BookDatabase.server.dao;

import org.kvp_bld_sck.BookDatabase.server.dao.exception.DaoException;
import org.kvp_bld_sck.BookDatabase.entity.UserSession;
import org.kvp_bld_sck.BookDatabase.entity.User;

public interface SessionDao {

    UserSession createSession(User user) throws DaoException;
    UserSession getSession(User user) throws DaoException;
    void deleteSession(User user) throws DaoException;
    void deleteSession(UserSession userSession) throws DaoException;
    boolean isSessionExist(UserSession userSession) throws DaoException;
    User getUser(UserSession userSession) throws DaoException;

}
