package com.parkingManagementSystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.parkingManagementSystem.entity.LotChargeSheet;
import com.parkingManagementSystem.service.LotChargeSheetService;

@ControllerAdvice
@RestController
@CrossOrigin(origins = "*") // Allow requests from any origin
@RequestMapping("/lotChargeSheets")
public class LotChargeSheetController {

    private final LotChargeSheetService lotChargeSheetService;
    
    @Autowired
    public LotChargeSheetController(LotChargeSheetService lotChargeSheetService) {
		this.lotChargeSheetService = lotChargeSheetService;
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<String> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
		String errorMessage = ex.getMessage();
		return ResponseEntity.badRequest().body(errorMessage);
	}
	
    @GetMapping("/{id}")
    public ResponseEntity<LotChargeSheet> getLotChargeSheetById(@PathVariable Long id) {
        LotChargeSheet lotChargeSheet = lotChargeSheetService.getLotChargeSheetById(id);
        if (lotChargeSheet != null) {
            return new ResponseEntity<>(lotChargeSheet, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<LotChargeSheet>> getAllLotChargeSheets() {
        List<LotChargeSheet> lotChargeSheets = lotChargeSheetService.getAllLotChargeSheets();
        return new ResponseEntity<>(lotChargeSheets, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<LotChargeSheet> createLotChargeSheet(@RequestBody LotChargeSheet lotChargeSheet) {
        LotChargeSheet createdLotChargeSheet = lotChargeSheetService.createLotChargeSheet(lotChargeSheet);
        return new ResponseEntity<>(createdLotChargeSheet, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LotChargeSheet> updateLotChargeSheet(
        @PathVariable Long id, @RequestBody LotChargeSheet lotChargeSheet) {
        LotChargeSheet updatedLotChargeSheet = lotChargeSheetService.updateLotChargeSheet(id, lotChargeSheet);
        if (updatedLotChargeSheet != null) {
            return new ResponseEntity<>(updatedLotChargeSheet, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLotChargeSheet(@PathVariable Long id) {
        lotChargeSheetService.deleteLotChargeSheet(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

