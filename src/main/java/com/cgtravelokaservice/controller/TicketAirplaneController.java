package com.cgtravelokaservice.controller;

import com.cgtravelokaservice.dto.TicketAirPlaneDTO;
import com.cgtravelokaservice.service.implement.TicketAirPlaneService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TicketAirplaneController {

    private final TicketAirPlaneService ticketAirplaneService;

    public TicketAirplaneController(TicketAirPlaneService ticketAirplaneService) {this.ticketAirplaneService = ticketAirplaneService;}

    @PostMapping("/api/airplane-tickets/booking")
    public ResponseEntity<?> booking(@Validated @RequestBody TicketAirPlaneDTO ticketAirplaneDto,
                                     BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("Yêu cầu không hợp lệ. Vui lòng xem lại định dạng.");
        }
        try {
            if (ticketAirplaneService.bookFlight(ticketAirplaneDto)) {
                return ResponseEntity.ok().body("Đặt vé máy bay thành công");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Máy bay hiện không đủ số lượng ghế " +
                        "ngồi.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Đặt vé máy bay thất bại");
        }
    }
}
