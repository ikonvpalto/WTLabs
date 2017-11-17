package org.kvp_bld_sck.BookDatabase.controller.usercommunication;

import org.kvp_bld_sck.BookDatabase.controller.exception.UserDataNotValidException;

import java.util.Date;

public interface UserDataGetter {

    String getUsername(boolean needValidate) throws UserDataNotValidException;
    String getEmail(boolean needValidate) throws UserDataNotValidException;
    String getPassword() throws UserDataNotValidException;
    String getConfirmedPassword() throws UserDataNotValidException;
    long getId() throws UserDataNotValidException;
    String getTitle(boolean needValidate) throws UserDataNotValidException;
    String getAuthor(boolean needValidate) throws UserDataNotValidException;
    Date getDate() throws UserDataNotValidException;
    String getLocation(boolean needValidate) throws UserDataNotValidException;


}
