package org.kvp_bld_sck.BookDatabase.controller.usercommunication;

import org.kvp_bld_sck.BookDatabase.controller.exception.UserDataNotValidException;

import java.util.Date;

public interface UserDataGetter {

    String getUsername() throws UserDataNotValidException;
    String getEmail() throws UserDataNotValidException;
    String getPassword() throws UserDataNotValidException;
    String getConfirmedPassword() throws UserDataNotValidException;
    long getId() throws UserDataNotValidException;
    String getTitle() throws UserDataNotValidException;
    String getAuthor() throws UserDataNotValidException;
    Date getDate() throws UserDataNotValidException;
    String getLocation() throws UserDataNotValidException;

}
