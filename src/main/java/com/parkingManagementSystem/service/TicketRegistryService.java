package com.parkingManagementSystem.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.parkingManagementSystem.entity.TicketRegistry;
import com.parkingManagementSystem.repository.TicketRegistryRepository;

@Service
public class TicketRegistryService {
	@Autowired
	private TicketRegistryRepository ticketRegistryRepository;
	private final EntityManager entityManager;

	public TicketRegistryService(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public List<TicketRegistry> getAllTickets() {
		return ticketRegistryRepository.findAll();
	}

	public TicketRegistry getTicketById(Long id) {
		return ticketRegistryRepository.findById(id).orElse(null);
	}

	public TicketRegistry createTicket(TicketRegistry ticket) {
		return ticketRegistryRepository.save(ticket);
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
}
