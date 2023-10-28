package com.parkingManagementSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.parkingManagementSystem.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
