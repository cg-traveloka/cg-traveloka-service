package com.cgtravelokaservice.service.implement;

import com.cgtravelokaservice.entity.airplant.AirPortLocation;
import com.cgtravelokaservice.repo.AirportLocationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AirportLocationService {

    @Autowired
    private AirportLocationRepo airportLocationRepo;

    public List<AirPortLocation> findByCityName(String name) {
        return airportLocationRepo.getAllByCity_Name(name);
    }
}
