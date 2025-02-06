package com.gpc.vehiclemanagement.model;

import org.springframework.stereotype.Component;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "vehicles")
@Component
public class Vehicle {
	
	@EmbeddedId
	private VehicleId vehicleId;
	
	@Column(name = "subModel")
    private String subModel;

	@Column(name = "createdBy")
    private String createdBy;
	
	public Vehicle() {
		
	}

	public Vehicle( VehicleId vehicleId, String submodel, String createdBy) {
		this.vehicleId = vehicleId;
		this.subModel = submodel;
		this.createdBy = createdBy;
	}

	public VehicleId getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(VehicleId vehicleId) {
		this.vehicleId = vehicleId;
	}

	public String getSubModel() {
		return subModel;
	}

	public void setSubModel(String subModel) {
		this.subModel = subModel;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Override
	public String toString() {
		return "Vehicle [vehicleId=" + vehicleId + ", submodel=" + subModel + ", createdBy=" + createdBy
				+ "]";
	}

}