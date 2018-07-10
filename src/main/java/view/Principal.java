package view;

import java.awt.EventQueue;

import controller.Client;

public class Principal {
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Tela frame = new Tela(getClient());
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
