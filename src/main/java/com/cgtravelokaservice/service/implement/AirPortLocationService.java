package com.cgtravelokaservice.service.implement;

import com.cgtravelokaservice.entity.airplant.AirPortLocation;
import com.cgtravelokaservice.repo.AirportLocationRepo;
import com.cgtravelokaservice.service.IAirPortLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AirPortLocationService implements IAirPortLocationService {
    @Autowired
    private AirportLocationRepo
            airportLocationRepo;

    @Override
    public List <AirPortLocation> getAirPortLocationByCityId(Integer cityId) {
        return airportLocationRepo.getAirPortLocationByCityId(cityId);
    }

    @Override
    public List<AirPortLocation> findByCityId(Integer cityId) {
        return null;
    }
}
