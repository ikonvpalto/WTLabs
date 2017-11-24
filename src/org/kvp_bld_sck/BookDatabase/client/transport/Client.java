package org.kvp_bld_sck.BookDatabase.client.transport;

import org.kvp_bld_sck.BookDatabase.client.transport.exception.ClientException;

public interface Client extends AutoCloseable {

    <R, P> R sendRequest(String method, P... params) throws ClientException;

}
