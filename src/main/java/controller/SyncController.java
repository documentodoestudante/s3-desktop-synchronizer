package controller;

import service.StorageService;
import view.ClientHelper;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

import static config.GlobalConstants.*;

public class SyncController implements Runnable {
    private Client client;
    private StorageService storageService;

    public SyncController(Client client) {
        this.client = client;
    }

    @Override
    public void run() {
        try {
            System.out.println(SYNC_INIT);
            download(client);
            upload();
            System.out.println(SYNC_END);
        }
        catch (Exception e){
            System.err.println(SYNC_ERROR);
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
            cli.setUpdateDate(cli.getUpdateDate().minusDays(7));
            try {
                ClientHelper.saveClient(cli);
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
