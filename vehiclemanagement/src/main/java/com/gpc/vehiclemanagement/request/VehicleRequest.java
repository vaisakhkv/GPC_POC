package com.gpc.vehiclemanagement.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class VehicleRequest {

	@NotEmpty(message = "vehicleMake is required")
    private String vehicleMake;
    
	@NotNull(message = "Vehicle year is required")
    private int vehicleYear;
	
	@NotEmpty(message = "model is required")
    private String model;
	
    private String subModel;

	public String getVehicleMake() {
		return vehicleMake;
	}

	public void setVehicleMake(String vehicleMake) {
		this.vehicleMake = vehicleMake;
	}

	public int getVehicleYear() {
		return vehicleYear;
	}

	public void setVehicleYear(int vehicleYear) {
		this.vehicleYear = vehicleYear;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getSubModel() {
		return subModel;
	}

	public void setSubModel(String subModel) {
		this.subModel = subModel;
	}

}
