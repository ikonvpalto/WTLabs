package org.kvp_bld_sck.BookDatabase.entity;

import org.apache.commons.codec.digest.DigestUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Session {

    private String id;
    private User user;

    public Session(String id, User user) {
        this.id = id;
        this.user = user;
    }

    public Session() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public static String generateId(User user) {
        DateFormat dateFormat = new SimpleDateFormat("ddMMyyyyHHmmss");
        Date now = new Date();
        return DigestUtils.md5Hex(user.getUsername() + " " + dateFormat.format(now));
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Session)
                && (id.equals(((Session) obj).id));
    }
}
