package com.gpc.vehiclemanagement.service;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
public class GcsFileService {

    private final Storage storage;

    public GcsFileService() {
        this.storage = StorageOptions.getDefaultInstance().getService();
    }

    public String readFile(String bucketName, String fileName) {
        Blob blob = storage.get(bucketName, fileName);
        if (blob == null) {
            throw new RuntimeException("No such object");
        }
        return new String(blob.getContent(), StandardCharsets.UTF_8);
    }
}
