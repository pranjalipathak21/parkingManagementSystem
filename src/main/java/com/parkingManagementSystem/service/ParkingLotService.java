package com.parkingManagementSystem.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.parkingManagementSystem.entity.ParkingLot;
import com.parkingManagementSystem.repository.ParkingLotRepository;

@Service
public class ParkingLotService {

	private final ParkingLotRepository parkingLotRepository;

	@Autowired
	public ParkingLotService(ParkingLotRepository parkingLotRepository) {
		this.parkingLotRepository = parkingLotRepository;
	}

	public List<ParkingLot> getAllParkingLot() {
		return parkingLotRepository.findAll();

	}

	public ParkingLot get(Long id) {
		Optional<ParkingLot> parkingLotOptional = parkingLotRepository.findById(id);
		return parkingLotOptional.orElse(null);
	}

	public ParkingLot create(ParkingLot parkingLot) {
		// You may want to add validation or additional logic here
		return parkingLotRepository.save(parkingLot);
	}

	public ParkingLot update(Long id, ParkingLot updatedParkingLot) {
		Optional<ParkingLot> parkingLotOptional = parkingLotRepository.findById(id);
		if (parkingLotOptional.isPresent()) {
			ParkingLot existingParkingLot = parkingLotOptional.get();
			existingParkingLot.setArea(updatedParkingLot.getArea());
			existingParkingLot.setCity(updatedParkingLot.getCity());
			existingParkingLot.setPostalCode(updatedParkingLot.getPostalCode());
			existingParkingLot.setFloors(updatedParkingLot.getFloors());
			existingParkingLot.setSlots(updatedParkingLot.getSlots());
			return parkingLotRepository.save(existingParkingLot);
		} else {
			return null; // Handle the case when the parking lot with the given ID doesn't exist
		}
	}

	public void delete(Long id) {
		parkingLotRepository.deleteById(id);
	}
}
