package org.kvp_bld_sck.BookDatabase.transport.commands;

import org.kvp_bld_sck.BookDatabase.transport.exception.CannotExecuteException;

public interface Commands {
    Object execute(String command, Object... params) throws CannotExecuteException;
}
