package com.gpc.vehiclemanagement.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.gpc.vehiclemanagement.constants.VehicleConstants;

@SpringBootTest
public class VehicleAlreadyExistsExceptionTest {

	@Test
    public void authExceptionWithMessageThrowableTest(){
		VehicleAlreadyExistsException authException = new VehicleAlreadyExistsException(VehicleConstants.RESOURCE_ALLREADY_FOUND, new NullPointerException());
        assertEquals(VehicleConstants.RESOURCE_ALLREADY_FOUND, authException.getMessage());
        assertEquals(NullPointerException.class, authException.getCause().getClass());
    }
	
}
