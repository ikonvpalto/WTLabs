package org.kvp_bld_sck.BookDatabase.controller.command.impl;

import org.kvp_bld_sck.BookDatabase.controller.command.Commands;
import org.kvp_bld_sck.BookDatabase.controller.command.Executable;

import java.util.Arrays;
import java.util.stream.Collectors;

public class HelpExecute implements Executable {

    @Override
    public String execute() {
        return Arrays.stream(Commands.values()).map(Commands::getName).collect(Collectors.joining("\n"));
    }
}
