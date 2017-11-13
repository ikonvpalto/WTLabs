package org.kvp_bld_sck.BookDatabase.entity;

import java.io.Serializable;

public class User implements Serializable {

    private long id = -1;
    private String username;
    private String password;
    private String email;
    private UserGroup userGroup;

    public User() {}

    public User(String username, String password, String email, UserGroup userGroup) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.userGroup = userGroup;
    }

    public User(long id) {
        this.id = id;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(long id, String username, String password, String email, UserGroup userGroup) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.userGroup = userGroup;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserGroup getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(UserGroup userGroup) {
        this.userGroup = userGroup;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", userGroup=" + userGroup +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof User))
            return false;
        if ((id != -1) && (((User) obj).id != -1))
            return id == ((User) obj).id;
        return (username.equals(((User) obj).username))
                || (email.equals(((User) obj).email));
    }


    public enum UserGroup {
        USER,
        ADMINISTRATOR;

        public static UserGroup getByName(String groupName) {
            groupName = groupName.toLowerCase();
            for (UserGroup userGroup : values())
                if (userGroup.toString().toLowerCase().equals(groupName))
                    return userGroup;
            return USER;
        }
    }
}
