package com.gpc.vehiclemanagement.service;

import java.util.List;
import java.util.Optional;

import com.gpc.vehiclemanagement.exception.ResourceNotFoundException;
import com.gpc.vehiclemanagement.exception.VehicleAlreadyExistsException;
import com.gpc.vehiclemanagement.model.Vehicle;
import com.gpc.vehiclemanagement.model.VehicleId;
import com.gpc.vehiclemanagement.request.VehicleRequest;

public interface VehicleService {

	    List<Vehicle> findAll() throws ResourceNotFoundException;
	    Optional<Vehicle> findById(VehicleId id, String username, boolean hasAdminRole) throws ResourceNotFoundException;
	    Vehicle create(VehicleRequest vehicleReq, String username) throws VehicleAlreadyExistsException;
	    String deleteById(VehicleId id)throws ResourceNotFoundException;
		Vehicle update(VehicleRequest vehicleReq, String username)throws ResourceNotFoundException;
}
