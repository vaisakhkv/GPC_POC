package com.gpc.vehiclemanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gpc.vehiclemanagement.model.InputFile;

@Repository
public interface FileRepository extends JpaRepository<InputFile, Long> {
}