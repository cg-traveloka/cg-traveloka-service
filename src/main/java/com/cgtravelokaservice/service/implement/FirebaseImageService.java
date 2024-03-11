package com.cgtravelokaservice.service.implement;

import com.cgtravelokaservice.firebase.FireBaseProperties;
import com.cgtravelokaservice.service.IImageService;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.StorageClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.IOException;

@Service
public class FirebaseImageService implements IImageService {
    private static boolean firebaseInitialized = false;

    @Autowired
    FireBaseProperties properties;

    @EventListener
    public void init(ApplicationReadyEvent event) {

        // initialize Firebase

        try {

            ClassPathResource serviceAccount =
                    new ClassPathResource("firebase.json");

            FirebaseOptions options =
                    new FirebaseOptions.Builder().setCredentials(GoogleCredentials.fromStream(serviceAccount.getInputStream())).setStorageBucket(properties.getBucketName()).build();

            FirebaseApp.initializeApp(options);
            firebaseInitialized=true;

        } catch (Exception ex) {

            ex.printStackTrace();

        }
    }

    @Override
    public String getImageUrl(String name) {
        return String.format(properties.imageUrl, name);
    }

    @Override
    public String save(MultipartFile file) throws IOException {

        Bucket bucket =
                StorageClient.getInstance().bucket();

        String name =
                generateFileName(file.getOriginalFilename());

        Blob blob =
                bucket.create(name, file.getBytes(), file.getContentType());

        String url =
                properties.getImageUrl() + "o/" + blob.getName() + "?alt=media";
        return url;
    }

    @Override
    public String save(BufferedImage bufferedImage, String originalFileName) throws IOException {

        byte[] bytes =
                getByteArrays(bufferedImage, getExtension(originalFileName));

        Bucket bucket =
                StorageClient.getInstance().bucket();

        String name =
                generateFileName(originalFileName);

        bucket.create(name, bytes);

        return name;
    }

    @Override
    public void delete(String name) throws IOException {

        Bucket bucket =
                StorageClient.getInstance().bucket();

        if (StringUtils.isEmpty(name)) {
            throw new IOException("invalid file name");
        }

        Blob blob = bucket.get(name);

        if (blob == null) {
            throw new IOException("file not found");
        }

        blob.delete();
    }

}
