package controller;

import java.io.File;

import service.StorageService;
import view.Tela;

public class SyncController implements Runnable {
	private Client client;
	private Tela jframe;

	public SyncController(Client client, Tela jrame) {
		this.client = client;
		this.jframe = jrame;
	}

	@Override
	public void run() {
		System.out.println("inicio");
		StorageService storageService = new StorageService(client);
		storageService.downloadObjects(client);
		File pickupDirectory = new File(client.getPath() + "/pickup/" + client.getName());
		pickupDirectory.mkdirs();
		File[] files = pickupDirectory.listFiles();
		storageService.uploadObjects(client.getBucket(), client.getName(), files);
		this.jframe.validaFim(true);
		System.out.println("Fim");
	}
}
