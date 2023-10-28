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

import com.parkingManagementSystem.entity.ParkingLot;
import com.parkingManagementSystem.service.ParkingLotService;

@ControllerAdvice
@RestController
@CrossOrigin(origins = "*") // Allow requests from any origin
@RequestMapping("/parking-lots")
public class ParkingLotController {
	private final ParkingLotService parkingLotService;

	@Autowired
	public ParkingLotController(ParkingLotService parkingLotService) {
		this.parkingLotService = parkingLotService;
	}

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        // Extract the exception message and return it in the response
        String errorMessage = ex.getRootCause().getMessage();
        return ResponseEntity.badRequest().body(errorMessage);
    }
    
	// Get all vehicle categories
	@GetMapping
	public ResponseEntity<List<ParkingLot>> getAllParkingLot() {
		List<ParkingLot> categories = parkingLotService.getAllParkingLot();
		return new ResponseEntity<>(categories, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ParkingLot getParkingLot(@PathVariable Long id) {
		// Implement code to get a parking lot by ID
		return parkingLotService.get(id);
	}

	@PostMapping
	public ParkingLot createParkingLot(@RequestBody ParkingLot parkingLot) {
		// Implement code to create a new parking lot
		return parkingLotService.create(parkingLot);
	}

	@PutMapping("/{id}")
	public ParkingLot updateParkingLot(@PathVariable Long id, @RequestBody ParkingLot parkingLot) {
		// Implement code to update an existing parking lot
		return parkingLotService.update(id, parkingLot);
	}

	@DeleteMapping("/{id}")
	public void deleteParkingLot(@PathVariable Long id) {
		// Implement code to delete a parking lot by ID
		parkingLotService.delete(id);
	}
}
