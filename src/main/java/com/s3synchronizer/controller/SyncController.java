package com.s3synchronizer.controller;

import com.s3synchronizer.service.StorageService;
import com.s3synchronizer.view.ClientHelper;
import com.s3synchronizer.view.MainView;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

public class SyncController implements Runnable {
    private Client client;
    private MainView jframe;
    private StorageService storageService;

    public SyncController(Client client, MainView jrame) {
        this.client = client;
        this.jframe = jrame;
    }

    @Override
    public void run() {
        System.out.println("inicio");
        try {
            download(client);
            upload();
            this.jframe.validaFim(true);
            System.out.println("Fim");
        }catch (Exception e){
            this.jframe.validaFim(false);
        }
    }

    private void upload() {
        File pickupDirectory = new File(client.getPath() + "/pickup/" + client.getName());
        pickupDirectory.mkdirs();
        File[] files = pickupDirectory.listFiles(new FileFilterCustom());
        storageService.uploadObjects(client.getBucket(), client.getName(), files);
    }

    private void download(Client cli){
        storageService = new StorageService(client);
        if (new File(cli.getPath() + System.getProperty("file.separator") + "processed/"+cli.getName()).list() == null){
            cli.setDataAtualizacao(cli.getDataAtualizacao().minusDays(7));
            try {
                ClientHelper.salvaClient(cli);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        storageService.downloadObjects(client);
    }

    public class FileFilterCustom implements FileFilter{
        String[] extensionsAllowed = new String[]{".txt", ".zip"};
        @Override
        public boolean accept(File pathname) {
            if (pathname.isDirectory())
                return false;
            else{
                String path = pathname.getAbsolutePath();
                for (String extension: extensionsAllowed) {
                    if (path.endsWith(extension))
                        return true;
                }
                return false;
            }
        }
    }
}
