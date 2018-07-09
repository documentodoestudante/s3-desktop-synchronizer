package controller;

import service.StorageService;

import java.io.File;

import static view.Principal.BASE_PATH;

public class SyncController {


    public SyncController(Client client) {

        StorageService storageService = new StorageService(client);
//        storageService.listObjects(client.getBucket());
        storageService.downloadObjects(client.getBucket(), client.getName());

        File pickupDirectory = new File(BASE_PATH + "/pickup/" + client.getName());
        pickupDirectory.mkdirs();
        File[] files = pickupDirectory.listFiles();
        storageService.uploadObjects(client.getBucket(), client.getName(), files);
    }
}
