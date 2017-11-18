package org.kvp_bld_sck.BookDatabase.service;

import org.kvp_bld_sck.BookDatabase.entity.Profile;
import org.kvp_bld_sck.BookDatabase.entity.Session;
import org.kvp_bld_sck.BookDatabase.service.exception.ServiceException;

public interface ProfileService {

    Profile getProfile(long id, Session session) throws ServiceException;
    Profile getProfile(String fullName, Session session) throws ServiceException;
    long saveProfile(Profile profile, Session session) throws ServiceException;
    void updateProfile(Profile profile, Session session) throws ServiceException;
    void deleteProfile(long id, Session session) throws ServiceException;

    //TODO: implement Profile service

}
