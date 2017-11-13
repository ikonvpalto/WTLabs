package org.kvp_bld_sck.BookDatabase;

import org.kvp_bld_sck.BookDatabase.view.ViewFabric;

import java.io.IOException;

public class Main {

    public static void main(String... args) throws IOException {
        ViewFabric.getFabric().getView().listenUser();
    }

}
