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

public class DeleteBookExecute implements Executable<String> {

    private UserDataGetter userDataGetter = UserDataGetterImpl.getInstance();

    @Override
    public String execute() throws ControllerException {
        long id = userDataGetter.getId();
        Book book = new Book(id);

        try {
            ServiceFabric.getFabric().getBookService().deleteBook(book, SessionHolder.getSession());
        } catch (ServiceException e) {
            throw new CannotExecuteCommandException(Commands.DELETE_BOOK.getFailMessage(), e);
        }

        return Commands.DELETE_BOOK.getSuccessMessage();
    }

}
