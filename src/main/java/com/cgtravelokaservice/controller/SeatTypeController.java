package com.cgtravelokaservice.controller;

import com.cgtravelokaservice.entity.airplant.SeatType;
import com.cgtravelokaservice.service.ISeatTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SeatTypeController {

    @Autowired
    private ISeatTypeService seatTypeService;

    @GetMapping("/api/seat-types")
    public ResponseEntity<?> getAllSeatTypes() {
        try {
            List<SeatType> seatTypes = seatTypeService.getAllSeatType();
            return ResponseEntity.ok().body(seatTypes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Đã xảy ra lỗi khi lấy danh sách loại ghế: " + e.getMessage());
        }
    }


}
