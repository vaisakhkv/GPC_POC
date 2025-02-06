package com.gpc.vehiclemanagement.controller;



import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.gpc.vehiclemanagement.constants.VehicleConstants;
import com.gpc.vehiclemanagement.model.Vehicle;
import com.gpc.vehiclemanagement.model.VehicleId;
import com.gpc.vehiclemanagement.request.VehicleRequest;
import com.gpc.vehiclemanagement.service.VehicleServiceImpl;

@SpringBootTest
@AutoConfigureMockMvc
public class VehicleControllerTest{

	@InjectMocks
	VehicleController testController;
	
	@Mock
	VehicleServiceImpl service;
	
	@MockitoBean
    private AuthenticationManager authenticationManager;
	
	private Vehicle vehicle ;
	
	private VehicleRequest request ;
	
	private UserDetails user;
	
	 @BeforeEach
	 public void setup(){
		 	request = new VehicleRequest();
			request.setVehicleMake("Maruti");
			request.setModel("Baleno");
			request.setSubModel("Delta");
			request.setVehicleYear(2024);
			request.setSubModel("Delta");
			
			vehicle = new Vehicle();
			VehicleId id = new VehicleId();
			id.setVehicleMake("Maruti");
			id.setModel("Baleno");
			id.setVehicleYear(2024);
			vehicle.setSubModel("Delta");
			vehicle.setVehicleId(id);
			
			user = User.builder().username("user").password(passwordEncoder().encode("user@123")).roles("USER")
	                .build();
		 
	 }
	
	@Test
	public void saveVehicleTest() {
		
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null);
        Mockito.when(authenticationManager.authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
		String username = "user";
		
		ResponseEntity<Vehicle> expected = new ResponseEntity<Vehicle>(vehicle,HttpStatus.CREATED);
		Mockito.when(service.create(request,username)).thenReturn(vehicle);
		ResponseEntity<Object> actual = testController.create(request, authentication);
		assertEquals(expected, actual);
	}
	
	@Test
	public void getAllVehiclesTest() {
		
		Vehicle vehicle1 = new Vehicle();
		VehicleId id = new VehicleId();
		id.setVehicleMake("Maruti");
		id.setModel("Alto");
		id.setVehicleYear(2024);
		vehicle1.setSubModel("XS");
		vehicle1.setVehicleId(id);
		
		List <Vehicle> vehicles = new ArrayList<Vehicle>();
		vehicles.add(vehicle);
		vehicles.add(vehicle1);
		
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null);
        Mockito.when(authenticationManager.authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
		
		Mockito.when(service.findAll()).thenReturn(vehicles);
		ResponseEntity<Object> actual = testController.getAll();
		assertThat(actual).isNotNull();
	}
	
	@Test
	public void getVehicleByIDTest() {
		Optional<Vehicle> vehicleResponse = Optional.ofNullable(vehicle);
		VehicleId id = new VehicleId();
		id.setVehicleMake("Maruti");
		id.setModel("Baleno");
		id.setVehicleYear(2024);
		
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null);
        Mockito.when(authenticationManager.authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
		
		Mockito.when(service.findById(id,"user",false)).thenReturn(vehicleResponse);
		ResponseEntity<Object> actual = testController.getById(id.getModel(), id.getVehicleYear(), id.getVehicleMake(), authentication);
		assertThat(actual).isNotNull();
	}
	
	@Test
	public void deleteVehicleTest() {
		VehicleId id = new VehicleId();
		id.setVehicleMake("Maruti");
		id.setModel("Baleno");
		id.setVehicleYear(2024);
		
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null);
        Mockito.when(authenticationManager.authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
		
        ResponseEntity<String> expected = new ResponseEntity<String>(HttpStatus.OK);
		Mockito.when(service.deleteById(id)).thenReturn(VehicleConstants.VEHICLE_DELETES_SUCCESS);
		ResponseEntity<String> actual = testController.delete(request);
		assertEquals(actual,expected);
	}
	
	@Test
	public void updateVehicleTest() {
		
		request.setSubModel("XS");
		vehicle.setSubModel("XS");
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null);
        Mockito.when(authenticationManager.authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
		
        ResponseEntity<Vehicle> expected = new ResponseEntity<Vehicle>(vehicle,HttpStatus.OK);
		Mockito.when(service.update(request,"user")).thenReturn(vehicle);
		ResponseEntity<Object> actual = testController.update(request,authentication);
		assertEquals(actual,expected);
	}
	  
	public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
