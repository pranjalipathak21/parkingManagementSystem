package com.parkingManagementSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.parkingManagementSystem.entity.VehicleCategory;

public interface VehicleCategoryRepository extends JpaRepository<VehicleCategory, Long> {
}
