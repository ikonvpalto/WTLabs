package org.kvp_bld_sck.BookDatabase.server.transport.tcp;

import org.kvp_bld_sck.BookDatabase.server.transport.Server;
import org.kvp_bld_sck.BookDatabase.server.transport.ServerConnection;
import org.kvp_bld_sck.BookDatabase.server.transport.exception.ServerException;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

public class ServerImpl implements Server {

    public static final int DEFAULT_PORT = 7474;

    private ServerSocket serverSocket;
    private volatile boolean work;
    private List<ServerConnection> connections;

    public ServerImpl(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
        connections = new LinkedList<>();
    }
    public static ServerImpl create() throws ServerException {
        return create(DEFAULT_PORT);
    }
    public static ServerImpl create(int port) throws ServerException {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            return new ServerImpl(serverSocket);
        } catch (IOException e) {
            throw new ServerException("cannot create server socket", e);
        }
    }

    @Override
    public void run() {
        work = true;
        while (work) {
            try {
                Socket socket = serverSocket.accept();
                ServerConnection connection = ServerConnectionImpl.create(socket);
                connections.add(connection);
                new Thread(connection).run();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        close();
    }

    @Override
    public void close() {
        if (work)
            work = false;
        else {
            for (ServerConnection connection : connections)
                try {
                    connection.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
