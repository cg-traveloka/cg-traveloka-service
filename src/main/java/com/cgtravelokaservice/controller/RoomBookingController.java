package com.cgtravelokaservice.controller;

import com.cgtravelokaservice.dto.request.RoomBookingRequestDTO;
import com.cgtravelokaservice.dto.response.RoomBookingResponse;
import com.cgtravelokaservice.dto.response.RoomBookingResponseDTO;
import com.cgtravelokaservice.service.implement.RoomBookingService;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoomBookingController {

    @Autowired
    private  RoomBookingService roomBookingService;

    @GetMapping("/api/getRoom")
    public ResponseEntity<?> bookRoom(@RequestBody @Valid RoomBookingRequestDTO bookingRequest) {
        try {
            if (bookingRequest.getPersonQuantity() < bookingRequest.getRoomQuantity()) {
                return new ResponseEntity<>("Số người đặt phòng phải lớn hơn hoặc bằng số lượng phòng", HttpStatus.BAD_REQUEST);
            }

            RoomBookingResponse responseDTO = roomBookingService.displayListRoom(bookingRequest);
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>("Đã xảy ra lỗi khi xử lý yêu cầu", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
