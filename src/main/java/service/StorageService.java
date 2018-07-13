package service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import controller.Client;
import org.joda.time.LocalDate;
import view.ClientHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static view.MainView.LBL_LOADING;

public class StorageService {

    private Client client;
    private AmazonS3 s3;

    public StorageService(Client client) {
        this.client = client;
        this.s3 = getClient();
    }

    private AmazonS3 getClient() {
        BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(client.getAccessKey(), client.getSecretKey());
        AWSStaticCredentialsProvider staticCredentialsProvider = new AWSStaticCredentialsProvider(basicAWSCredentials);
        return AmazonS3ClientBuilder.standard().withRegion(Regions.SA_EAST_1)
                .withCredentials(staticCredentialsProvider).build();
    }

    public void listObjects(String bucketName) {
        ObjectListing objects = s3.listObjects(bucketName);
        for (S3ObjectSummary listing : objects.getObjectSummaries()) {
            System.out.println(listing.getKey());
        }
    }

    private void addKeyToList(List<String> keysToDownload, ObjectListing listObjects, Client client) {
        int data = client.getDataAtualizacao().getDayOfYear();
        for (S3ObjectSummary listing : listObjects.getObjectSummaries()) {
            LocalDate dataFile = new LocalDate(listing.getLastModified());
            if (dataFile.getDayOfYear() >= data)
                keysToDownload.add(listing.getKey());
        }
    }

    private void downloadToLocal(String path, String bucket_name, String key_name) {

        try {
            S3Object o = s3.getObject(bucket_name, key_name);
            S3ObjectInputStream s3is = o.getObjectContent();
            if (!new File(path).exists())
                new File(path).getParentFile().mkdirs();

            File arquivo = new File(path);
            FileOutputStream fos = new FileOutputStream(arquivo);
            byte[] read_buf = new byte[1024];
            int read_len;
            while ((read_len = s3is.read(read_buf)) > 0) {
                fos.write(read_buf, 0, read_len);
            }
            s3is.close();
            fos.close();
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void downloadObjects(final Client cli) {
        LBL_LOADING.setValue(0);

        ObjectListing listObjects = s3.listObjects(cli.getBucket(), "processed/" + cli.getName() + "/");
        final List<String> keysToDownload = new ArrayList<String>();
        addKeyToList(keysToDownload, listObjects, cli);
        while (listObjects.isTruncated()) {
            listObjects = s3.listNextBatchOfObjects(listObjects);
            addKeyToList(keysToDownload, listObjects, cli);
        }

        LBL_LOADING.setMaximum(keysToDownload.size());
        LBL_LOADING.setStringPainted(true);


        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 0;
                for (String key : keysToDownload) {
                    LBL_LOADING.setValue(i);
                    i++;
                    if (key.contains(".jpg") || key.contains(".txt") || key.contains(".jpeg") || key.contains(".png")) {
                        downloadToLocal(cli.getPath() + System.getProperty("file.separator") + key, cli.getBucket(), key);
                    }
                }
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            cli.setDataAtualizacao(new LocalDate());
            ClientHelper.salvaClient(cli);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void uploadObjects(final String bucket, String name, final File[] files) {
        LBL_LOADING.setValue(0);
        LBL_LOADING.setMaximum(files.length);
        final String key = "pickup/" + name + "/";

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 1;
                for (File s : files) {
                    LBL_LOADING.setValue(i);
                    i++;
                    File fileToUpload = new File(s.getAbsolutePath());
                    s3.putObject(bucket, key + fileToUpload.getName(), fileToUpload);
                }
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}
