package org.kvp_bld_sck.BookDatabase;

import org.kvp_bld_sck.BookDatabase.transport.exception.ServerException;
import org.kvp_bld_sck.BookDatabase.transport.tcp.ServerImpl;

public class Server {

    public static void main(String... args) throws ServerException {
        ServerImpl server = ServerImpl.create();
        server.run();
    }

}
