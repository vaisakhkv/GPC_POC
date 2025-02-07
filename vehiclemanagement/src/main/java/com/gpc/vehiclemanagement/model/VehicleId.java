package com.gpc.vehiclemanagement.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Embeddable
public class VehicleId implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name = "vehicleMake", nullable = false)
    private String vehicleMake;
    
	@Column(name = "vehicleYear",nullable = false)
    private int vehicleYear;
	
	@Column(name = "model",nullable = false)
    private String model;

	public VehicleId() {
	
	}
    // Parameterized constructor

	public VehicleId(int id, String vehicleMake, int vehicleYear, String model) {
		this.id = id;
		this.vehicleMake = vehicleMake;
		this.vehicleYear = vehicleYear;
		this.model = model;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

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

	@Override
	public String toString() {
		return "VehicleId [id=" + id + ", vehicleMake=" + vehicleMake + ", vehicleYear=" + vehicleYear + ", model="
				+ model + "]";
	}

	

	
}