package com.parkingManagementSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.parkingManagementSystem.entity.LotChargeSheet;

@Repository
public interface LotChargeSheetRepository extends JpaRepository<LotChargeSheet, Long> {
    // You can add custom query methods here if needed
}
