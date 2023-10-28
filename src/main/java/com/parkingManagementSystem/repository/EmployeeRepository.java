package com.parkingManagementSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.parkingManagementSystem.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long>{
}
