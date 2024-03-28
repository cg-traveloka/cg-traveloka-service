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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@RestController
public class RoomContractController {
    @Autowired
    private IConvertUtil convertUtil;
    @Autowired
    private IRoomContractService
            roomContractService;
    @Autowired
    private RoomContractRepo roomContractRepo;
    @Autowired
    private CustomerRepo customerRepo;
    @Autowired
    private UserRepo userRepo;

    @PostMapping(value = "/api/contracts")
    public ResponseEntity <?> makeContract(@RequestBody RoomContractRegisterFormDTO roomContractRegisterFormDTO, @AuthenticationPrincipal UserDetails userDetails) {
        Customer customer =
                customerRepo.findByUserUsername(userDetails.getUsername());
        RoomContract roomContract =
                convertUtil.roomContractFormDTOToRoomContract(roomContractRegisterFormDTO);
        roomContract.setCustomer(customer);
        boolean isValid =
                roomContractService.isContractValid(roomContract);
        if (isValid) {
            roomContractRepo.save(roomContract);
            BookingResponseDTO
                    bookingResponseDTO =
                    new BookingResponseDTO();
            bookingResponseDTO.setHotelName(roomContract.getRoom().getHotel().getHotelName());
            bookingResponseDTO.setStatus("Đang đợi thanh toán");
            return ResponseEntity.ok().body("Đặt vé thành công");
        } else {
            return ResponseEntity.ok().body("Không còn phòng khả dụng");
        }
    }

    @GetMapping(value = "/api/contractPending/customer/{customerId}")
    public ResponseEntity <?> getContractPendingByCustomer(@PathVariable Integer customerId) {
        List <RoomContract> roomContracts =
                roomContractRepo.findAllByCustomerIdAndStatus(customerId, "pending");
        List <BookingResponseDTO>
                bookingResponses =
                new ArrayList <>();
        for (RoomContract roomContract : roomContracts) {
            BookingResponseDTO bookingResponse =
                    new BookingResponseDTO();
            bookingResponse.setHotelName(roomContract.getRoom().getHotel().getHotelName());
            bookingResponse.setStatus("Đang chờ thanh toán");
            bookingResponses.add(bookingResponse);
        }
        return ResponseEntity.ok().body(bookingResponses);
    }

    @GetMapping(value = "/api/contractBooked/customer/{customerId}")
    public ResponseEntity <?> getContractBookedByCustomer(@PathVariable Integer customerId) {
        List <RoomContract> roomContracts =
                roomContractRepo.findAllByCustomerIdAndStatus(customerId, "booked");
        List <BookingResponseDTO>
                bookingResponses =
                new ArrayList <>();
        for (RoomContract roomContract : roomContracts) {
            BookingResponseDTO bookingResponse =
                    new BookingResponseDTO();
            bookingResponse.setHotelName(roomContract.getRoom().getHotel().getHotelName());
            bookingResponse.setStatus("Đã thanh toán");
            bookingResponses.add(bookingResponse);
        }
        return ResponseEntity.ok().body(bookingResponses);
    }
}
