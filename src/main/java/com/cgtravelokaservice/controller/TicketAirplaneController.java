package com.cgtravelokaservice.controller;

import com.cgtravelokaservice.dto.TicketAirplaneDto;
import com.cgtravelokaservice.service.implement.TicketAirplaneService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TicketAirplaneController {

    private final TicketAirplaneService ticketAirplaneService;

    public TicketAirplaneController(TicketAirplaneService ticketAirplaneService) {this.ticketAirplaneService = ticketAirplaneService;}

    @PostMapping("/api/airplane-tickets/booking")
    public ResponseEntity<?> booking(@Validated @RequestBody TicketAirplaneDto ticketAirplaneDto,
                                     BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("Invalid request");
        }
        try {
            if (ticketAirplaneService.add(ticketAirplaneDto)) {
                return ResponseEntity.ok().body("Booking airplane ticket success");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Not enough seat");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Fail to book airplane ticket");
        }
    }
}
