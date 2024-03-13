package com.cgtravelokaservice.controller;

import com.cgtravelokaservice.dto.FlightInformationDto;
import com.cgtravelokaservice.dto.FlightInfoSearchDto;
import com.cgtravelokaservice.dto.TicketAirplaneDto;
import com.cgtravelokaservice.dto.request.SearchFlightRequest;
import com.cgtravelokaservice.entity.airplant.FlightInformation;
import com.cgtravelokaservice.service.implement.FlightInformationService;
import com.cgtravelokaservice.service.implement.SeatService;
import com.cgtravelokaservice.service.implement.TicketAirplaneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.cgtravelokaservice.util.implement.ConvertUtil;

@RestController
public class FlightInformationController {
    private final FlightInformationService flightInformationService;
    private final SeatService seatService;
    private final ConvertUtil convertUtil;

    @Autowired
    public FlightInformationController(FlightInformationService flightInformationService, SeatService seatService,
                                       ConvertUtil convertUtil) {
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

    @GetMapping("/api/flights/search")
    public ResponseEntity<?> searchFlight(@Validated @RequestBody SearchFlightRequest request,
                                          BindingResult bindingResult,
                                          @RequestParam(value = "page", defaultValue = "0") int page,
                                          @RequestParam(value = "size", defaultValue = "10") int size) {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("Invalid request");
        }
        try {
            Pageable pageable = PageRequest.of(page, size);
            Slice<FlightInfoSearchDto> data = flightInformationService.search(request, pageable);
            return ResponseEntity.ok(data);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Fail to search flights");
        }

    }

    @GetMapping("/api/flights/search1")
    public ResponseEntity<?> firstSearch(@Validated @RequestBody SearchFlightRequest request,
                                         BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("Invalid request");
        }
        try {
            request.setAirplaneId(null);
            return ResponseEntity.ok(flightInformationService.createFirstSearchResponse(request));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Fail to search flights");
        }
    }


}
