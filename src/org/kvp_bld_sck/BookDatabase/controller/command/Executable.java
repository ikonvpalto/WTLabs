package org.kvp_bld_sck.BookDatabase.controller.command;

public interface Executable<T> {

    T execute() throws Exception;

}
