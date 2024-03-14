package com.cgtravelokaservice.service;

import com.cgtravelokaservice.dto.FlightInfoSearchDTO;
import com.cgtravelokaservice.dto.request.SearchFlightDetailsRequestDTO;
import com.cgtravelokaservice.dto.response.SearchFlightResponse;
import com.cgtravelokaservice.entity.airplant.FlightInformation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;


public interface IFlightInformationService {
    void saveFlightInformation(FlightInformation flightInformation);

    Slice<FlightInfoSearchDTO> searchFlights(SearchFlightDetailsRequestDTO request, Pageable pageable);

    List<FlightInformation> searchList(SearchFlightDetailsRequestDTO request);

    SearchFlightResponse loadSearchFlightResponse(SearchFlightDetailsRequestDTO request);
}


