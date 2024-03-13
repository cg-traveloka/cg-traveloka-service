package com.cgtravelokaservice.service;

import com.cgtravelokaservice.dto.DetailedFlightInformationDto;
import com.cgtravelokaservice.dto.FlightDetailsDTO;
import com.cgtravelokaservice.dto.request.FlightDetailsRequestDTO;
import com.cgtravelokaservice.dto.response.SearchFlightResponse;
import com.cgtravelokaservice.entity.airplant.FlightInformation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface IFlightInFormationService {
    void saveFlightInformation(FlightInformation flightInformation);
    List<FlightInformation> searchList(FlightDetailsRequestDTO request);
    SearchFlightResponse loadSearchFlightResponse(FlightDetailsRequestDTO request);
    Slice<FlightDetailsDTO> searchFlights(FlightDetailsRequestDTO request, Pageable pageable);
}
