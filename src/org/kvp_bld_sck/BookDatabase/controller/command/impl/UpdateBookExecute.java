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

public class UpdateBookExecute implements Executable<String> {

    private UserDataGetter userDataGetter = UserDataGetterImpl.getInstance();

    @Override
    public String execute() throws ControllerException {
        String title = userDataGetter.getTitle();
        String author = userDataGetter.getAuthor();
        Date date = userDataGetter.getDate();
        long id = userDataGetter.getId();
        String location = userDataGetter.getLocation();

        Book book = new Book(id, title, author, date, location);

        try {
            ServiceFabric.getFabric().getBookService().updateBook(book, SessionHolder.getSession());
        } catch (ServiceException e) {
            throw new CannotExecuteCommandException(Commands.UPDATE_BOOK.getFailMessage(), e);
        }

        return Commands.UPDATE_BOOK.getSuccessMessage();
    }

}
