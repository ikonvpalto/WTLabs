package org.kvp_bld_sck.BookDatabase.controller.usercommunication;

public enum UserInfoKind {

    USERNAME,
    EMAIL,
    PASSWORD,
    CONFIRM_PASSWORD("password confirmation"),
    ID,
    TITLE,
    AUTHOR,
    PUBLISH_DATE("publish date"),
    BOOK_LOCATION("book location"),
    FULL_NAME("full name"),
    BIRTH_DATE("birth date"),
    SEX,
    CHARACTERISTICS,
    SECURITY_LEVEL("security level");

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
