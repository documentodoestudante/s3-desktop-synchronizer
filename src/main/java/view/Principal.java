package view;

import controller.Client;
import controller.TerminalController;

public class Principal {
    public static void main(String[] args) throws ClassNotFoundException {

        Client client = ClientHelper.loadClient();

        if (client == null) {
            client = new Client();
            MainView mainView = new MainView(client);

            mainView.setVisible(true);
            return;
        }
        TerminalController terminalController = new TerminalController();
        terminalController.sync(client);
    }

}
