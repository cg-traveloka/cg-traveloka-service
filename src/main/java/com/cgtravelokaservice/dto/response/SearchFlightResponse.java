package com.cgtravelokaservice.dto.response;

import com.cgtravelokaservice.dto.AirPlantSearchDTO;
import com.cgtravelokaservice.dto.FlightInfoSearchDTO;
import com.cgtravelokaservice.dto.FlightInForShortDescription;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Slice;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class SearchFlightResponse {

    Slice<FlightInfoSearchDTO> flightDetailsDTO;
    List<AirPlantSearchDTO> airPlantSearchDTO;
    List<FlightInForShortDescription> flightInForShortDescriptions;
}
