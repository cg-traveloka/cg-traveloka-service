package com.cgtravelokaservice.controller;

import com.cgtravelokaservice.dto.request.GetAvailableSeatsRequest;
import com.cgtravelokaservice.service.implement.SeatService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SeatInfoController {
    private final SeatService seatService;

    public SeatInfoController(SeatService seatService) {
        this.seatService = seatService;
    }

    @GetMapping("/api/flights/seats")
    public ResponseEntity <?> getAvailableSeatsByFlight(@Validated @RequestBody GetAvailableSeatsRequest request, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            return ResponseEntity.badRequest().body("Yêu cầu không hợp lệ. Vui lòng xem lại định dạng.");
        }
        try {
            return ResponseEntity.ok(seatService.getAllAvailableSeatsByFlight(request));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Đã xảy ra lỗi khi tìm kiếm ghế ngồi theo chuyến bay.");
        }

    }
}
