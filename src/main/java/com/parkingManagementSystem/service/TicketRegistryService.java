package com.parkingManagementSystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.parkingManagementSystem.entity.TicketRegistry;
import com.parkingManagementSystem.repository.TicketRegistryRepository;

@Service
public class TicketRegistryService {
	@Autowired
    private TicketRegistryRepository ticketRegistryRepository;

    public List<TicketRegistry> getAllTickets() {
        return ticketRegistryRepository.findAll();
    }

    public TicketRegistry getTicketById(Long id) {
        return ticketRegistryRepository.findById(id).orElse(null);
    }

    public TicketRegistry createTicket(TicketRegistry ticket) {
        return ticketRegistryRepository.save(ticket);
    }

    public TicketRegistry updateTicket(Long id, TicketRegistry ticket) {
        if (ticketRegistryRepository.existsById(id)) {
            ticket.setTicketId(id);
            return ticketRegistryRepository.save(ticket);
        } else {
            return null; // Handle not found case
        }
    }

    public void deleteTicket(Long id) {
    	ticketRegistryRepository.deleteById(id);
    }
}
