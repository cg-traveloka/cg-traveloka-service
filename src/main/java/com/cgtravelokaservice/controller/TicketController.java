package com.cgtravelokaservice.controller;

import com.cgtravelokaservice.dto.TicketAirPlaneDTO;
import com.cgtravelokaservice.dto.response.BookingResponseDTO;
import com.cgtravelokaservice.entity.booking.TicketAirPlant;
import com.cgtravelokaservice.entity.user.Customer;
import com.cgtravelokaservice.entity.user.User;
import com.cgtravelokaservice.repo.CustomerRepo;
import com.cgtravelokaservice.repo.TicketAirPlaneRepo;
import com.cgtravelokaservice.repo.UserRepo;
import com.cgtravelokaservice.service.implement.TicketAirPlaneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TicketController {
    @Autowired
    private TicketAirPlaneService ticketAirPlaneService;
    @Autowired
    private TicketAirPlaneRepo ticketAirPlaneRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private CustomerRepo customerRepo;

    @PostMapping("/api/ticket")
    public ResponseEntity<?> bookFlight(@Validated @RequestBody TicketAirPlaneDTO ticketDTO, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
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

    @GetMapping("/api/ticketPending")
    public ResponseEntity<?> getTicketPendingByCustomer(@AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        Customer customer = customerRepo.findByUser(userRepo.findByUsername(username).orElse(null));
        List<TicketAirPlant> tickets = ticketAirPlaneRepo.findAllByCustomerIdAndStatus(customer.getId(), "pending");
        List<BookingResponseDTO> bookingResponses = new ArrayList<>();
        for (TicketAirPlant ticket : tickets) {
            BookingResponseDTO bookingResponse = new BookingResponseDTO();
            bookingResponse.setFlightNameFromCity(ticket.getFlightInformation().getFromAirPortLocation().getCity().getName());
            bookingResponse.setFlightNameToCity(ticket.getFlightInformation().getToAirPortLocation().getCity().getName());
            bookingResponse.setStatus("Đang đợi thanh toán");
            bookingResponses.add(bookingResponse);
        }
        return ResponseEntity.ok().body(bookingResponses);
    }

    @GetMapping("/api/ticketBooked")
    public ResponseEntity<?> getTicketBookedByCustomer(@AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        Customer customer = customerRepo.findByUser(userRepo.findByUsername(username).orElse(null));
        List<TicketAirPlant> tickets = ticketAirPlaneRepo.findAllByCustomerIdAndStatus(customer.getId(), "booked");
        List<BookingResponseDTO> bookingResponses = new ArrayList<>();
        for (TicketAirPlant ticket : tickets) {
            BookingResponseDTO bookingResponse = new BookingResponseDTO();
            bookingResponse.setFlightNameFromCity(ticket.getFlightInformation().getFromAirPortLocation().getCity().getName());
            bookingResponse.setFlightNameToCity(ticket.getFlightInformation().getToAirPortLocation().getCity().getName());
            bookingResponse.setStatus("Đã thanh toán");
            bookingResponses.add(bookingResponse);
        }
        return ResponseEntity.ok().body(bookingResponses);
    }
}
