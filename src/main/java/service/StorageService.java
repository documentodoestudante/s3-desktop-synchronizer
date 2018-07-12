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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
        AmazonS3 build = AmazonS3ClientBuilder.standard().withRegion(Regions.SA_EAST_1)
                .withCredentials(staticCredentialsProvider).build();
        return build;
    }

    public void listObjects(String bucketName) {
        ObjectListing objects = s3.listObjects(bucketName);
        for (S3ObjectSummary listing : objects.getObjectSummaries()) {
            System.out.println(listing.getKey());
        }
    }

    private void addKeyToList(List<String> keysToDownload, ObjectListing listObjects) {
        for (S3ObjectSummary listing : listObjects.getObjectSummaries()) {
            LocalDate dataFile = new LocalDate(listing.getLastModified());
            LocalDate dataAtual = new LocalDate();
            if (dataAtual.getDayOfYear() - dataFile.getDayOfYear() <= 3)
                keysToDownload.add(listing.getKey());
        }
    }

    public void downloadObjects(Client cli) {
        ObjectListing listObjects = s3.listObjects(cli.getBucket(), "processed/" + cli.getName() + "/");
        List<String> keysToDownload = new ArrayList<String>();
        addKeyToList(keysToDownload, listObjects);
        while (listObjects.isTruncated()) {
            listObjects = s3.listNextBatchOfObjects(listObjects);
            addKeyToList(keysToDownload, listObjects);
        }
        for (String key : keysToDownload) {
            if (key.contains(".jpg") || key.contains(".txt") || key.contains(".jpeg"))
                downloadToLocal(cli.getPath() + System.getProperty("file.separator") + key, cli.getBucket(), key);
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
            int read_len = 0;
            while ((read_len = s3is.read(read_buf)) > 0) {
                fos.write(read_buf, 0, read_len);
            }
            s3is.close();
            fos.close();
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            System.exit(1);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    public void uploadObjects(String bucket, String name, File[] files) {
        String key = "pickup/" + name + "/";
        for (File s : files) {
            File fileToUpload = new File(s.getAbsolutePath());
            s3.putObject(bucket, key + fileToUpload.getName(), fileToUpload);
        }
    }
}
