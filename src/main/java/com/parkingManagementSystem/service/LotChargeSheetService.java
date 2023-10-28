package com.parkingManagementSystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.parkingManagementSystem.entity.LotChargeSheet;
import com.parkingManagementSystem.repository.LotChargeSheetRepository;

@Service
public class LotChargeSheetService {

    @Autowired
    private LotChargeSheetRepository lotChargeSheetRepository;

    public List<LotChargeSheet> getAllLotChargeSheets() {
        return lotChargeSheetRepository.findAll();
    }

    public LotChargeSheet getLotChargeSheetById(Long id) {
        return lotChargeSheetRepository.findById(id).orElse(null);
    }

    public LotChargeSheet createLotChargeSheet(LotChargeSheet lotChargeSheet) {
        return lotChargeSheetRepository.save(lotChargeSheet);
    }

    public LotChargeSheet updateLotChargeSheet(Long id, LotChargeSheet lotChargeSheet) {
        if (lotChargeSheetRepository.existsById(id)) {
            lotChargeSheet.setId(id);
            return lotChargeSheetRepository.save(lotChargeSheet);
        } else {
            return null; // Handle not found case
        }
    }

    public void deleteLotChargeSheet(Long id) {
        lotChargeSheetRepository.deleteById(id);
    }
}
