package org.kvp_bld_sck.BookDatabase.transport.commands;

import org.kvp_bld_sck.BookDatabase.transport.exception.CannotExecuteException;

public interface Command {
    Object execute(Object... params) throws CannotExecuteException;
}
