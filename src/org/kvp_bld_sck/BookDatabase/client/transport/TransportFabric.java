package org.kvp_bld_sck.BookDatabase.client.transport;

import org.kvp_bld_sck.BookDatabase.client.transport.exception.TransportException;
import org.kvp_bld_sck.BookDatabase.client.transport.tcp.ClientImpl;

public class TransportFabric {

    private Client client = null;

    private TransportFabric() {}
    private static TransportFabric fabric;
    public static TransportFabric getFabric() {
        if (null == fabric)
            fabric = new TransportFabric();
        return fabric;
    }

    public Client getClientTransport() throws TransportException {
        if (null == client)
            client = ClientImpl.connect();
        return client;
    }
}
