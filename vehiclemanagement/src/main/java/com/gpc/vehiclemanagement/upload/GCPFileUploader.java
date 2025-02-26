//package com.gpc.vehiclemanagement.upload;
//import com.google.cloud.storage.BlobId;
//import com.google.cloud.storage.BlobInfo;
//import com.google.cloud.storage.Storage;
//import com.google.cloud.storage.StorageOptions;
//
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.io.IOException;
//
//public class GCPFileUploader {
//
//    public static void uploadFile(String projectId, String bucketName, String objectName, String filePath) {
//        // Initialize the storage client
//        Storage storage = StorageOptions.newBuilder().setProjectId(projectId).build().getService();
//
//        // Get the file path
//        Path path = Paths.get(filePath);
//        try {
//            // Read the file content
//            byte[] bytes = Files.readAllBytes(path);
//
//            // Create a BlobId
//            BlobId blobId = BlobId.of(bucketName, objectName);
//
//            // Create a BlobInfo
//            BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
//
//            // Upload the file
//            storage.create(blobInfo, bytes);
//
//            System.out.println("File " + filePath + " uploaded to bucket " + bucketName + " as " + objectName);
//        } catch (IOException e) {
//            System.err.println("Failed to upload file: " + e.getMessage());
//        }
//    }
//
//    public static void main(String[] args) {
//        String projectId = "gcpbucketfilestorage";
//        String bucketName = "your-bucket-name";
//        String objectName = "your-object-name";
//        String filePath = "D:/gitrep/GPC_POC/vehiclemanagement/file.txt";
//
//        uploadFile(projectId, bucketName, objectName, filePath);
//    }
//}