package com.cgtravelokaservice.dto.response;

import com.cgtravelokaservice.dto.AirPlantSearchDTO;
import com.cgtravelokaservice.dto.FlightDetailsDTO;
import com.cgtravelokaservice.dto.FlightInForShortDescription;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.domain.Slice;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class SearchFlightResponse {

    Slice<FlightDetailsDTO> flightDetailsDTO;
    List<AirPlantSearchDTO> airPlantSearchDTO;
    List<FlightInForShortDescription> flightInForShortDescriptions;
}
