package com.parkingManagementSystem.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.parkingManagementSystem.entity.Employee;
import com.parkingManagementSystem.entity.ParkingLot;
import com.parkingManagementSystem.entity.Role;
import com.parkingManagementSystem.repository.EmployeeRepository;
import com.parkingManagementSystem.repository.ParkingLotRepository;
import com.parkingManagementSystem.repository.RoleRepository;

@Service
public class EmployeeService {
	private final EmployeeRepository employeeRepository;
	private final ParkingLotRepository parkingLotRepository;
	private final RoleRepository roleRepository;

	@Autowired
	public EmployeeService(EmployeeRepository employeeRepository, ParkingLotRepository parkingLotRepository,
			RoleRepository roleRepository) {
		this.employeeRepository = employeeRepository;
		this.parkingLotRepository = parkingLotRepository;
		this.roleRepository = roleRepository;
	}

	public List<Employee> getAllEmployees() {
		return employeeRepository.findAll();

	}

	public Employee get(Long id) {
		Optional<Employee> employeeOptional = employeeRepository.findById(id);
		return employeeOptional.orElse(null);
	}

	public Employee create(Employee employee) {
		Employee savedEmployee = employeeRepository.save(employee);
		ParkingLot parkingLot = parkingLotRepository.findById(savedEmployee.getParkingLot().getLotId()).orElse(null);
		savedEmployee.setParkingLot(parkingLot);
		Role role = roleRepository.findById(savedEmployee.getRole().getRoleId()).orElse(null);
		savedEmployee.setRole(role);
		return savedEmployee;
	}

	public Employee update(Long id, Employee updatedEmployee) {
		Optional<Employee> employeeOptional = employeeRepository.findById(id);
		if (employeeOptional.isPresent()) {
			Employee existingEmployee = employeeOptional.get();
			existingEmployee.setAadharNo(updatedEmployee.getAadharNo());
			existingEmployee.setName(updatedEmployee.getName());
			existingEmployee.setParkingLot(updatedEmployee.getParkingLot());
			existingEmployee.setRole(updatedEmployee.getRole());

			Employee employee = employeeRepository.save(existingEmployee);
			ParkingLot parkingLot = parkingLotRepository.findById(employee.getParkingLot().getLotId()).orElse(null);
			employee.setParkingLot(parkingLot);
			Role role = roleRepository.findById(employee.getRole().getRoleId()).orElse(null);
			employee.setRole(role);
			return employee;
		} else {
			throw new DataIntegrityViolationException("Employee Does not Exists");
		}
	}

	public void delete(Long id) {
		employeeRepository.deleteById(id);
	}
}
