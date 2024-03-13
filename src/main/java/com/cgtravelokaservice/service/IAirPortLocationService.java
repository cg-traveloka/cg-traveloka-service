package com.cgtravelokaservice.service;

import com.cgtravelokaservice.entity.airplant.AirPortLocation;

import java.util.List;

public interface IAirPortLocationService {
    List<AirPortLocation> getAirPortLocationByCity_Name(String city_name);
}
