package org.kvp_bld_sck.BookDatabase.client.view.impl;

import org.kvp_bld_sck.BookDatabase.client.controller.ControllerFabric;
import org.kvp_bld_sck.BookDatabase.client.view.View;

import java.io.PrintStream;
import java.util.Scanner;

public class ConsoleView implements View {

    private Scanner in;
    private PrintStream out;
    private boolean work;

    public ConsoleView() {
        in = new Scanner(System.in);
        out = System.out;
    }

    public void listenUser() {
        work = true;

        while (work) {
            out.print(">");
            String command = in.nextLine();
            String response = ControllerFabric.getFabric().getController().execute(command);
            out.println(response);
        }

        in.close();
    }

    public String getAdditionalInfo(String infoKind)  {
        out.print(infoKind);
        out.print(':');
        return in.nextLine();
    }

    @Override
    public void showMessage(String message) {
        out.println(message);
    }

    public void close() {
        work = false;
    }

}
