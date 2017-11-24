package org.kvp_bld_sck.BookDatabase.client.transport.tcp;

import org.kvp_bld_sck.BookDatabase.client.transport.Client;
import org.kvp_bld_sck.BookDatabase.client.transport.exception.CannotConnectToServerException;
import org.kvp_bld_sck.BookDatabase.client.transport.exception.ClientException;
import org.kvp_bld_sck.BookDatabase.client.transport.exception.TransportException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientImpl implements Client {

    public static final String DEFAULT_SERVER_HOST = "localhost";
    public static final int DEFAULT_SERVER_PORT = 7474;

    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    public ClientImpl(Socket socket, ObjectInputStream in, ObjectOutputStream out) {
        this.socket = socket;
        this.in = in;
        this.out = out;
    }
    public static ClientImpl connect() throws TransportException {
        return connect(DEFAULT_SERVER_HOST, DEFAULT_SERVER_PORT);
    }
    public static ClientImpl connect(String serverHost, int serverPort) throws TransportException {
        try {
            Socket socket = new Socket(serverHost, serverPort);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(null);
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            in.readObject();
            return new ClientImpl(socket, in, out);
        } catch (ClassNotFoundException | IOException e) {
            throw new CannotConnectToServerException("cannot connect to " + serverHost + ":" + serverPort, e);
        }
    }


    @Override
    public <R, P> R sendRequest(String method, P... params) throws ClientException {
        try {
            out.writeObject(method);
            out.writeObject(params.length);
            for (P param: params)
                out.writeObject(param);
            Object result = in.readObject();
            if (result instanceof Exception)
                throw (Exception) result;
            else
                return (R) result;
        } catch (Exception e) {
            throw new ClientException("cannot talk with "
                    + socket.getInetAddress().getHostAddress()
                    + ":"
                    + socket.getPort()
                    , e);
        }
    }

    @Override
    public void close() throws Exception {
        in.close();
        out.close();
        socket.close();
    }
}
