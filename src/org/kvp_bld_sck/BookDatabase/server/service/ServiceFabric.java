package org.kvp_bld_sck.BookDatabase.server.service;

import org.kvp_bld_sck.BookDatabase.server.service.impl.BookServiceImpl;
import org.kvp_bld_sck.BookDatabase.server.service.impl.UserServiceImpl;

public class ServiceFabric {

    private UserService userService;
    private BookService bookService;
    private ProfileService profileService;

    private ServiceFabric() {
        userService = new UserServiceImpl();
        bookService = new BookServiceImpl();
    }
    private static ServiceFabric instance;
    public static ServiceFabric getFabric() {
        if (null == instance)
            instance = new ServiceFabric();
        return instance;
    }

    public UserService getUserService() {
        return userService;
    }

    public BookService getBookService() {
        return bookService;
    }

    public ProfileService getProfileService() {
        return profileService;
    }
}
