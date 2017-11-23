package org.kvp_bld_sck.BookDatabase.transport.commands.impl;

import org.kvp_bld_sck.BookDatabase.transport.commands.Command;
import org.kvp_bld_sck.BookDatabase.transport.commands.Commands;
import org.kvp_bld_sck.BookDatabase.transport.exception.CannotExecuteException;

import java.util.Map;

public class CommandsImpl implements Commands {
    private Map<String, Command> commands = Map.of(
            "addProfile", new AddProfileCommand(),
            "deleteProfile", new DeleteProfileCommand(),
            "getManProfile", new GetManProfileCommand(),
            "getProfileById", new GetProfileByIdCommand(),
            "signIn", new SignInCommand(),
            "signUp", new SignUpCommand(),
            "signOut", new SignOutCommand(),
            "updateProfile", new UpdateProfileCommand()
    );

    @Override
    public synchronized Object execute(String command, Object... params) throws CannotExecuteException {
        return commands.get(command).execute(params);
    }
}
