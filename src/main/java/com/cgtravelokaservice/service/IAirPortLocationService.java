package com.cgtravelokaservice.service;

import com.cgtravelokaservice.entity.airplant.AirPortLocation;

import java.util.List;

public interface IAirPortLocationService {
    List <AirPortLocation> getAirPortLocationByCityId(Integer cityId);
}
