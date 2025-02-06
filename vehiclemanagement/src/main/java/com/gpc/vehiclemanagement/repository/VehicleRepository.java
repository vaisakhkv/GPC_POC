package com.gpc.vehiclemanagement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gpc.vehiclemanagement.model.Vehicle;
import com.gpc.vehiclemanagement.model.VehicleId;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, VehicleId>{
	Optional<Vehicle> findByVehicleIdAndCreatedBy(VehicleId id, String username);
}
