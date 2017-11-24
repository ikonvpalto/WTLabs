package org.kvp_bld_sck.BookDatabase.server.service;

import org.kvp_bld_sck.BookDatabase.entity.Profile;
import org.kvp_bld_sck.BookDatabase.entity.UserSession;
import org.kvp_bld_sck.BookDatabase.server.service.exception.ServiceException;

public interface ProfileService {

    Profile getProfile(long id, UserSession userSession) throws ServiceException;
    Profile getProfile(String fullName, UserSession userSession) throws ServiceException;
    long addProfile(Profile profile, UserSession userSession) throws ServiceException;
    void updateProfile(Profile profile, UserSession userSession) throws ServiceException;
    void deleteProfile(long id, UserSession userSession) throws ServiceException;

    //TODO: implement Profile service

}
