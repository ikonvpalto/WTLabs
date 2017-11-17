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
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GetBooksExecute implements Executable<String> {

    private UserDataGetter userDataGetter = UserDataGetterImpl.getInstance();

    @Override
    public String execute() throws ControllerException {
        String title = userDataGetter.getTitle(false);
        String author = userDataGetter.getAuthor(false);

        Book book = new Book(title, author);
        try {
            return ServiceFabric.getFabric().getBookService().getBooks(book, SessionHolder.getSession())
                    .stream()
                    .map(Book::toString)
                    .collect(Collectors.joining("\n"));
        } catch (ServiceException e) {
            throw new CannotExecuteCommandException(Commands.GET_BOOKS.getFailMessage(), e);
        }
    }

}
