package org.kvp_bld_sck.BookDatabase.transport;

import org.kvp_bld_sck.BookDatabase.transport.exception.ClientException;

public interface Client extends AutoCloseable {

    <R, P> R sendRequest(String method, P... params) throws ClientException;

}
