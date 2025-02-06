package com.gpc.vehiclemanagement.exception;
public class VehicleAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public VehicleAlreadyExistsException(String message) {
        super(message);
    }

    public VehicleAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}