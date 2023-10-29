package com.parkingManagementSystem.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.parkingManagementSystem.entity.Employee;
import com.parkingManagementSystem.entity.ParkingLot;
import com.parkingManagementSystem.entity.TicketRegistry;
import com.parkingManagementSystem.entity.VehicleCategory;
import com.parkingManagementSystem.repository.EmployeeRepository;
import com.parkingManagementSystem.repository.ParkingLotRepository;
import com.parkingManagementSystem.repository.TicketRegistryRepository;
import com.parkingManagementSystem.repository.VehicleCategoryRepository;

@Service
public class TicketRegistryService {

	private final TicketRegistryRepository ticketRegistryRepository;
	private final ParkingLotRepository parkingLotRepository;
	private final EmployeeRepository employeeRepository;
	private final VehicleCategoryRepository categoryRepository;
	private final EntityManager entityManager;

	@Autowired
	public TicketRegistryService(EntityManager entityManager, TicketRegistryRepository ticketRegistryRepository,ParkingLotRepository parkingLotRepository, EmployeeRepository employeeRepository, VehicleCategoryRepository categoryRepository ) {
		this.entityManager = entityManager;
		this.ticketRegistryRepository = ticketRegistryRepository;
		this.parkingLotRepository = parkingLotRepository;
		this.employeeRepository = employeeRepository;
		this.categoryRepository = categoryRepository;
	}

	public List<TicketRegistry> getAllTickets() {
		return ticketRegistryRepository.findAll();
	}

	public TicketRegistry getTicketById(Long id) {
		return ticketRegistryRepository.findById(id).orElse(null);
	}

	public TicketRegistry createTicket(TicketRegistry ticket) {
		TicketRegistry createdTicket = ticketRegistryRepository.save(ticket);
		
		ParkingLot parkingLot = parkingLotRepository.findById(createdTicket.getLot().getLotId()).orElse(null);
		createdTicket.setLot(parkingLot);
		Employee employee = employeeRepository.findById(createdTicket.getEmployee().getEmployeeId()).orElse(null);
		createdTicket.setEmployee(employee);
		VehicleCategory category = categoryRepository.findById(createdTicket.getCategory().getCategoryId()).orElse(null);
		createdTicket.setCategory(category);
		
		return createdTicket;
	}

	public TicketRegistry updatePaymentStatus(Long id, String paymentStatus) {
		if (ticketRegistryRepository.existsById(id)) {
			TicketRegistry ticket = ticketRegistryRepository.findById(id).get();
			if (ticket.getPaymentStatus().equalsIgnoreCase("Paid")) {
				throw new DataIntegrityViolationException("Payment already received..!!");
			} else {
				ticket.setPaymentStatus(paymentStatus);
				return ticketRegistryRepository.save(ticket);
			}
		} else {
			return null;
		}
	}

	public void deleteTicket(Long id) {
		ticketRegistryRepository.deleteById(id);
	}

	public Double computeFare(Long ticketId) {
		StoredProcedureQuery query = entityManager.createStoredProcedureQuery("ComputeTicketFare")
				.registerStoredProcedureParameter("input_ticket_id", Long.class, ParameterMode.IN)
				.registerStoredProcedureParameter("fare_amount", Double.class, ParameterMode.OUT)
				.setParameter("input_ticket_id", ticketId);
		query.execute();
		Double fareAmount = (Double) query.getOutputParameterValue("fare_amount");
		return fareAmount;
	}
	
    public List<Object[]> getLotDailyRevenueByLotId(Long lotId) {
        return ticketRegistryRepository.getLotDailyRevenueByLotId(lotId);
    } 
}
