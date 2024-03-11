package com.cgtravelokaservice.service;

import com.cgtravelokaservice.dto.FlightInformationDto;
import com.cgtravelokaservice.entity.airplant.FlightInformation;
import com.cgtravelokaservice.entity.airplant.SeatInformation;

import java.util.List;

public interface ISeatService {
    public List<SeatInformation> createSeatsForNewFlight(FlightInformationDto flightInformationDto, FlightInformation flightInformation) ;
}
