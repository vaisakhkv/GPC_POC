package com.gpc.vehiclemanagement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gpc.vehiclemanagement.model.Vehicle;
import com.gpc.vehiclemanagement.model.VehicleId;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, VehicleId>{
	
	Optional<Vehicle> findByVehicleIdAndCreatedBy(VehicleId id, String username);

    @Query("SELECT v FROM Vehicle v WHERE v.vehicleId.vehicleMake = ?1 AND v.vehicleId.model = ?2 AND v.vehicleId.vehicleYear =?3")
	Vehicle findByVehicleMakeAndModelAndVehicleYear(String vehicleMake, String model, int vehicleYear);

}
