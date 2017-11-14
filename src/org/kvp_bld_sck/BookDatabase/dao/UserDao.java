package org.kvp_bld_sck.BookDatabase.dao;

import org.kvp_bld_sck.BookDatabase.dao.exception.DaoException;
import org.kvp_bld_sck.BookDatabase.entity.User;

public interface UserDao {

    long saveUser(User user) throws DaoException;
    User getUser(String username) throws DaoException;
    User getUser(long id) throws DaoException;

}
