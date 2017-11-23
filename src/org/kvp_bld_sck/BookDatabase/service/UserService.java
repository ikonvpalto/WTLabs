package org.kvp_bld_sck.BookDatabase.service;

import org.kvp_bld_sck.BookDatabase.entity.UserSession;
import org.kvp_bld_sck.BookDatabase.entity.User;
import org.kvp_bld_sck.BookDatabase.service.exception.ServiceException;

public interface UserService {

    /**
     * @return id of new session
     * @throws ServiceException
     */
    UserSession signIn(User user) throws ServiceException;
    void signOut(UserSession userSession) throws ServiceException;
    void signUp(User user) throws ServiceException;
    User getSignedInUser(UserSession userSession) throws ServiceException;

}
