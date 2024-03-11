package com.cgtravelokaservice.controller;

import com.cgtravelokaservice.dto.RoomRegisterFormDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoomController {

    @PostMapping("/api/rooms")
    public ResponseEntity <?> registerRoom(@RequestBody RoomRegisterFormDTO roomRegisterFormDTO) {

    }
}
