package org.kvp_bld_sck.BookDatabase.controller.usercommunication.impl;

import org.kvp_bld_sck.BookDatabase.controller.exception.UserDataNotValidException;
import org.kvp_bld_sck.BookDatabase.controller.usercommunication.UserDataGetter;
import org.kvp_bld_sck.BookDatabase.controller.usercommunication.UserInfoKind;
import org.kvp_bld_sck.BookDatabase.view.View;
import org.kvp_bld_sck.BookDatabase.view.ViewFabric;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class UserDataGetterImpl implements UserDataGetter {

    private static final Pattern NAME = Pattern.compile("^\\w{3,}$");
    private static final Pattern EMAIL = Pattern.compile("^[^@\n']+@[^@\n']+$");
    private static final Pattern PASSWORD = Pattern.compile("^\\w{3,}$");
    private static final Pattern TITLE = Pattern.compile("^[^'\n]+$");
    private static final Pattern AUTHOR = Pattern.compile("^[^'\n]+( [^'\n]+( [^'\n]+)?)?$");
    private static final Pattern DATE = Pattern.compile("^\\d\\d[/ .\\\\,]\\d\\d[/ .,\\\\]\\d\\d\\d\\d$");
    private static final Pattern LOCATION = Pattern.compile("^[^'\n]+$");


    private UserDataGetterImpl() {}
    private static UserDataGetter instance;
    public static UserDataGetter getInstance() {
        if (null == instance)
            instance = new UserDataGetterImpl();
        return instance;
    }

    @Override
    public String getUsername(boolean needValidate) throws UserDataNotValidException {
        View view = ViewFabric.getFabric().getView();

        String username = view.getAdditionalInfo(UserInfoKind.USERNAME.getKindName());
        if (needValidate && !NAME.matcher(username).matches())
            throw new UserDataNotValidException(UserInfoKind.USERNAME.getKindName() + " not valid");
        return username;
    }

    @Override
    public String getEmail(boolean needValidate) throws UserDataNotValidException {
        View view = ViewFabric.getFabric().getView();

        String email = view.getAdditionalInfo(UserInfoKind.EMAIL.getKindName());
        if (needValidate && !EMAIL.matcher(email).matches())
            throw new UserDataNotValidException(UserInfoKind.EMAIL.getKindName() + " not valid");
        return email;
    }

    @Override
    public String getPassword() throws UserDataNotValidException {
        View view = ViewFabric.getFabric().getView();

        String password = view.getAdditionalInfo(UserInfoKind.PASSWORD.getKindName());
        if (!PASSWORD.matcher(password).matches())
            throw new UserDataNotValidException(UserInfoKind.PASSWORD.getKindName() + " not valid");
        return password;
    }

    @Override
    public String getConfirmedPassword() throws UserDataNotValidException {
        View view = ViewFabric.getFabric().getView();

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
        View view = ViewFabric.getFabric().getView();

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
        View view = ViewFabric.getFabric().getView();

        String title = view.getAdditionalInfo(UserInfoKind.TITLE.getKindName());
        if (needValidate && !TITLE.matcher(title).matches())
            throw new UserDataNotValidException(UserInfoKind.TITLE.getKindName() + " not valid");
        return title;
    }

    @Override
    public String getAuthor(boolean needValidate) throws UserDataNotValidException {
        View view = ViewFabric.getFabric().getView();

        String author = view.getAdditionalInfo(UserInfoKind.AUTHOR.getKindName());
        if (needValidate && !AUTHOR.matcher(author).matches())
            throw new UserDataNotValidException(UserInfoKind.AUTHOR.getKindName() + " not valid");
        return author;
    }

    @Override
    public Date getDate() throws UserDataNotValidException {
        View view = ViewFabric.getFabric().getView();

        String date = view.getAdditionalInfo(UserInfoKind.PUBLISH_DATE.getKindName());
        if (!DATE.matcher(date).matches())
            throw new UserDataNotValidException(UserInfoKind.PUBLISH_DATE.getKindName() + " not valid");
        Date parsedDate;
        for (char delimiter : new char[] {',', '.', '\\', '/', ' '}) {
            try {
                DateFormat format = new SimpleDateFormat("dd" + delimiter +"MM" + delimiter + "yyyy");
                parsedDate = format.parse(date);
                return parsedDate;
            } catch (ParseException ignored) {}
        }

        throw new UserDataNotValidException(UserInfoKind.PUBLISH_DATE.getKindName() + " not valid");
    }

    @Override
    public String getLocation(boolean needValidate) throws UserDataNotValidException {
        View view = ViewFabric.getFabric().getView();

        String location = view.getAdditionalInfo(UserInfoKind.BOOK_LOCATION.getKindName());
        if (needValidate && !LOCATION.matcher(location).matches())
            throw new UserDataNotValidException(UserInfoKind.BOOK_LOCATION.getKindName() + " not valid");
        return location;
    }

}
