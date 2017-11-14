package org.kvp_bld_sck.BookDatabase.controller.command;

import org.kvp_bld_sck.BookDatabase.controller.exception.ControllerException;

public interface Executable<T> {

    T execute() throws ControllerException;

}
