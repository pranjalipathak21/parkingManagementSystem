package com.parkingManagementSystem.entity;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

@Entity
@Table(name = "TicketRegistry")
public class TicketRegistry {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Ticket_id")
    private Long ticketId;

    @Column(name = "Vehicle_No", nullable = false)
    private String vehicleNo;

    @Column(name = "Phone_No")
    private String phoneNo;

    @Column(name = "Entry_Time", nullable = false)
    private Timestamp entryTime;

    @Column(name = "Exit_Time", nullable = true)
    private Timestamp exitTime;

    @Column(name = "Fare", nullable = false)
    private int fare;

    @Column(name = "Payment_Status", nullable = false)
    private String paymentStatus = "Pending";

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Category_id")
    private VehicleCategory category;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Employee_id")
    private Employee employee;
    
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Lot_id")
    private ParkingLot lot;

    public Long getTicketId() {
		return ticketId;
	}

	public void setTicketId(Long ticketId) {
		this.ticketId = ticketId;
	}

	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public Timestamp getEntryTime() {
		return entryTime;
	}

	public Timestamp getExitTime() {
		return exitTime;
	}

	public void setExitTime(Timestamp exitTime) {
		this.exitTime = exitTime;
	}

	public int getFare() {
		return fare;
	}

	public void setFare(int fare) {
		this.fare = fare;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public VehicleCategory getCategory() {
		return category;
	}

	public void setCategory(VehicleCategory category) {
		this.category = category;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public ParkingLot getLot() {
		return lot;
	}

	public void setLot(ParkingLot lot) {
		this.lot = lot;
	}

	@PrePersist
    protected void onCreate() {
        // Set the creation time to the current timestamp when persisting for the first time
        if (entryTime == null) {
            entryTime = new Timestamp(new Date().getTime());
        }
    }
}
