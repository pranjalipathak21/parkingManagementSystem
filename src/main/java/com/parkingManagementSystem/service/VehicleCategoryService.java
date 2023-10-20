package com.parkingManagementSystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import com.parkingManagementSystem.entity.VehicleCategory;
import com.parkingManagementSystem.repository.VehicleCategoryRepository;

@Service
public class VehicleCategoryService {
	private final VehicleCategoryRepository vehicleCategoryRepository;

	@Autowired
	public VehicleCategoryService(VehicleCategoryRepository vehicleCategoryRepository) {
		this.vehicleCategoryRepository = vehicleCategoryRepository;
	}

	public List<VehicleCategory> getAllVehicleCategories() {
		return vehicleCategoryRepository.findAll();
	}

	public Optional<VehicleCategory> getVehicleCategoryById(Long id) {
		return vehicleCategoryRepository.findById(id);
	}

	public VehicleCategory createVehicleCategory(VehicleCategory category) {
		return vehicleCategoryRepository.save(category);
	}

	public VehicleCategory updateVehicleCategory(VehicleCategory updatedCategory) {
		return vehicleCategoryRepository.save(updatedCategory);
	}

	public void deleteVehicleCategory(Long id) {
		vehicleCategoryRepository.deleteById(id);
	}
}
