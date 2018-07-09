package controller;

import service.StorageService;

import javax.swing.*;

public class SyncController {


    public SyncController(Client client) {

        StorageService storageService = new StorageService(client);
//        storageService.listObjects(client.getBucket());
        storageService.downloadObjects(client.getBucket(), client.getName());
        storageService.uploadObjects(client.getBucket(), client.getName());
    }
}
