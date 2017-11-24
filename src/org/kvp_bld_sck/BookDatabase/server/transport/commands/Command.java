package org.kvp_bld_sck.BookDatabase.server.transport.commands;

import org.kvp_bld_sck.BookDatabase.server.transport.exception.CannotExecuteException;

public interface Command {
    Object execute(Object... params) throws CannotExecuteException;
}
