package com.parkingManagementSystem.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.parkingManagementSystem.entity.VehicleCategory;
import com.parkingManagementSystem.service.VehicleCategoryService;

@RestController
@CrossOrigin(origins = "*") // Allow requests from any origin
@RequestMapping("/vehicle-categories")
public class VehicleCategoryController {

	private final VehicleCategoryService vehicleCategoryService;
	// Implement CRUD endpoints using @GetMapping, @PostMapping, @PutMapping,
	// @DeleteMapping
	@Autowired
	public VehicleCategoryController(VehicleCategoryService vehicleCategoryService) {
		this.vehicleCategoryService = vehicleCategoryService;
	}

	// Get all vehicle categories
	@GetMapping
	public ResponseEntity<List<VehicleCategory>> getAllVehicleCategories() {
		List<VehicleCategory> categories = vehicleCategoryService.getAllVehicleCategories();
		return new ResponseEntity<>(categories, HttpStatus.OK);
	}

	// Get a single vehicle category by ID
	@GetMapping("/{id}")
	public ResponseEntity<VehicleCategory> getVehicleCategory(@PathVariable Long id) {
		Optional<VehicleCategory> category = vehicleCategoryService.getVehicleCategoryById(id);
		if (category.isPresent()) {
			return new ResponseEntity<>(category.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	// Create a new vehicle category
	@PostMapping
	public VehicleCategory createVehicleCategory(@RequestBody VehicleCategory category) {
		return vehicleCategoryService.createVehicleCategory(category);
	}

	// Update an existing vehicle category
	@PutMapping("/{id}")
	public ResponseEntity<VehicleCategory> updateVehicleCategory(@PathVariable Long id,
			@RequestBody VehicleCategory updatedCategory) {
		Optional<VehicleCategory> category = vehicleCategoryService.getVehicleCategoryById(id);
		if (category.isPresent()) {
			updatedCategory.setCategoryId(id);
			VehicleCategory updated = vehicleCategoryService.updateVehicleCategory(updatedCategory);
			return new ResponseEntity<>(updated, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	// Delete a vehicle category by ID
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteVehicleCategory(@PathVariable Long id) {
		Optional<VehicleCategory> category = vehicleCategoryService.getVehicleCategoryById(id);
		if (category.isPresent()) {
			vehicleCategoryService.deleteVehicleCategory(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
