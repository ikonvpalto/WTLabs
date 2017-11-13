package org.kvp_bld_sck.BookDatabase.controller;

public interface Controller {

    void close();
    String execute(String command);
}
