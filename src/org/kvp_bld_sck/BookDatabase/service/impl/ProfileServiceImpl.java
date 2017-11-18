package org.kvp_bld_sck.BookDatabase.service.impl;

import org.kvp_bld_sck.BookDatabase.dao.DaoFactory;
import org.kvp_bld_sck.BookDatabase.dao.ProfileDao;
import org.kvp_bld_sck.BookDatabase.dao.exception.DaoException;
import org.kvp_bld_sck.BookDatabase.entity.Profile;
import org.kvp_bld_sck.BookDatabase.entity.Session;
import org.kvp_bld_sck.BookDatabase.service.ProfileService;
import org.kvp_bld_sck.BookDatabase.service.exception.*;

public class ProfileServiceImpl implements ProfileService {

    private ProfileDao profileDao = DaoFactory.getFactory().getProfileDao();
    private PermissionChecker permissionChecker = PermissionChecker.getChecker();

    @Override
    public Profile getProfile(long id, Session session) throws ServiceException {
        if (!permissionChecker.checkMethod("get", session))
            throw new PermissionDeniedException("cannot get profile: permission denied");
        if (id < 1)
            throw new InvalidDataException("profile id not correct");

        Profile profile;
        try {
            profile = profileDao.getProfile(id);
        } catch (DaoException e) {
            throw new ProfileServiceException("cannot get profile " + id, e);
        }

        if (!permissionChecker.checkProfile(profile.getSecurityLevel(), session))
            throw new PermissionDeniedException("cannot get profile: permission denied");
        return profile;
    }

    @Override
    public Profile getProfile(String fullName, Session session) throws ServiceException {
        if (!permissionChecker.checkMethod("get", session))
            throw new PermissionDeniedException("cannot get profile: permission denied");
        if (null == fullName)
            throw new InvalidDataException("name not correct");

        Profile profile;
        try {
            profile = profileDao.getProfile(fullName);
        } catch (DaoException e) {
            throw new ProfileServiceException("cannot get profile of " + fullName, e);
        }

        if (!permissionChecker.checkProfile(profile.getSecurityLevel(), session))
            throw new PermissionDeniedException("cannot get profile: permission denied");
        return profile;
    }

    @Override
    public long addProfile(Profile profile, Session session) throws ServiceException {
        if (!PermissionChecker.getChecker().checkMethod("add", session))
            throw new PermissionDeniedException("cannot add profile: permission denied");
        if ((null == profile)
                || (null == profile.getFullName())
                || (null == profile.getSex())
                || (null == profile.getBirthDate())
                || (null == profile.getCharacteristics())
                || (null == profile.getSecurityLevel()))
            throw new InvalidDataException("profile data not correct");
        if (!PermissionChecker.getChecker().checkProfile(profile.getSecurityLevel(), session))
            throw new PermissionDeniedException("cannot add profile: permission denied");

        try {
            return profileDao.saveProfile(profile);
        } catch (DaoException e) {
            throw new ProfileServiceException("cannot add " + profile, e);
        }
    }

    @Override
    public void updateProfile(Profile profile, Session session) throws ServiceException {
        if (!PermissionChecker.getChecker().checkMethod("update", session))
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

        if (!PermissionChecker.getChecker().checkProfile(old.getSecurityLevel(), session))
            throw new PermissionDeniedException("cannot update profile: permission denied");

        try {
            profileDao.updateProfile(profile);
        } catch (DaoException e) {
            throw new ProfileServiceException("cannot update " + profile, e);
        }
    }

    @Override
    public void deleteProfile(long id, Session session) throws ServiceException {
        if (!PermissionChecker.getChecker().checkMethod("delete", session))
            throw new PermissionDeniedException("cannot delete profile: permission denied");

        if (1 > id)
            throw new InvalidDataException("profile data not correct");

        Profile old;
        try {
            old = profileDao.getProfile(id);
        } catch (DaoException e) {
            throw new ProfileServiceException("cannot delete profile " + id, e);
        }

        if (!PermissionChecker.getChecker().checkProfile(old.getSecurityLevel(), session))
            throw new PermissionDeniedException("cannot delete profile: permission denied");

        try {
            profileDao.deleteProfile(id);
        } catch (DaoException e) {
            throw new ProfileServiceException("cannot delete profile " + id, e);
        }
    }
}
