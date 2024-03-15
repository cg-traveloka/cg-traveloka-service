package com.cgtravelokaservice.service.implement;

import com.cgtravelokaservice.entity.airplant.AirPortLocation;
import com.cgtravelokaservice.repo.AirportLocationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AirPortLocationService implements com.cgtravelokaservice.service.IAirPortLocationService {
    @Autowired
    private AirportLocationRepo airportLocationRepo;

    @Override
    public List<AirPortLocation> getAirPortLocationByCity_Name(String city_name) {
        return airportLocationRepo.getAirPortLocationByCity_Name(city_name);
    }
}
