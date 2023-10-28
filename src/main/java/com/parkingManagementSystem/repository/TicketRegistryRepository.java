package com.parkingManagementSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.parkingManagementSystem.entity.TicketRegistry;

public interface TicketRegistryRepository extends JpaRepository<TicketRegistry, Long> {

}
