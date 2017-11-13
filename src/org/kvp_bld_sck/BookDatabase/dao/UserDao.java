package org.kvp_bld_sck.BookDatabase.dao;

import org.kvp_bld_sck.BookDatabase.dao.exception.UserDaoException;
import org.kvp_bld_sck.BookDatabase.entity.User;

public interface UserDao {

    long saveUser(User user) throws UserDaoException;
    User getUser(String username) throws UserDaoException;
    User getUser(long id) throws UserDaoException;

}
