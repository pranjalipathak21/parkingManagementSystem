package com.parkingManagementSystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.parkingManagementSystem.entity.TicketRegistry;

public interface TicketRegistryRepository extends JpaRepository<TicketRegistry, Long> {

	@Query(value = "SELECT * FROM Lot_Daily_Revenue WHERE Revenue_Lot_Id = :lotId", nativeQuery = true)
	List<Object[]> getLotDailyRevenueByLotId(@Param("lotId") Long lotId);
}
