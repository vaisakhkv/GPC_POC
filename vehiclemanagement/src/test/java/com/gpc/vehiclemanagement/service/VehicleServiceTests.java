package com.gpc.vehiclemanagement.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.context.SpringBootTest;

import com.gpc.vehiclemanagement.constants.VehicleConstants;
import com.gpc.vehiclemanagement.exception.ResourceNotFoundException;
import com.gpc.vehiclemanagement.model.Vehicle;
import com.gpc.vehiclemanagement.model.VehicleId;
import com.gpc.vehiclemanagement.repository.VehicleRepository;
import com.gpc.vehiclemanagement.request.VehicleRequest;


@SpringBootTest
public class VehicleServiceTests {

	@InjectMocks
	private VehicleServiceImpl vehicleService;

	@Mock
	private VehicleRepository vehicleRepository;

	private Vehicle vehicle;

	private VehicleRequest vehicleRequest;

	/*
	 * public void init() { MockitoAnnotations.openMocks(this); }
	 */
	@BeforeEach
	public void setup() {
		vehicle = new Vehicle();
		VehicleId id = new VehicleId();
		id.setVehicleMake("Maruti");
		id.setModel("Baleno");
		id.setVehicleYear(2024);
		vehicle.setSubModel("Delta");
		vehicle.setVehicleId(id);
		vehicle.setCreatedBy("user");

		vehicleRequest = new VehicleRequest();
		vehicleRequest.setVehicleMake("Maruti");
		vehicleRequest.setModel("Baleno");
		vehicleRequest.setSubModel("Delta");
		vehicleRequest.setVehicleYear(2024);
	}

	@DisplayName("JUnit test for save Vehicle method")
	@Test
	public void saveVehicleTest() {
		// given - precondition or setup

		given(vehicleRepository.findByVehicleIdAndCreatedBy(vehicle.getVehicleId(), vehicle.getCreatedBy()))
				.willReturn(Optional.empty());

		given(vehicleRepository.save(vehicle)).willReturn(vehicle);

		// when - action or the behaviour that we are going test
		Vehicle savedVehicle = vehicleService.create(vehicleRequest, "user");

		// then - verify the output
		assertThat(savedVehicle).isNull();

	}
	@DisplayName("JUnit test for find vehicle method which throws exception")
    @Test
    public void findVehicleExceptionTest(){

		VehicleRequest vehicleRequest1 = new VehicleRequest();
		vehicleRequest1.setVehicleMake("Maruti");
		vehicleRequest1.setModel("Baleno");
		vehicleRequest1.setSubModel("Delta");
		vehicleRequest1.setVehicleYear(2024);
		
		vehicleService.create(vehicleRequest, "user");
		vehicleService.create(vehicleRequest1, "user");
		
		ResourceNotFoundException resourceNotFoundException = assertThrows(ResourceNotFoundException.class,
				() -> vehicleService.findById(vehicle.getVehicleId(), "admin", true));

		assertEquals(VehicleConstants.RESOURCE_NOTFOUND, resourceNotFoundException.getMessage());
		
    }
	
	@DisplayName("JUnit test for getAll Vehicles method")
    @Test
    public void getVehiclesListExceptionTest(){
        // given - precondition or setup

		Vehicle vehicle1 = new Vehicle();
		VehicleId id = new VehicleId();
		id.setVehicleMake("Maruti");
		id.setModel("Alto");
		id.setVehicleYear(2023);
		vehicle1.setSubModel("XS");
		vehicle1.setVehicleId(id);
		vehicle1.setCreatedBy("user");
		
		ResourceNotFoundException resourceNotFoundException = assertThrows(ResourceNotFoundException.class,
				() -> vehicleService.findAll());

		assertEquals(VehicleConstants.GET_ALL_RESOURCE_NOTFOUND, resourceNotFoundException.getMessage());

    }
	
	 	@DisplayName("JUnit test for getAll Vehicles method (negative scenario)")
	    @Test
	    public void getVehiclesExceptionTest(){
	        // given - precondition or setup
	 		ResourceNotFoundException resourceNotFoundException = assertThrows(ResourceNotFoundException.class,
					() -> vehicleService.findAll());
	        // then
			assertEquals(VehicleConstants.GET_ALL_RESOURCE_NOTFOUND, resourceNotFoundException.getMessage());
	    }
	 	
	    @DisplayName("JUnit test for getVehicleById method")
	    @Test
	    public void givenVehicleByIdExceptionTest(){
	    	VehicleId id = new VehicleId();
			id.setVehicleMake("Maruti");
			id.setModel("Alto");
			id.setVehicleYear(2023);
			ResourceNotFoundException resourceNotFoundException = assertThrows(ResourceNotFoundException.class,
					() -> vehicleService.findById(id, "user", false));
	        // then
			assertEquals(VehicleConstants.RESOURCE_NOTFOUND, resourceNotFoundException.getMessage());
	    }
	    
	    @DisplayName("JUnit test for delete Vehicle method")
	    @Test
	    public void deleteVehicleExceptionTest(){
	        // given - precondition or setup
	    	VehicleId id = new VehicleId();
			id.setVehicleMake("Maruti");
			id.setModel("Alto");
			id.setVehicleYear(2023);
	    	ResourceNotFoundException resourceNotFoundException = assertThrows(ResourceNotFoundException.class,
					() -> vehicleService.deleteById(id));
	        // then
			assertEquals(VehicleConstants.RESOURCE_NOTFOUND, resourceNotFoundException.getMessage());
	    }
	    
	    @DisplayName("JUnit test for updateVehicle method")
	    @Test
	    public void updateVehicleExceptionTest(){
	    	VehicleRequest vehicleRequest = new VehicleRequest();
	    	vehicleRequest.setVehicleMake("Maruti");
	    	vehicleRequest.setModel("Baleno");
	    	vehicleRequest.setSubModel("Delta");
	    	vehicleRequest.setVehicleYear(2024);
	    	ResourceNotFoundException resourceNotFoundException = assertThrows(ResourceNotFoundException.class,
					() -> vehicleService.update(vehicleRequest,"user"));
	        // then
			assertEquals(VehicleConstants.RESOURCE_NOTFOUND, resourceNotFoundException.getMessage());
	    }
}