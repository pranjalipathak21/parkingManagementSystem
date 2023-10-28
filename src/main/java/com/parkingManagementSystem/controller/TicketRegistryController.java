package com.parkingManagementSystem.controller;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RequestParam;
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

	@GetMapping("/generateExitTicket/{id}")
	public ResponseEntity<Map<String, Double>>  getComputedFair(@PathVariable Long id) {
		Double fareAmount = ticketRegistryService.computeFare(id);
		if (fareAmount != null) {
			Map<String, Double> fareResponse = new HashMap<>();
	        fareResponse.put("fareAmount", fareAmount);
	        return new ResponseEntity<>(fareResponse, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping
	public ResponseEntity<List<TicketRegistry>> getAllTickets() {
		List<TicketRegistry> tickets = ticketRegistryService.getAllTickets();
		return new ResponseEntity<>(tickets, HttpStatus.OK);
	}
	

    @GetMapping("/lotDailyRevenue")
    public ResponseEntity<Map<String, String>> getLotDailyRevenueByLotId(@RequestParam Long lotId) {
        List<Object[]> lotDailyRevenueDetails = ticketRegistryService.getLotDailyRevenueByLotId(lotId);
        
        if (!lotDailyRevenueDetails.isEmpty()) {
			Map<String, String> revenueDetails = new HashMap<>();
			
			for (Object[] row : lotDailyRevenueDetails) {
	            Integer revenueLotId = (Integer) row[0];
	            Date revenueDate = (Date) row[1];
	            BigInteger ticketsPaid = (BigInteger) row[2];
	            BigDecimal revenueFare = (BigDecimal) row[3];
				
				revenueDetails.put("Lot_id", revenueLotId.toString());
				revenueDetails.put("Date", revenueDate.toString());
				revenueDetails.put("Tickets paid", ticketsPaid.toString());
				revenueDetails.put("Total fare", revenueFare.toString());
	        }

	        return new ResponseEntity<>(revenueDetails, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
    }

	@PostMapping
	public ResponseEntity<TicketRegistry> createTicket(@RequestBody TicketRegistry ticket) {
		TicketRegistry createdTicket = ticketRegistryService.createTicket(ticket);
		return new ResponseEntity<>(createdTicket, HttpStatus.CREATED);
	}

	@PutMapping
	public ResponseEntity<TicketRegistry> updateTicket(@RequestParam Long id,@RequestParam String paymentStatus) {
		TicketRegistry updatedTicket = ticketRegistryService.updatePaymentStatus(id, paymentStatus);
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
