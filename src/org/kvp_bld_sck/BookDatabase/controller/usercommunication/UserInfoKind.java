package org.kvp_bld_sck.BookDatabase.controller.usercommunication;

public enum UserInfoKind {

    USERNAME,
    EMAIL,
    PASSWORD,
    CONFIRM_PASSWORD("password confirmation"),
    ID,
    TITLE,
    AUTHOR,
    PUBLISH_DATE,
    BOOK_LOCATION;

    private String kindName;

    UserInfoKind(String kindName) {
        this.kindName = kindName;
    }

    UserInfoKind() {
        kindName = toString().toLowerCase();
    }

    public String getKindName() {
        return kindName;
    }
}
