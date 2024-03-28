package com.cgtravelokaservice.service;

import com.cgtravelokaservice.dto.AirPortLocationDTO;
import com.cgtravelokaservice.entity.airplant.AirPortLocation;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface IAirPortLocationService {
    List <AirPortLocation> getAirPortLocationByCityId(Integer cityId);
    List<AirPortLocationDTO> getAllAirPortLocations();
    List <AirPortLocation> getAllAirportLocation();
    List<AirPortLocation> getAllByNameContains(String name);
}
