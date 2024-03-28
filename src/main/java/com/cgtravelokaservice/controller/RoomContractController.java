package com.cgtravelokaservice.controller;

import com.cgtravelokaservice.dto.request.RoomContractRegisterFormDTO;
import com.cgtravelokaservice.dto.response.BookingResponseDTO;
import com.cgtravelokaservice.entity.booking.RoomContract;
import com.cgtravelokaservice.entity.user.Customer;
import com.cgtravelokaservice.repo.CustomerRepo;
import com.cgtravelokaservice.repo.RoomContractRepo;
import com.cgtravelokaservice.repo.UserRepo;
import com.cgtravelokaservice.service.IRoomContractService;
import com.cgtravelokaservice.util.IConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RoomContractController {
    @Autowired
    private IConvertUtil convertUtil;
    @Autowired
    private IRoomContractService roomContractService;
    @Autowired
    private RoomContractRepo roomContractRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private CustomerRepo customerRepo;

    @PostMapping(value = "/api/contracts")
    public ResponseEntity<?> makeContract(@RequestBody RoomContractRegisterFormDTO roomContractRegisterFormDTO, @AuthenticationPrincipal UserDetails user) {
        RoomContract roomContract = convertUtil.roomContractFormDTOToRoomContract(roomContractRegisterFormDTO);
        boolean isValid = roomContractService.isContractValid(roomContract);
        if (isValid) {
            roomContractRepo.save(roomContract);
            BookingResponseDTO bookingResponseDTO = new BookingResponseDTO();
            bookingResponseDTO.setHotelName(roomContract.getRoom().getHotel().getHotelName());
            bookingResponseDTO.setStatus("Đang đợi thanh toán");
            return ResponseEntity.ok().body("Đặt vé thành công");
        } else {
            return ResponseEntity.ok().body("Không còn phòng khả dụng");
        }
    }

    @GetMapping(value = "/api/contractPending")
    public ResponseEntity<?> getContractPendingByCustomer(@AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        Customer customer = customerRepo.findByUser(userRepo.findByUsername(username).orElse(null));
        List<RoomContract> roomContracts = roomContractRepo.findAllByCustomerIdAndStatus(customer.getId(), "pending");
        List<BookingResponseDTO> bookingResponses = new ArrayList<>();
        for (RoomContract roomContract : roomContracts) {
            BookingResponseDTO bookingResponse = new BookingResponseDTO();
            bookingResponse.setHotelName(roomContract.getRoom().getHotel().getHotelName());
            bookingResponse.setStatus("Đang chờ thanh toán");
            bookingResponses.add(bookingResponse);
        }
        return ResponseEntity.ok().body(bookingResponses);
    }

    @GetMapping(value = "/api/contractBooked")
    public ResponseEntity<?> getContractBookedByCustomer(@AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        Customer customer = customerRepo.findByUser(userRepo.findByUsername(username).orElse(null));
        List<RoomContract> roomContracts = roomContractRepo.findAllByCustomerIdAndStatus(customer.getId(), "booked");
        List<BookingResponseDTO> bookingResponses = new ArrayList<>();
        for (RoomContract roomContract : roomContracts) {
            BookingResponseDTO bookingResponse = new BookingResponseDTO();
            bookingResponse.setHotelName(roomContract.getRoom().getHotel().getHotelName());
            bookingResponse.setStatus("Đã thanh toán");
            bookingResponse.setContractId(roomContract.getId());
            bookingResponses.add(bookingResponse);
        }
        return ResponseEntity.ok().body(bookingResponses);
    }
}
