package org.kvp_bld_sck.BookDatabase.controller.command;

import org.kvp_bld_sck.BookDatabase.controller.command.impl.*;
import org.kvp_bld_sck.BookDatabase.controller.exception.CommandNotFoundException;
import org.kvp_bld_sck.BookDatabase.controller.exception.ControllerException;

public enum Commands implements Command<String> {

    HELP(new HelpExecute(), "help", "", "help fail"),
    EXIT(new ExitExecute(), "exit", "goodbye", "exit fail"),
    SIGN_IN(new SignInExecute(), "sign in", "hello", "sign in fail"),
    SIGN_OUT(new SignOutExecute(), "sign out", "goodbye", "sign out fail"),
    SIGN_UP(new SignUpExecute(), "sign up", "welcome", "sign out fail"),
    GET_BOOK(new GetBookExecute(), "get book"),
    GET_BOOKS(new GetBooksExecute(), "get books"),
    ADD_BOOK(new AddBookExecute(), "add book"),
    UPDATE_BOOK(new UpdateBookExecute(), "update book"),
    DELETE_BOOK(new DeleteBookExecute(), "delete book");

    private Executable<String> command;
    private String commandName;
    private String description;
    private String successMessage;
    private String failMessage;

    Commands(Executable<String> command, String commandName, String description) {
        this(command, commandName, description, commandName + " success", commandName + " fail");
    }

    Commands(Executable<String> command, String commandName) {
        this(command, commandName, commandName, commandName + " success", commandName + " fail");
    }

    Commands(Executable<String> command, String commandName, String description, String successMessage, String failMessage) {
        this.command = command;
        this.commandName = commandName;
        this.description = description;
        this.successMessage = successMessage;
        this.failMessage = failMessage;
    }

    Commands(Executable<String> command, String commandName, String successMessage, String failMessage) {
        this(command, commandName, commandName, successMessage, failMessage);
    }


    public String getName() {
        return commandName;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String getSuccessMessage() {
        return successMessage;
    }

    @Override
    public String getFailMessage() {
        return failMessage;
    }


    @Override
    public String execute() throws ControllerException {
        try {
            return command.execute();
        } catch (Exception e) {
            if (e instanceof ControllerException)
                throw (ControllerException) e;
            return e.getMessage();
        }
    }


    public static Command<String> getCommand(String commandName) throws ControllerException {
        for (Command<String> command : values())
            if (commandName.equals(command.getName()))
                return command;
        throw new CommandNotFoundException("no such command");
    }

    public static String execute(String commandName) throws ControllerException {
        for (Command<String> command : values())
            if (commandName.equals(command.getName()))
                return command.execute();

        throw new CommandNotFoundException("no such command");
    }
}
