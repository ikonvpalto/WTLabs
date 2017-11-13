package org.kvp_bld_sck.BookDatabase.controller.command;

public interface Command<T> extends Executable<T> {

    String getName();
    String getDescription();
    String getSuccessMessage();
    String getFailMessage();

}
