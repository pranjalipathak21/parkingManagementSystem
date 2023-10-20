package com.parkingManagementSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.parkingManagementSystem.entity.ParkingLot;

public interface ParkingLotRepository extends JpaRepository<ParkingLot, Long> {
}
