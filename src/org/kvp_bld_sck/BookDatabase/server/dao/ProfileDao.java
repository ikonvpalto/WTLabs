package org.kvp_bld_sck.BookDatabase.server.dao;

import org.kvp_bld_sck.BookDatabase.server.dao.exception.DaoException;
import org.kvp_bld_sck.BookDatabase.entity.Profile;

public interface ProfileDao {

    Profile getProfile(long id) throws DaoException;
    Profile getProfile(String fullName) throws DaoException;
    long saveProfile(Profile profile) throws DaoException;
    void updateProfile(Profile profile) throws DaoException;
    void deleteProfile(long id) throws DaoException;

    //TODO: implement profile dao

}
