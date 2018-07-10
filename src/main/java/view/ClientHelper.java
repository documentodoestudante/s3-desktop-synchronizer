package view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import controller.Client;

public class ClientHelper {
	public static void salvaClient(Client cli) throws IOException {
		FileOutputStream f = new FileOutputStream(new File(System.getProperty("user.home")+"/client"));
		ObjectOutputStream o = new ObjectOutputStream(f);
		o.writeObject(cli);
		o.close();
		f.close();
	}

	public static Client carregaClient() throws ClassNotFoundException {
		Client cli;
		try {
			FileInputStream fi = new FileInputStream(new File(System.getProperty("user.home")+"/client"));
			ObjectInputStream oi = new ObjectInputStream(fi);
			cli = (Client) oi.readObject();

			oi.close();
			fi.close();
		} catch (IOException e) {
			e.printStackTrace();
			cli = new Client();
		}
		return cli;
	}
}
