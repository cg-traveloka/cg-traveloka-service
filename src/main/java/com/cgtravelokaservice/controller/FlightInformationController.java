package com.cgtravelokaservice.controller;

import com.cgtravelokaservice.dto.DetailedFlightInformationDto;
import com.cgtravelokaservice.dto.FlightDetailsDTO;
import com.cgtravelokaservice.dto.FlightInformationDto;
import com.cgtravelokaservice.dto.request.FlightDetailsRequestDTO;
import com.cgtravelokaservice.dto.response.SearchFlightResponse;
import com.cgtravelokaservice.entity.airplant.FlightInformation;
import com.cgtravelokaservice.service.IFlightInFormationService;
import com.cgtravelokaservice.service.IFlightService;
import com.cgtravelokaservice.service.implement.SeatService;
import com.cgtravelokaservice.util.implement.ConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class FlightInformationController {
    private final IFlightInFormationService flightInformationService;
    private final SeatService seatService;

    private final ConvertUtil convertUtil;

    private final IFlightService flightService;
    @Autowired
    public FlightInformationController(IFlightInFormationService flightInformationService, SeatService seatService, ConvertUtil convertUtil, IFlightService flightService) {
        this.flightInformationService = flightInformationService;
        this.seatService = seatService;
        this.convertUtil = convertUtil;
        this.flightService = flightService;
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


    @GetMapping("/api/flights/list")
    public ResponseEntity<?> getAllFlightsSortedByStartDate(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
            ) {
        try {
            // Kiểm tra lỗi truyền tham số

            Pageable pageable = PageRequest.of(page, size);
            Slice<DetailedFlightInformationDto> flights = flightService.getAllFlightsSortedByStartDate(page, size);
            return new ResponseEntity<>(flights, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving flights", e);
        }
    }


    @GetMapping("/api/flights/search")
    public ResponseEntity<?> searchFlights(@RequestBody FlightDetailsRequestDTO request,
                                                                 @RequestParam(defaultValue = "0") int page,
                                                                 @RequestParam(defaultValue = "10") int size,
                                                                 @RequestParam(value = "sortBy", defaultValue = "start_time") String sortBy,
                                                                 @RequestParam(value = "order", defaultValue = "asc") String order) {
        try {
            Pageable pageable = null;
            if(order.equalsIgnoreCase("asc")){
                pageable= PageRequest.of(page,size);
            }
            request.setSortBy(sortBy);
            request.setOrder(order);

            Slice<FlightDetailsDTO> flights = flightInformationService.searchFlights(request, pageable);
            System.out.println(request);

            return new ResponseEntity<>(flights, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving flights", e);
        }
    }

    @GetMapping("/api/flights/search2")
    public ResponseEntity<?> searchGeneral(@RequestBody FlightDetailsRequestDTO requestDTO) {
        try {
            SearchFlightResponse response = flightInformationService.loadSearchFlightResponse(requestDTO);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Đã xảy ra lỗi khi tìm kiếm chuyến bay.");
        }
    }


}
