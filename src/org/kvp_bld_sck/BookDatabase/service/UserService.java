package org.kvp_bld_sck.BookDatabase.service;

import org.kvp_bld_sck.BookDatabase.entity.Session;
import org.kvp_bld_sck.BookDatabase.entity.User;
import org.kvp_bld_sck.BookDatabase.service.exception.ServiceException;

import java.util.List;

public interface UserService {

    /**
     * @return id of new session
     * @throws ServiceException
     */
    Session signIn(User user) throws ServiceException;
    void signOut(Session session) throws ServiceException;
    void signUp(User user) throws ServiceException;
    User getSignedInUser(Session session) throws ServiceException;

}
