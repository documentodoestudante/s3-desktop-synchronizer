package controller;


import view.MainView;


public class TerminalController {

    public void sync (Client client) {
            SyncController sc = new SyncController(client);
            Thread t = new Thread(sc);
            t.start();
    }
}
