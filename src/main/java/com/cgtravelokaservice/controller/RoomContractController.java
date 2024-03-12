package com.cgtravelokaservice.controller;

import com.cgtravelokaservice.dto.request.RoomContractRegisterFormDTO;
import com.cgtravelokaservice.entity.booking.RoomContract;
import com.cgtravelokaservice.repo.RoomContractRepo;
import com.cgtravelokaservice.service.IRoomContractService;
import com.cgtravelokaservice.util.IConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoomContractController {
    @Autowired
    private IConvertUtil convertUtil;
    @Autowired
    private IRoomContractService
            roomContractService;
    @Autowired
    private RoomContractRepo roomContractRepo;

    @PostMapping(value = "/api/contracts")
    public ResponseEntity <?> makeContract(@RequestBody RoomContractRegisterFormDTO roomContractRegisterFormDTO) {
        RoomContract roomContract =
                convertUtil.roomContractFormDTOToRoomContract(roomContractRegisterFormDTO);
        boolean isValid =
                roomContractService.isContractValid(roomContract);
        if (isValid) {
            roomContractRepo.save(roomContract);
            return ResponseEntity.ok().body("Đặt vé thành công");
        } else {
            return ResponseEntity.ok().body("Không còn phòng khả dụng");
        }
    }
}
