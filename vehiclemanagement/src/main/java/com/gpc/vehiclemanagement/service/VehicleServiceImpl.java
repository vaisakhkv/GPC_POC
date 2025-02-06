package com.gpc.vehiclemanagement.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gpc.vehiclemanagement.constants.VehicleConstants;
import com.gpc.vehiclemanagement.exception.ResourceNotFoundException;
import com.gpc.vehiclemanagement.exception.VehicleAlreadyExistsException;
import com.gpc.vehiclemanagement.model.Vehicle;
import com.gpc.vehiclemanagement.model.VehicleId;
import com.gpc.vehiclemanagement.repository.VehicleRepository;
import com.gpc.vehiclemanagement.request.VehicleRequest;

@Service
public class VehicleServiceImpl implements VehicleService{

	@Autowired
    private VehicleRepository repository;
	
	private static final Logger logger = LoggerFactory.getLogger(VehicleServiceImpl.class);

    public List<Vehicle> findAll() {
    	List<Vehicle> vehicles = repository.findAll();
    	if(vehicles.isEmpty()) {
    		throw new ResourceNotFoundException(VehicleConstants.GET_ALL_RESOURCE_NOTFOUND);
    	}
        return repository.findAll();
    }

    public Optional<Vehicle> findById(VehicleId id, String username, boolean hasAdminRole) {
    	Optional<Vehicle> vehicle;
    	if(hasAdminRole) {
    		vehicle = repository.findById(id);
    	}else {
    	vehicle = repository.findByVehicleIdAndCreatedBy(id,username);
    	}
    	if(vehicle.isPresent()) {
    		return vehicle;
    	}
	   throw new ResourceNotFoundException(VehicleConstants.RESOURCE_NOTFOUND);

    }

    public Vehicle create(VehicleRequest vehicleReq, String username) {
    	logger.info("Inside service create method");
    	Vehicle vehicle = convetRequestToModel(vehicleReq);
    	vehicle.setCreatedBy(username);
        Optional<Vehicle> savedVehicle = repository.findById(vehicle.getVehicleId());
		if(savedVehicle.isPresent()){
	       throw new VehicleAlreadyExistsException(VehicleConstants.RESOURCE_ALLREADY_FOUND);
	     }
        return repository.save(vehicle);
    }

    public String deleteById(VehicleId id) {
    	if (repository.findById(id).isPresent()) {
    		 repository.deleteById(id);
    		 return VehicleConstants.VEHICLE_DELETES_SUCCESS;
        }
    	throw new ResourceNotFoundException(VehicleConstants.RESOURCE_NOTFOUND);
    }

	public Vehicle update(VehicleRequest vehicleReq, String username) {
		Vehicle vehicle = convetRequestToModel(vehicleReq);
    	vehicle.setCreatedBy(username);
    	logger.info("Vehicle submodal ----"+vehicle.getSubModel());
		Optional<Vehicle> oldVehicle = repository.findByVehicleIdAndCreatedBy(vehicle.getVehicleId(),username);
		if(oldVehicle.isPresent()) {
		vehicle.setCreatedBy(username);
		repository.save(vehicle);
		return vehicle;
		}
    	throw new ResourceNotFoundException(VehicleConstants.RESOURCE_NOTFOUND);
	}

	private Vehicle convetRequestToModel(VehicleRequest request) {
    	Vehicle vehicle  = new Vehicle();

		if(request != null) {
    	VehicleId id = new VehicleId();
    	id.setModel(request.getModel());
    	id.setVehicleMake(request.getVehicleMake());
    	id.setVehicleYear(request.getVehicleYear());
    	vehicle.setVehicleId(id);
    	vehicle.setSubModel(request.getSubModel());
		}
		
		return vehicle;
	}
	
}
