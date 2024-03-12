package com.cgtravelokaservice.service;

import com.cgtravelokaservice.dto.FlightDetailsDTO;
import com.cgtravelokaservice.dto.request.FlightDetailsRequestDTO;
import com.cgtravelokaservice.dto.response.SearchFlightResponse;
import com.cgtravelokaservice.entity.airplant.FlightInformation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface IFlightInformationService {
    void saveFlightInformation (FlightInformation flightInformation);
    Slice<FlightDetailsDTO> searchFlights (FlightDetailsRequestDTO request, Pageable pageable);


    List<FlightInformation> searchList(FlightDetailsRequestDTO request);



    SearchFlightResponse loadSearchFlightResponse(FlightDetailsRequestDTO request);
}
