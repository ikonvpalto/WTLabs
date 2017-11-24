package org.kvp_bld_sck.BookDatabase;

import org.kvp_bld_sck.BookDatabase.server.transport.exception.ServerException;
import org.kvp_bld_sck.BookDatabase.server.transport.tcp.ServerImpl;

public class Server {

    public static void main(String... args) throws ServerException {
        ServerImpl server = ServerImpl.create();
        server.run();
    }

}
