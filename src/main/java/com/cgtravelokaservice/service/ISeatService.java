package com.cgtravelokaservice.service;

import com.cgtravelokaservice.dto.FlightInformationRegisterDto;
import com.cgtravelokaservice.dto.request.SearchFlightDetailsRequestDTO;
import com.cgtravelokaservice.entity.airplant.FlightInformation;
import com.cgtravelokaservice.entity.airplant.SeatInformation;

import java.util.List;

public interface ISeatService {
    public List <SeatInformation> createSeatsForNewFlight(FlightInformationRegisterDto flightInformationRegisterDto, FlightInformation flightInformation);

    List <SeatInformation> findAllAvailableSeatByRequest(SearchFlightDetailsRequestDTO flightRequest, Integer page);
}
