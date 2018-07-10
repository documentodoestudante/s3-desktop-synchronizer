package controller;

import service.StorageService;

import javax.swing.*;
import java.io.File;

import static view.Principal.BASE_PATH;

public class SyncController {


    public SyncController(Client client) {

        System.out.println("INICIANDO DOWNLOAD....");
        StorageService storageService = new StorageService(client);
        storageService.downloadObjects(client.getBucket(), client.getName());

        System.out.println("INICIANDO UPLOAD....");
        File pickupDirectory = new File(BASE_PATH + "/pickup/" + client.getName());
        pickupDirectory.mkdirs();
        File[] files = pickupDirectory.listFiles();
        storageService.uploadObjects(client.getBucket(), client.getName(), files);
        JOptionPane.showMessageDialog(null, "Fim sincronização", "Sincronização", JOptionPane.INFORMATION_MESSAGE);

    }
}
