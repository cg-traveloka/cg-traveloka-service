package com.cgtravelokaservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import com.cgtravelokaservice.dto.AirPlantSearchDTO;
import com.cgtravelokaservice.dto.FlightInfoSearchDTO;
import com.cgtravelokaservice.dto.FlightInForShortDescription;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Slice;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Builder


public class SearchFlightResponse {
    List<FlightInfoSearchDTO> flightDetailsDTO;
    List<AirPlantSearchDTO> airPlantSearchDTO;
    List<FlightInForShortDescription> flightInForShortDescriptions;
}
