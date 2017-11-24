package org.kvp_bld_sck.BookDatabase.client;

import org.kvp_bld_sck.BookDatabase.client.view.ViewFabric;

import java.io.IOException;

public class Client {

    public static void main(String... args) throws IOException {
        ViewFabric.getFabric().getView().listenUser();
    }

}
