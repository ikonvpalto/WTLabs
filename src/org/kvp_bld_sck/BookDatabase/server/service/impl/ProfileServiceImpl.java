package org.kvp_bld_sck.BookDatabase.server.service.impl;

import org.kvp_bld_sck.BookDatabase.server.dao.DaoFactory;
import org.kvp_bld_sck.BookDatabase.server.dao.ProfileDao;
import org.kvp_bld_sck.BookDatabase.server.dao.exception.DaoException;
import org.kvp_bld_sck.BookDatabase.entity.Profile;
import org.kvp_bld_sck.BookDatabase.entity.UserSession;
import org.kvp_bld_sck.BookDatabase.server.service.ProfileService;
import org.kvp_bld_sck.BookDatabase.server.service.exception.*;

public class ProfileServiceImpl implements ProfileService {

    private ProfileDao profileDao = DaoFactory.getFactory().getProfileDao();
    private PermissionChecker permissionChecker = PermissionChecker.getChecker();

    @Override
    public Profile getProfile(long id, UserSession userSession) throws ServiceException {
        if (!permissionChecker.checkMethod("get", userSession))
            throw new PermissionDeniedException("cannot get profile: permission denied");
        if (id < 1)
            throw new InvalidDataException("profile id not correct");

        Profile profile;
        try {
            profile = profileDao.getProfile(id);
        } catch (DaoException e) {
            throw new ProfileServiceException("cannot get profile " + id, e);
        }

        if (!permissionChecker.checkProfile(profile.getSecurityLevel(), userSession))
            throw new PermissionDeniedException("cannot get profile: permission denied");
        return profile;
    }

    @Override
    public Profile getProfile(String fullName, UserSession userSession) throws ServiceException {
        if (!permissionChecker.checkMethod("get", userSession))
            throw new PermissionDeniedException("cannot get profile: permission denied");
        if (null == fullName)
            throw new InvalidDataException("name not correct");

        Profile profile;
        try {
            profile = profileDao.getProfile(fullName);
        } catch (DaoException e) {
            throw new ProfileServiceException("cannot get profile of " + fullName, e);
        }

        if (!permissionChecker.checkProfile(profile.getSecurityLevel(), userSession))
            throw new PermissionDeniedException("cannot get profile: permission denied");
        return profile;
    }

    @Override
    public long addProfile(Profile profile, UserSession userSession) throws ServiceException {
        if (!PermissionChecker.getChecker().checkMethod("add", userSession))
            throw new PermissionDeniedException("cannot add profile: permission denied");
        if ((null == profile)
                || (null == profile.getFullName())
                || (null == profile.getSex())
                || (null == profile.getBirthDate())
                || (null == profile.getCharacteristics())
                || (null == profile.getSecurityLevel()))
            throw new InvalidDataException("profile data not correct");
        if (!PermissionChecker.getChecker().checkProfile(profile.getSecurityLevel(), userSession))
            throw new PermissionDeniedException("cannot add profile: permission denied");

        try {
            return profileDao.saveProfile(profile);
        } catch (DaoException e) {
            throw new ProfileServiceException("cannot add " + profile, e);
        }
    }

    @Override
    public void updateProfile(Profile profile, UserSession userSession) throws ServiceException {
        if (!PermissionChecker.getChecker().checkMethod("update", userSession))
            throw new PermissionDeniedException("cannot update profile: permission denied");

        if ((null == profile)
                || (null == profile.getFullName())
                || (null == profile.getSex())
                || (null == profile.getBirthDate())
                || (null == profile.getCharacteristics())
                || (null == profile.getSecurityLevel())
                || (1 > profile.getId()))
            throw new InvalidDataException("profile data not correct");

        Profile old;
        try {
            old = profileDao.getProfile(profile.getId());
        } catch (DaoException e) {
            throw new ProfileServiceException("cannot update " + profile, e);
        }

        if (!PermissionChecker.getChecker().checkProfile(old.getSecurityLevel(), userSession))
            throw new PermissionDeniedException("cannot update profile: permission denied");

        try {
            profileDao.updateProfile(profile);
        } catch (DaoException e) {
            throw new ProfileServiceException("cannot update " + profile, e);
        }
    }

    @Override
    public void deleteProfile(long id, UserSession userSession) throws ServiceException {
        if (!PermissionChecker.getChecker().checkMethod("delete", userSession))
            throw new PermissionDeniedException("cannot delete profile: permission denied");

        if (1 > id)
            throw new InvalidDataException("profile data not correct");

        Profile old;
        try {
            old = profileDao.getProfile(id);
        } catch (DaoException e) {
            throw new ProfileServiceException("cannot delete profile " + id, e);
        }

        if (!PermissionChecker.getChecker().checkProfile(old.getSecurityLevel(), userSession))
            throw new PermissionDeniedException("cannot delete profile: permission denied");

        try {
            profileDao.deleteProfile(id);
        } catch (DaoException e) {
            throw new ProfileServiceException("cannot delete profile " + id, e);
        }
    }
}
