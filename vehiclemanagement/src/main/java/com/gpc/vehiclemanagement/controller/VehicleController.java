
package com.gpc.vehiclemanagement.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gpc.vehiclemanagement.model.Vehicle;
import com.gpc.vehiclemanagement.model.VehicleId;
import com.gpc.vehiclemanagement.request.VehicleRequest;
import com.gpc.vehiclemanagement.service.VehicleService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/vehicle")
public class VehicleController {
	
	@Value("${admin.role}")
	private String adminRole;

	@Autowired
	private VehicleService vehicleService;
	
	private static final Logger logger = LoggerFactory.getLogger(VehicleController.class);

	@GetMapping("/all")
	public ResponseEntity<Object> getAll() {
		logger.info("Find All Vehicle method");
		List<Vehicle> vehicles = vehicleService.findAll();
        return new ResponseEntity<>(vehicles,HttpStatus.OK);
	}

	@GetMapping("/{id}/{model}/{year}/{make}")
	public ResponseEntity<Object> getById( @PathVariable int id,@PathVariable String model,  @PathVariable int year,  @PathVariable String make,
			Authentication authentication) {
		logger.info("Find Vehicle by id request id "+id+" model "+model+", year "+year+", make "+make);
		boolean hasAdminRole = authentication.getAuthorities().stream()
		          .anyMatch(r -> r.getAuthority().equals(adminRole));
		VehicleId vehicleId = new VehicleId(id,make, year, model);
		Optional<Vehicle> vehicle = vehicleService.findById(vehicleId, authentication.getName(),hasAdminRole);
        return new ResponseEntity<>(vehicle,HttpStatus.OK);
	}

	@PostMapping("/add")
	public ResponseEntity<Object> create(@Valid @RequestBody VehicleRequest vehicle,
			Authentication authentication) {
		logger.info("Vehicle creation request " + vehicle.toString());
		Vehicle vehicleResponse = vehicleService.create(vehicle, authentication.getName());
		logger.info("Vehicle creation response " + vehicleResponse.toString());
		return new ResponseEntity<>(vehicleResponse, HttpStatus.CREATED);

	}

	@DeleteMapping("/delete")
	public ResponseEntity<String> delete( @Valid @RequestBody VehicleRequest vehicle) {
		logger.info("Delete Vehicle request model -"+ vehicle.toString());
		VehicleId vehicleId = new VehicleId(vehicle.getId(),vehicle.getVehicleMake(), vehicle.getVehicleYear(), vehicle.getModel());
		String status = vehicleService.deleteById(vehicleId);
		return new ResponseEntity<>(status, HttpStatus.OK);
	}

	@PutMapping("/update")
	public ResponseEntity<Object> update( @Valid @RequestBody VehicleRequest vehicle, Authentication authentication) {
		logger.info("Vehicle update request " + vehicle.toString());
		Vehicle vehicleRsponse = vehicleService.update(vehicle, authentication.getName());
		logger.info("Get Vehicle response " + vehicleRsponse.toString());
        return new ResponseEntity<>(vehicleRsponse,HttpStatus.OK);
	}
	

}
