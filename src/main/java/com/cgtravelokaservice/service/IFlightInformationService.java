package com.cgtravelokaservice.service;

import com.cgtravelokaservice.dto.FlightInformationDto;
import com.cgtravelokaservice.entity.airplant.FlightInformation;

import java.util.Optional;

public interface IFlightInformationService {
    FlightInformation convertToNewFlightInformation(FlightInformationDto flightInformationDto);
}
