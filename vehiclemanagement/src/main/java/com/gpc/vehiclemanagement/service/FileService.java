package com.gpc.vehiclemanagement.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.gpc.vehiclemanagement.model.InputFile;

public interface FileService {

    List<InputFile> uploadFiles(MultipartFile[] files);
}