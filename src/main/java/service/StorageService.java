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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static view.Principal.BASE_PATH;
import static view.Principal.FILE_SEPARATOR;

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
        AmazonS3 build = AmazonS3ClientBuilder.standard().withRegion(Regions.SA_EAST_1).withCredentials(staticCredentialsProvider).build();
        return build;
    }

    public void listObjects(String bucketName) {
        ObjectListing objects = s3.listObjects(bucketName);
        for (S3ObjectSummary listing : objects.getObjectSummaries()) {
            System.out.println(listing.getKey());
        }
    }

    public void downloadObjects(String bucket, String nome) {
        ObjectListing listObjects = s3.listObjects(bucket, "processed/" + nome + "/");
        List<String> objects = new ArrayList<>();
        for (S3ObjectSummary listing : listObjects.getObjectSummaries()) {
            System.out.println(listing.getKey());
            objects.add(listing.getKey());
        }
        for (String key : objects) {
            if (key.contains(".zip") || key.contains(".txt"))
                downloadToLocal(BASE_PATH + FILE_SEPARATOR + key, bucket, key);
        }
    }

    private void downloadToLocal(String path, String bucket_name, String key_name) {

        try {
            S3Object o = s3.getObject(bucket_name, key_name);
            S3ObjectInputStream s3is = o.getObjectContent();
            File arquivo = new File(path);
            arquivo.getParentFile().mkdirs();
            System.out.println("ARQUIVO " + arquivo.getAbsolutePath());
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
