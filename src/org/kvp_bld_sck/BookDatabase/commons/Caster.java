package org.kvp_bld_sck.BookDatabase.commons;

import org.kvp_bld_sck.BookDatabase.entity.Profile;
import org.kvp_bld_sck.BookDatabase.entity.User;

import java.util.Date;

public class Caster {

    protected static Date stringToDate(String src) {
        return new Date(Long.parseLong(src));
    }

    protected static String dateToString(Date src) {
        return String.valueOf(src.getTime());
    }

    protected static Long stringToLong(String src) {
        return Long.parseLong(src);
    }

    protected static String longToString(Long src) {
        return String.valueOf(src);
    }

    protected static User.UserGroup stringToUserGroup(String src) {
        return User.UserGroup.valueOf(src);
    }

    protected static Profile.Sex stringToSex(String src) {
        return Profile.Sex.valueOf(src);
    }

    protected static Profile.SecurityLevel stringToSecurityLevel(String src) {
        return Profile.SecurityLevel.valueOf(src);
    }

    @SuppressWarnings("unchecked")
    public static <R> R cast(Object src, Class<R> destClass) {
        if (Date.class.equals(destClass)) {
            if (src instanceof String)
                return (R) stringToDate((String) src);
        }

        if (Long.class.equals(destClass)) {
            if (src instanceof String)
                return (R) stringToLong((String) src);
        }



        if (String.class.equals(destClass)) {
            if (src instanceof Long)
                return (R) longToString((Long) src);
            if (src instanceof Date)
                return (R) dateToString((Date) src);
            return (R) src.toString();
        }

        return (R) src;
    }

}
