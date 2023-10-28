package com.parkingManagementSystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.parkingManagementSystem.entity.TicketRegistry;
import com.parkingManagementSystem.service.TicketRegistryService;

@ControllerAdvice
@RestController
@CrossOrigin(origins = "*") // Allow requests from any origin
@RequestMapping("/ticketRegistry")
public class TicketRegistryController {
	private final TicketRegistryService ticketRegistryService;
	
    @Autowired
    public TicketRegistryController(TicketRegistryService ticketRegistryService) {
		this.ticketRegistryService = ticketRegistryService;
	}
    
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<String> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
		String errorMessage = ex.getMessage();
		return ResponseEntity.badRequest().body(errorMessage);
	}
	
    @GetMapping("/{id}")
    public ResponseEntity<TicketRegistry> getTicketById(@PathVariable Long id) {
    	TicketRegistry ticketRegistry = ticketRegistryService.getTicketById(id);
        if (ticketRegistry != null) {
            return new ResponseEntity<>(ticketRegistry, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping
    public ResponseEntity<List<TicketRegistry>> getAllTickets() {
        List<TicketRegistry> tickets = ticketRegistryService.getAllTickets();
        return new ResponseEntity<>(tickets, HttpStatus.OK);
    }
    
    @PostMapping
    public ResponseEntity<TicketRegistry> createTicket(@RequestBody TicketRegistry ticket) {
    	TicketRegistry createdTicket = ticketRegistryService.createTicket(ticket);
        return new ResponseEntity<>(createdTicket, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TicketRegistry> updateTicket(
        @PathVariable Long id, @RequestBody TicketRegistry ticket) {
    	TicketRegistry updatedTicket = ticketRegistryService.updateTicket(id, ticket);
        if (updatedTicket != null) {
            return new ResponseEntity<>(updatedTicket, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable Long id) {
        ticketRegistryService.deleteTicket(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
