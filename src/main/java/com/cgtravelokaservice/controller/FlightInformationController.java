package com.cgtravelokaservice.controller;

import com.cgtravelokaservice.dto.FlightInformationDto;
import com.cgtravelokaservice.entity.airplant.FlightInformation;
import com.cgtravelokaservice.service.implement.FlightInformationService;
import com.cgtravelokaservice.service.implement.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.cgtravelokaservice.util.implement.ConvertUtil;

@RestController
public class FlightInformationController {
    private final FlightInformationService flightInformationService;
    private final SeatService seatService;

    private final ConvertUtil convertUtil;

    @Autowired
    public FlightInformationController(FlightInformationService flightInformationService, SeatService seatService, ConvertUtil convertUtil) {
        this.flightInformationService = flightInformationService;
        this.seatService = seatService;
        this.convertUtil = convertUtil;
    }

    @PostMapping(value = "/api/flights", consumes = "application/json")

    public ResponseEntity<?> createFlightAndSeats(@Validated @RequestBody FlightInformationDto flightInformationDto) {
        try {
            // Tạo thông tin chuyến bay
            FlightInformation flightInformation = convertUtil.convertToNewFlightInformation(flightInformationDto);
            // Lưu thông tin chuyến bay vào cơ sở dữ liệu
            flightInformationService.saveFlightInformation(flightInformation);

            // Tạo các ghế cho chuyến bay và gán thông tin chuyến bay vào
            seatService.createSeatsForNewFlight(flightInformationDto, flightInformation);

            return ResponseEntity.ok("Flight and seats created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create flight and seats");
        }
    }
}
