package com.gpc.vehiclemanagement.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.io.FileUtils;
import org.apache.coyote.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.gpc.vehiclemanagement.exception.FileWriteException;
import com.gpc.vehiclemanagement.exception.GCPFileUploadException;
import com.gpc.vehiclemanagement.exception.InvalidFileTypeException;
import com.gpc.vehiclemanagement.request.FileDto;

import net.bytebuddy.utility.RandomString;

@Component
public class DataBucketUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataBucketUtil.class);

    @Value("${gcp.config.file}")
    private String gcpConfigFile;

    @Value("${gcp.project.id}")
    private String gcpProjectId;

    @Value("${gcp.bucket.id}")
    private String gcpBucketId;

    @Value("${gcp.dir.name}")
    private String gcpDirectoryName;


    public FileDto uploadFile(MultipartFile multipartFile, String fileName, String contentType) {

        try{

            LOGGER.info("Start file uploading process on GCS");
            byte[] fileData = FileUtils.readFileToByteArray(convertFile(multipartFile));

            InputStream inputStream = new ClassPathResource(gcpConfigFile).getInputStream();
            LOGGER.info("----------1"+gcpConfigFile);
            StorageOptions options = StorageOptions.newBuilder().setProjectId(gcpProjectId)
                    .setCredentials(GoogleCredentials.fromStream(inputStream)).build();
            LOGGER.info("----------2");
            Storage storage = options.getService();
            LOGGER.info("----------2.1"+storage);
            Bucket bucket = storage.get(gcpBucketId,Storage.BucketGetOption.fields());
            LOGGER.info("----------3");
            RandomString id = new RandomString(6, ThreadLocalRandom.current());
            Blob blob = bucket.create(gcpDirectoryName + "/" + fileName + "-" + id.nextString() + checkFileExtension(fileName), fileData, contentType);
            LOGGER.info("----------4");
            if(blob != null){
                LOGGER.info("File successfully uploaded to GCS");
                return new FileDto(blob.getName(), blob.getMediaLink());
            }else {
            	 LOGGER.info("Blob is null");
            }

        }catch (Exception e){
            LOGGER.info("An error occurred while uploading data. Exception: ", e);
            throw new GCPFileUploadException("An error occurred while storing data to GCS");
        }
        throw new GCPFileUploadException("An error occurred while storing data to GCS");
    }

    private File convertFile(MultipartFile file) {

        try{
            if(file.getOriginalFilename() == null){
                throw new BadRequestException("Original file name is null");
            }
            File convertedFile = new File(file.getOriginalFilename());
            FileOutputStream outputStream = new FileOutputStream(convertedFile);
            outputStream.write(file.getBytes());
            outputStream.close();
            LOGGER.info("Converting multipart file : {}", convertedFile);
            return convertedFile;
        }catch (Exception e){
            throw new FileWriteException("An error has occurred while converting the file");
        }
    }

    private String checkFileExtension(String fileName) {
        if(fileName != null && fileName.contains(".")){
            String[] extensionList = {".png", ".jpeg", ".pdf", ".doc", ".mp3"};

            for(String extension: extensionList) {
                if (fileName.endsWith(extension)) {
                    LOGGER.info("Accepted file type : {}", extension);
                    return extension;
                }
            }
        }
        LOGGER.info("Not a permitted file type");
        throw new InvalidFileTypeException("Not a permitted file type");
    }
}