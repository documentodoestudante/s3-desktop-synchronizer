package view;

import controller.Client;

import java.awt.*;

public class Principal {
    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MainView frame = new MainView(getClient());
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private static Client getClient() {
        Client cli = null;
        try {
            cli = ClientHelper.carregaClient();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return cli;
    }

}
