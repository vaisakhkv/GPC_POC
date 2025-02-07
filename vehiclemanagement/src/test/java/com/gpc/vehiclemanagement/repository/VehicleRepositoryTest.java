package com.gpc.vehiclemanagement.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.gpc.vehiclemanagement.model.Vehicle;
import com.gpc.vehiclemanagement.model.VehicleId;

@DataJpaTest
public class VehicleRepositoryTest {
	
	 @Autowired
	 private VehicleRepository vehicleRepository;

	 private Vehicle vehicle ;

	 @BeforeEach
	    public void setup(){
		 	vehicle = new Vehicle();
			VehicleId id = new VehicleId();
			id.setId(1);
			id.setVehicleMake("Maruti");
			id.setModel("Baleno");
			id.setVehicleYear(2024);
			vehicle.setSubModel("Delta");
			vehicle.setVehicleId(id);
			vehicle.setCreatedBy("user");
	    }
	 
	 @DisplayName("JUnit test to save Vehicle")
	 @Test
	 public void saveVehicleTest(){
	        // given - precondition or setup
		 	vehicle = new Vehicle();
			VehicleId id = new VehicleId();
			id.setVehicleMake("Maruti");
			id.setModel("Baleno");
			id.setVehicleYear(2024);
			vehicle.setSubModel("Delta");
			vehicle.setVehicleId(id);
			vehicle.setCreatedBy("user");
	        // when - action or the behaviour that we are going test
	        Vehicle savedVehicle = vehicleRepository.save(vehicle);

	        // then - verify the output
	        assertThat(savedVehicle).isNotNull();
	    }
	 
	 	@DisplayName("JUnit test for get all Vehicles operation")
	    @Test
	    public void getAllVehiclesTest(){
	        // given - precondition or setup
	 		Vehicle vehicle1 = new Vehicle();
			VehicleId id = new VehicleId();
			id.setId(2);
			id.setVehicleMake("Maruti");
			id.setModel("Baleno");
			id.setVehicleYear(2025);
			vehicle1.setSubModel("Delta");
			vehicle1.setVehicleId(id);
			vehicle1.setCreatedBy("user");

	        vehicleRepository.save(vehicle);
	        vehicleRepository.save(vehicle1);

	        // when - action or the behaviour that we are going test
	        List<Vehicle> vehicles = vehicleRepository.findAll();

	        // then - verify the output
	        assertThat(vehicles).isNotNull();
	        assertThat(vehicles.size()).isEqualTo(2);
	    }
	 	
	 	 @DisplayName("JUnit test for get vehicle by id operation")
	     @Test
	     public void getVehicleByIdTest(){
	         // given - precondition or setup
	 		Vehicle vehicle1 = new Vehicle();
			VehicleId id = new VehicleId();
			id.setId(2);
			id.setVehicleMake("Maruti");
			id.setModel("Baleno");
			id.setVehicleYear(2025);
			vehicle1.setSubModel("Delta");
			vehicle1.setVehicleId(id);
			vehicle1.setCreatedBy("user");
			
	         vehicleRepository.save(vehicle1);

	         // when - action or the behaviour that we are going test
	         Vehicle savedVehicle = vehicleRepository.findById(vehicle1.getVehicleId()).get();

	         // then - verify the output
	         assertThat(savedVehicle).isNotNull();
	     }
	 	 
	 	 @DisplayName("JUnit test for get vehicle by id and created by operation")
	     @Test
	     public void getVehicleByIdAndCreatedByTest(){
	         // given - precondition or setup
	 		 
	 		Vehicle vehicle2 = new Vehicle();
			VehicleId id = new VehicleId();
			id.setVehicleMake("Maruti");
			id.setModel("Baleno");
			id.setVehicleYear(2026);
			vehicle2.setSubModel("Delta");
			vehicle2.setVehicleId(id);
			vehicle2.setCreatedBy("user");
	 		 
	 		Vehicle savedVehicle =vehicleRepository.save(vehicle2);

	         // when - action or the
	 		Vehicle getVehicle = vehicleRepository.findByVehicleIdAndCreatedBy(savedVehicle.getVehicleId(),savedVehicle.getCreatedBy()).get();

	         // then - verify the output
	         assertThat(getVehicle).isNotNull();
	     }
	 	 
	 	@DisplayName("JUnit test for update vehicle operation")
	    @Test
	    public void updateVehicleTest(){
	        // given - precondition or setup
	 		vehicleRepository.save(vehicle);

	        // when - action or the behaviour that we are going test
	 		Vehicle savedVehicle = vehicleRepository.findById(vehicle.getVehicleId()).get();
	 		savedVehicle.setSubModel("Zeta");
	        Vehicle updatedVehicle = vehicleRepository.save(savedVehicle);

	        // then - verify the output
	        assertThat(updatedVehicle.getSubModel()).isEqualTo("Zeta");
	    }

	    // JUnit test for delete Vehicle operation
	    @DisplayName("JUnit test for delete vehicle operation")
	    @Test
	    public void deleteVehicleTest(){
	        // given - precondition or setup
	    	vehicleRepository.save(vehicle);

	        // when - action or the behaviour that we are going test
	    	vehicleRepository.deleteById(vehicle.getVehicleId());
	        Optional<Vehicle> vehicleOptional = vehicleRepository.findById(vehicle.getVehicleId());

	        // then - verify the output
	        assertThat(vehicleOptional).isEmpty();
	    }
}
