package org.kvp_bld_sck.BookDatabase.view;

public interface View {

    void listenUser();
    String getAdditionalInfo(String infoKind);
    void showMessage(String message);
    void close();

}
