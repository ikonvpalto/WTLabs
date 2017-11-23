package org.kvp_bld_sck.BookDatabase.transport.tcp;

import org.kvp_bld_sck.BookDatabase.transport.ServerConnection;
import org.kvp_bld_sck.BookDatabase.transport.commands.Commands;
import org.kvp_bld_sck.BookDatabase.transport.commands.impl.CommandsImpl;
import org.kvp_bld_sck.BookDatabase.transport.exception.CannotExecuteException;
import org.kvp_bld_sck.BookDatabase.transport.exception.ServerException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerConnectionImpl implements ServerConnection{

    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private volatile boolean work;
    private Commands commands = new CommandsImpl();

    public ServerConnectionImpl(Socket socket, ObjectInputStream in, ObjectOutputStream out) {
        this.socket = socket;
        this.in = in;
        this.out = out;
    }
    public static ServerConnection create(Socket socket) throws IOException, ClassNotFoundException {
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        out.writeObject(null);
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        in.readObject();
        return new ServerConnectionImpl(socket, in, out);
    }

    @Override
    public void run() {
        work = true;
        while (work) {
            try {
                String method = (String) in.readObject();
                Integer paramsAmout = (Integer) in.readObject();
                Object[] params = new Object[paramsAmout];
                for (int i = 0; i < paramsAmout; i++)
                    params[i] = in.readObject();

                commands.execute(method, params);
            } catch (ClassNotFoundException e) {
                try {
                    out.writeObject(new ServerException("cannot find class", e));
                } catch (IOException e1) {
                    close();
                }
            } catch (IOException e) {
                close();
            } catch (CannotExecuteException e) {
                try {
                    out.writeObject(e);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        close();
    }

    @Override
    public void close() {
        if (work)
            work = false;
        else {
            try {
                out.close();
                in.close();
                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
