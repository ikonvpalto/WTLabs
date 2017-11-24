package org.kvp_bld_sck.BookDatabase.client.controller.command;

import org.kvp_bld_sck.BookDatabase.client.controller.exception.ControllerException;

public interface Executable<T> {

    T execute() throws ControllerException;

}
