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

public class AddBookExecute implements Executable<String> {

    private UserDataGetter userDataGetter = UserDataGetterImpl.getInstance();

    @Override
    public String execute() throws ControllerException {
        String title = userDataGetter.getTitle(true);
        String author = userDataGetter.getAuthor(true);
        Date date = userDataGetter.getDate();
        String location = userDataGetter.getLocation(true);

        Book book = new Book(title, author, date, location);

        try {
            return "new book id=" + String.valueOf(ServiceFabric.getFabric().getBookService().addBook(book, SessionHolder.getSession()));
        } catch (ServiceException e) {
            throw new CannotExecuteCommandException(Commands.ADD_BOOK.getFailMessage(), e);
        }
    }

}
