package com.cgtravelokaservice.controller;

import com.cgtravelokaservice.dto.TicketAirPlaneDTO;
import com.cgtravelokaservice.service.implement.TicketAirPlaneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TicketController {
    @Autowired
    private TicketAirPlaneService ticketAirPlaneService;
    @PostMapping("/api/ticket")
    public ResponseEntity<?> bookFlight(@Validated  @RequestBody TicketAirPlaneDTO ticketDTO, BindingResult bindingResult) {
        if(bindingResult.hasFieldErrors()){
            return ResponseEntity.badRequest().body("Your request is not valid. Please check quantity");
        }
        try {
            boolean isBooked = ticketAirPlaneService.bookFlight(ticketDTO);

            if (isBooked) {
                return ResponseEntity.status(HttpStatus.CREATED).body("Ticket booked successfully.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Failed to book the ticket due to insufficient quantity.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to book the ticket. An error occurred.");
        }
    }
}
