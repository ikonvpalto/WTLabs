package org.kvp_bld_sck.BookDatabase.client.controller.usercommunication.impl;

import org.kvp_bld_sck.BookDatabase.client.controller.exception.UserDataNotValidException;
import org.kvp_bld_sck.BookDatabase.client.controller.usercommunication.UserDataGetter;
import org.kvp_bld_sck.BookDatabase.client.controller.usercommunication.UserInfoKind;
import org.kvp_bld_sck.BookDatabase.entity.Profile;
import org.kvp_bld_sck.BookDatabase.client.view.View;
import org.kvp_bld_sck.BookDatabase.client.view.ViewFabric;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class UserDataGetterImpl implements UserDataGetter {

    private static final Pattern USERNAME = Pattern.compile("^\\w{3,}$");
    private static final Pattern EMAIL = Pattern.compile("^[^@\n']+@[^@\n']+$");
    private static final Pattern PASSWORD = Pattern.compile("^\\w{3,}$");
    private static final Pattern TITLE = Pattern.compile("^[^'\n]+$");
    private static final Pattern NAME = Pattern.compile("^[^'\n]+( [^'\n]+( [^'\n]+)?)?$");
    private static final Pattern DATE = Pattern.compile("^\\d\\d[/ .\\\\,]\\d\\d[/ .,\\\\]\\d\\d\\d\\d$");
    private static final Pattern LOCATION = Pattern.compile("^[^'\n]+$");
    private static final Pattern SEX
            = Pattern.compile(Arrays.stream(Profile.Sex.values())
                     .map(Profile.Sex::toString)
                     .map(String::toLowerCase)
                     .collect(Collectors.joining(")|(", "^(", ")$")), Pattern.CASE_INSENSITIVE);
    private static final Pattern SECURITY_LEVEL
            = Pattern.compile(Arrays.stream(Profile.SecurityLevel.values())
                     .map(Profile.SecurityLevel::toString)
                     .map(String::toLowerCase)
                     .collect(Collectors.joining(")|(", "^(", ")$")), Pattern.CASE_INSENSITIVE);
    private static final Pattern CHARACTERISTICS = Pattern.compile("^[\\w\\s,.:-]$");


    private View view;


    private UserDataGetterImpl() {
        view = ViewFabric.getFabric().getView();
    }
    private static UserDataGetter instance;
    public static UserDataGetter getInstance() {
        if (null == instance)
            instance = new UserDataGetterImpl();
        return instance;
    }


    private Date parseDate(String date) throws UserDataNotValidException {
        Date parsedDate;
        for (char delimiter : new char[] {',', '.', '\\', '/', ' '}) {
            try {
                DateFormat format = new SimpleDateFormat("dd" + delimiter +"MM" + delimiter + "yyyy");
                parsedDate = format.parse(date);
                return parsedDate;
            } catch (ParseException ignored) {}
        }

        throw new UserDataNotValidException("date not valid");
    }

    @Override
    public String getUsername(boolean needValidate) throws UserDataNotValidException {
        String username = view.getAdditionalInfo(UserInfoKind.USERNAME.getKindName());
        if (needValidate && !USERNAME.matcher(username).matches())
            throw new UserDataNotValidException(UserInfoKind.USERNAME.getKindName() + " not valid");
        return username;
    }

    @Override
    public String getEmail(boolean needValidate) throws UserDataNotValidException {
        String email = view.getAdditionalInfo(UserInfoKind.EMAIL.getKindName());
        if (needValidate && !EMAIL.matcher(email).matches())
            throw new UserDataNotValidException(UserInfoKind.EMAIL.getKindName() + " not valid");
        return email;
    }

    @Override
    public String getPassword() throws UserDataNotValidException {
        String password = view.getAdditionalInfo(UserInfoKind.PASSWORD.getKindName());
        if (!PASSWORD.matcher(password).matches())
            throw new UserDataNotValidException(UserInfoKind.PASSWORD.getKindName() + " not valid");
        return password;
    }

    @Override
    public String getConfirmedPassword() throws UserDataNotValidException {
        String password = view.getAdditionalInfo(UserInfoKind.PASSWORD.getKindName());
        if (!PASSWORD.matcher(password).matches())
            throw new UserDataNotValidException(UserInfoKind.PASSWORD.getKindName() + " not valid");

        String passwordConfirmation = view.getAdditionalInfo(UserInfoKind.CONFIRM_PASSWORD.getKindName());
        if (!password.equals(passwordConfirmation))
            throw new UserDataNotValidException(UserInfoKind.PASSWORD.getKindName() +
                                       " and " +
                                       UserInfoKind.CONFIRM_PASSWORD.getKindName() +
                                       " are not equals");

        return password;
    }

    @Override
    public long getId() throws UserDataNotValidException {
        String id = view.getAdditionalInfo(UserInfoKind.ID.getKindName());
        try {
            long parsedId = Integer.parseInt(id);
            if (parsedId < 1)
                throw new UserDataNotValidException(UserInfoKind.ID.getKindName() + " not valid");
            return parsedId;
        } catch (NumberFormatException e) {
            throw new UserDataNotValidException(UserInfoKind.ID.getKindName() + " not valid", e);
        }
    }

    @Override
    public String getTitle(boolean needValidate) throws UserDataNotValidException {
        String title = view.getAdditionalInfo(UserInfoKind.TITLE.getKindName());
        if (needValidate && !TITLE.matcher(title).matches())
            throw new UserDataNotValidException(UserInfoKind.TITLE.getKindName() + " not valid");
        return title;
    }

    @Override
    public String getAuthor(boolean needValidate) throws UserDataNotValidException {
        String author = view.getAdditionalInfo(UserInfoKind.AUTHOR.getKindName());
        if (needValidate && !NAME.matcher(author).matches())
            throw new UserDataNotValidException(UserInfoKind.AUTHOR.getKindName() + " not valid");
        return author;
    }

    @Override
    public Date getPublishDate() throws UserDataNotValidException {
        String date = view.getAdditionalInfo(UserInfoKind.PUBLISH_DATE.getKindName());
        if (!DATE.matcher(date).matches())
            throw new UserDataNotValidException(UserInfoKind.PUBLISH_DATE.getKindName() + " not valid");
        return parseDate(date);
    }

    @Override
    public String getLocation(boolean needValidate) throws UserDataNotValidException {
        String location = view.getAdditionalInfo(UserInfoKind.BOOK_LOCATION.getKindName());
        if (needValidate && !LOCATION.matcher(location).matches())
            throw new UserDataNotValidException(UserInfoKind.BOOK_LOCATION.getKindName() + " not valid");
        return location;
    }

    @Override
    public String getFullName(boolean needValidate) throws UserDataNotValidException {
        String fullName = view.getAdditionalInfo(UserInfoKind.FULL_NAME.getKindName());
        if (needValidate && !NAME.matcher(fullName).matches())
            throw new UserDataNotValidException(UserInfoKind.FULL_NAME.getKindName() + " not valid");
        return fullName;
    }

    @Override
    public Profile.Sex getSex() throws UserDataNotValidException {
        String sexName = view.getAdditionalInfo(UserInfoKind.SEX.getKindName());
        if (!SEX.matcher(sexName).matches())
            throw new UserDataNotValidException(UserInfoKind.SEX.getKindName() + " not valid");
        return Profile.Sex.valueOf(sexName);
    }

    @Override
    public String getCharacteristics(boolean needValidate) throws UserDataNotValidException {
        String characteristics = view.getAdditionalInfo(UserInfoKind.CHARACTERISTICS.getKindName());
        if (needValidate && !CHARACTERISTICS.matcher(characteristics).matches())
            throw new UserDataNotValidException(UserInfoKind.CHARACTERISTICS.getKindName() + " not valid");
        return characteristics;
    }

    @Override
    public Profile.SecurityLevel getSecurityLevel() throws UserDataNotValidException {
        String securityLevelName = view.getAdditionalInfo(UserInfoKind.SECURITY_LEVEL.getKindName());
        if (!SECURITY_LEVEL.matcher(securityLevelName).matches())
            throw new UserDataNotValidException(UserInfoKind.SECURITY_LEVEL.getKindName() + " not valid");
        return Profile.SecurityLevel.valueOf(securityLevelName);
    }

    @Override
    public Date getBirthDate() throws UserDataNotValidException {
        String date = view.getAdditionalInfo(UserInfoKind.BIRTH_DATE.getKindName());
        if (!DATE.matcher(date).matches())
            throw new UserDataNotValidException(UserInfoKind.BIRTH_DATE.getKindName() + " not valid");
        return parseDate(date);
    }

}
