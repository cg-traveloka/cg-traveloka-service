package com.cgtravelokaservice.controller;


import com.cgtravelokaservice.dto.FlightInformationDetailedDto;
import com.cgtravelokaservice.dto.FlightInformationRegisterDto;
import com.cgtravelokaservice.dto.request.SearchFlightDetailsRequestDTO;
import com.cgtravelokaservice.dto.response.ListFlightInformationsDTO;
import com.cgtravelokaservice.dto.response.SearchFlightResponse;
import com.cgtravelokaservice.entity.airplant.FlightInformation;
import com.cgtravelokaservice.service.IFlightInformationService;
import com.cgtravelokaservice.service.IFlightService;
import com.cgtravelokaservice.service.implement.SeatService;
import com.cgtravelokaservice.util.implement.ConvertUtil;
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
import org.springframework.web.server.ResponseStatusException;

import java.util.NoSuchElementException;

@RestController
public class FlightInformationController {
    private final IFlightInformationService flightInformationService;
    private final SeatService seatService;
    private final ConvertUtil convertUtil;
    private final IFlightService flightService;

    @Autowired

    public FlightInformationController(IFlightInformationService flightInformationService, SeatService seatService, ConvertUtil convertUtil, IFlightService flightService) {
        this.flightInformationService = flightInformationService;
        this.seatService = seatService;
        this.convertUtil = convertUtil;
        this.flightService = flightService;
    }

    @PostMapping(value = "/api/flights", consumes = "application/json")

    public ResponseEntity<?> createFlightAndSeats(@Validated @RequestBody FlightInformationRegisterDto flightInformationRegisterDto) {
        try {
            // Tạo thông tin chuyến bay
            FlightInformation flightInformation = convertUtil.convertToNewFlightInformation(flightInformationRegisterDto);
            // Lưu thông tin chuyến bay vào cơ sở dữ liệu
            flightInformationService.saveFlightInformation(flightInformation);

            // Tạo các ghế cho chuyến bay và gán thông tin chuyến bay vào
            seatService.createSeatsForNewFlight(flightInformationRegisterDto, flightInformation);

            return ResponseEntity.ok("Flight and seats created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create flight and seats");
        }
    }


    @GetMapping("/api/flights")
    public ResponseEntity<?> getAllFlightsSortedByStartDate(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        try {
            // Kiểm tra lỗi truyền tham số
            Slice<FlightInformationDetailedDto>
                    flights =
                    flightService.getAllFlightsSortedByStartDate(page, size);
            return new ResponseEntity<>(flights, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Lỗi khi truy xuất thông tin chuyến " + "bay", e);
        }
    }


    @GetMapping("/api/flights/search/filter")
    public ResponseEntity<?> searchFlights(@Validated @RequestBody SearchFlightDetailsRequestDTO request, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            return ResponseEntity.badRequest().body("Request không hợp lệ.");
        }
        try {
            if (request.getPage() != 0) {
                page = request.getPage();
            }
            Pageable pageable =
                    PageRequest.of(page, size);
            ListFlightInformationsDTO list =
                    new ListFlightInformationsDTO();
            list.setFlights(flightInformationService.searchFlights(request, pageable));
            list.setPage(request.getPage());
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Đã xảy ra lỗi khi tìm kiếm chuyến " + "bay.");
        }
    }


    @GetMapping("/api/flights/search")
    public ResponseEntity<?> searchGeneral(@Validated @RequestBody SearchFlightDetailsRequestDTO requestDTO, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            return ResponseEntity.badRequest().body("Request không hợp lệ.");
        }
        try {
            requestDTO.setAirPlantBrandId(null);
            SearchFlightResponse response =
                    flightInformationService.loadSearchFlightResponse(requestDTO);
            return ResponseEntity.ok(response);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Không có chuyến bay phù hợp.");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Đã xảy ra lỗi khi tìm kiếm chuyến " + "bay.");

        }
    }


}
