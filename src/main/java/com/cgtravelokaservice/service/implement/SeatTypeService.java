package com.cgtravelokaservice.service.implement;

import com.cgtravelokaservice.entity.airplant.SeatType;
import com.cgtravelokaservice.repo.SeatTypeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SeatTypeService implements com.cgtravelokaservice.service.ISeatTypeService {

    @Autowired
    private SeatTypeRepo seatTypeRepo;

    @Override
    public List<SeatType> getAllSeatType(){
        return seatTypeRepo.findAll();
    }
}
