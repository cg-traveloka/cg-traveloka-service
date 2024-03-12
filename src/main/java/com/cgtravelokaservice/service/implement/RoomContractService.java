package com.cgtravelokaservice.service.implement;

import com.cgtravelokaservice.entity.booking.RoomContract;
import com.cgtravelokaservice.entity.room.Room;
import com.cgtravelokaservice.repo.RoomContractRepo;
import com.cgtravelokaservice.service.IRoomContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class RoomContractService implements IRoomContractService {
    @Autowired
    RoomContractRepo roomContractRepo;

    public boolean isContractValid(RoomContract roomContract) {
        Room room = roomContract.getRoom();
        LocalDate startDate =
                roomContract.getStartDate();
        LocalDate endDate =
                roomContract.getEndDate();
        List <RoomContract> roomContracts =
                roomContractRepo.findAllByRoomId(room.getId());
        List <RoomContract> roomsNotAvailable =
                new ArrayList <>();
        for (RoomContract roomContract1 : roomContracts) {
            LocalDate startDate1 =
                    roomContract1.getStartDate();
            LocalDate endDate1 =
                    roomContract1.getEndDate();
            if (startDate1.equals(startDate) || endDate1.equals(endDate) || startDate1.isBefore(startDate) && endDate1.isAfter(startDate) || startDate1.isBefore(endDate) && endDate1.isAfter(endDate) || startDate1.isAfter(startDate) && endDate1.isBefore(endDate) || startDate1.isBefore(startDate) && endDate1.isAfter(endDate)) {
                roomsNotAvailable.add(roomContract1);
            }
        }
        if (roomContract.getRoomQuantity() > room.getQuantity() - roomsNotAvailable.size()) {
            return false;
        } else {
            return true;
        }

    }
}
