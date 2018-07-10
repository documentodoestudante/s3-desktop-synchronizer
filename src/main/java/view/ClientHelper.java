package view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import controller.Client;

public class ClientHelper {
	public static boolean salvaClient(Client cli) throws IOException {
		if (verificaClient(cli)) {
			FileOutputStream f = new FileOutputStream(new File(System.getProperty("user.home") + "/client"));
			ObjectOutputStream o = new ObjectOutputStream(f);
			o.writeObject(cli);
			o.close();
			f.close();
			return true;
		} else {
			return false;
		}
	}

	public static Client carregaClient() throws ClassNotFoundException {
		Client cli;
		try {
			FileInputStream fi = new FileInputStream(new File(System.getProperty("user.home") + "/client"));
			ObjectInputStream oi = new ObjectInputStream(fi);
			cli = (Client) oi.readObject();
			oi.close();
			fi.close();
			cli.setLoaded(true);
		} catch (IOException e) {
			System.err.println(e.getMessage());
			cli = new Client();
		}
		return cli;
	}

	private static boolean verificaClient(Client cli) {
		if (cli == null)
			return false;
		if (cli.getAccessKey() == null || cli.getAccessKey().trim().length() == 0)
			return false;
		if (cli.getSecretKey() == null || cli.getSecretKey().trim().length() == 0)
			return false;
		if (cli.getBucket() == null || cli.getBucket().trim().length() == 0)
			return false;
		if (cli.getName() == null || cli.getName().trim().length() == 0)
			return false;
		if (cli.getPath() == null || cli.getPath().trim().length() == 0)
			return false;
		if (cli.getRegion() == null || cli.getRegion().trim().length() == 0)
			return false;
		return true;
	}
}
