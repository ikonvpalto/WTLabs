package org.kvp_bld_sck.BookDatabase.controller.command.impl;

import org.kvp_bld_sck.BookDatabase.controller.command.Commands;
import org.kvp_bld_sck.BookDatabase.controller.exception.CannotExecuteCommandException;
import org.kvp_bld_sck.BookDatabase.controller.exception.ControllerException;
import org.kvp_bld_sck.BookDatabase.controller.session.SessionHolder;
import org.kvp_bld_sck.BookDatabase.controller.usercommunication.UserDataGetter;
import org.kvp_bld_sck.BookDatabase.controller.command.Executable;
import org.kvp_bld_sck.BookDatabase.controller.usercommunication.impl.UserDataGetterImpl;
import org.kvp_bld_sck.BookDatabase.entity.Book;
import org.kvp_bld_sck.BookDatabase.service.ServiceFabric;
import org.kvp_bld_sck.BookDatabase.service.exception.ServiceException;

import java.util.Date;

public class GetBooksExecute implements Executable<String> {

    private UserDataGetter userDataGetter = UserDataGetterImpl.getInstance();

    @Override
    public String execute() throws ControllerException {
        String title = userDataGetter.getTitle();
        String author = userDataGetter.getAuthor();
        Date date = userDataGetter.getDate();
        String location = userDataGetter.getLocation();

        Book book = new Book(title, author, date, location);
        try {
            ServiceFabric.getFabric().getBookService().getBooks(book, SessionHolder.getSession());
        } catch (ServiceException e) {
            throw new CannotExecuteCommandException(Commands.GET_BOOKS.getFailMessage(), e);
        }

        return Commands.GET_BOOKS.getSuccessMessage();
    }

}
