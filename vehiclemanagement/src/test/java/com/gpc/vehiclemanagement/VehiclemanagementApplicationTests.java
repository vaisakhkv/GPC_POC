package com.gpc.vehiclemanagement;


import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.gpc.vehiclemanagement.controller.VehicleController;

@SpringBootTest
class VehiclemanagementApplicationTests {

	 @Autowired
	 VehicleController vehicleController;

	  @Test
	  public void contextLoads() {
	    assertNotNull(vehicleController);
	  }

}
