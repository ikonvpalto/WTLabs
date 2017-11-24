package org.kvp_bld_sck.BookDatabase.server.service.impl;

import org.kvp_bld_sck.BookDatabase.server.dao.DaoFactory;
import org.kvp_bld_sck.BookDatabase.server.dao.SessionDao;
import org.kvp_bld_sck.BookDatabase.server.dao.UserDao;
import org.kvp_bld_sck.BookDatabase.server.dao.exception.DaoException;
import org.kvp_bld_sck.BookDatabase.entity.Profile;
import org.kvp_bld_sck.BookDatabase.entity.UserSession;
import org.kvp_bld_sck.BookDatabase.entity.User;
import org.kvp_bld_sck.BookDatabase.server.service.exception.InvalidDataException;
import org.kvp_bld_sck.BookDatabase.server.service.exception.ServiceException;

import java.util.Map;
import java.util.Set;

public class PermissionChecker {

    
    
    private Map<User.UserGroup, Set<String>> methodsPermissions =
            Map.of(User.UserGroup.USER, Set.of("get", "get", "get"),
                   User.UserGroup.ADMINISTRATOR, Set.of("add", "get", "update", "delete"));
    private Map<User.UserGroup, Set<Profile.SecurityLevel>> profileLevelsPermissions =
            Map.of(User.UserGroup.USER, Set.of(Profile.SecurityLevel.PUBLIC),
                   User.UserGroup.ADMINISTRATOR, Set.of(Profile.SecurityLevel.PUBLIC,
                                                        Profile.SecurityLevel.SECRET,
                                                        Profile.SecurityLevel.TOP_SECRET));
    private UserDao userDao = DaoFactory.getFactory().getUserDao();
    private SessionDao sessionDao = DaoFactory.getFactory().getSessionDao();

    private PermissionChecker() {}
    private static PermissionChecker instance;
    public static PermissionChecker getChecker() {
        if (null == instance)
            instance = new PermissionChecker();
        return instance;
    }

    public boolean checkMethod(String method, User.UserGroup userGroup) {
        return methodsPermissions.get(userGroup).contains(method.toLowerCase());
    }

    public boolean checkProfile(Profile.SecurityLevel securityLevel, User.UserGroup userGroup) {
        return profileLevelsPermissions.get(userGroup).contains(securityLevel);
    }

    public boolean checkMethod(String method, UserSession userSession) throws ServiceException {
        if ((null == userSession) || (null == userSession.getId()))
            throw new InvalidDataException("userSession id not correct");
        User user;
        try {
            user = userDao.getUser(sessionDao.getUser(userSession).getId());
            return checkMethod(method, user.getUserGroup());
        } catch (DaoException e) {
            throw new ServiceException("cannot get user by userSession " + userSession.getId());
        }
    }

    public boolean checkProfile(Profile.SecurityLevel securityLevel, UserSession userSession) throws ServiceException {
        if ((null == userSession) || (null == userSession.getId()))
            throw new InvalidDataException("userSession id not correct");
        User user;
        try {
            user = userDao.getUser(sessionDao.getUser(userSession).getId());
            return checkProfile(securityLevel, user.getUserGroup());
        } catch (DaoException e) {
            throw new ServiceException("cannot get user by userSession " + userSession.getId());
        }
    }

}
