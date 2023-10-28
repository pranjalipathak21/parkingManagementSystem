package com.parkingManagementSystem.entity;

import javax.persistence.*;

@Entity
@Table(name = "LotChargeSheet")
public class LotChargeSheet {

	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Sheet_id")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "Lot_id")
	private ParkingLot parkingLot;

	@ManyToOne
	@JoinColumn(name = "Category_id")
	private VehicleCategory vehicleCategory;

	@Column(name = "rate_per_hour", nullable = false, columnDefinition = "INT DEFAULT 0")
	private int ratePerHour;

	// Constructors, getters, and setters

	public LotChargeSheet() {
		// Default constructor
	}

	public LotChargeSheet(ParkingLot parkingLot, VehicleCategory vehicleCategory, int ratePerHour) {
		this.parkingLot = parkingLot;
		this.vehicleCategory = vehicleCategory;
		this.ratePerHour = ratePerHour;
	}

	// Getters and setters

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ParkingLot getParkingLot() {
		return parkingLot;
	}

	public void setParkingLot(ParkingLot parkingLot) {
		this.parkingLot = parkingLot;
	}

	public VehicleCategory getVehicleCategory() {
		return vehicleCategory;
	}

	public void setVehicleCategory(VehicleCategory vehicleCategory) {
		this.vehicleCategory = vehicleCategory;
	}

	public int getRatePerHour() {
		return ratePerHour;
	}

	public void setRatePerHour(int ratePerHour) {
		this.ratePerHour = ratePerHour;
	}
}
