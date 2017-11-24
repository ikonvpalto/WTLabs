package org.kvp_bld_sck.BookDatabase.client.controller.usercommunication;

import org.kvp_bld_sck.BookDatabase.client.controller.exception.UserDataNotValidException;
import org.kvp_bld_sck.BookDatabase.entity.Profile;

import java.util.Date;

public interface UserDataGetter {

    String getUsername(boolean needValidate) throws UserDataNotValidException;
    String getEmail(boolean needValidate) throws UserDataNotValidException;
    String getPassword() throws UserDataNotValidException;
    String getConfirmedPassword() throws UserDataNotValidException;
    long getId() throws UserDataNotValidException;
    String getTitle(boolean needValidate) throws UserDataNotValidException;
    String getAuthor(boolean needValidate) throws UserDataNotValidException;
    Date getPublishDate() throws UserDataNotValidException;
    String getLocation(boolean needValidate) throws UserDataNotValidException;
    String getFullName(boolean needValidate) throws UserDataNotValidException;
    Profile.Sex getSex() throws UserDataNotValidException;
    String getCharacteristics(boolean needValidate) throws UserDataNotValidException;
    Profile.SecurityLevel getSecurityLevel() throws UserDataNotValidException;
    Date getBirthDate() throws UserDataNotValidException;

}
