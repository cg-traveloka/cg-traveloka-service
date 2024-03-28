package com.cgtravelokaservice.service.implement;

import com.cgtravelokaservice.dto.AirPortLocationDTO;
import com.cgtravelokaservice.entity.airplant.AirPortLocation;
import com.cgtravelokaservice.repo.AirportLocationRepo;
import com.cgtravelokaservice.service.IAirPortLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AirPortLocationService implements IAirPortLocationService {
    @Autowired
    private AirportLocationRepo
            airportLocationRepo;

    @Override
    public List <AirPortLocation> getAirPortLocationByCityId(Integer cityId) {
        return airportLocationRepo.getAirPortLocationByCityId(cityId);
    }
    public List<AirPortLocationDTO> getAllAirPortLocations() {
        List<AirPortLocation> airPortLocations = airportLocationRepo.findAll();
        return airPortLocations.stream()
                .map(airPortLocation -> new AirPortLocationDTO(airPortLocation.getId(), airPortLocation.getCity().getName(), airPortLocation.getName()))
                .collect(Collectors.toList());
    }


}
