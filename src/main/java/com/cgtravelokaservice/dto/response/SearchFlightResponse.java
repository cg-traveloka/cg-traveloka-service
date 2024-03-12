package com.cgtravelokaservice.dto.response;

import com.cgtravelokaservice.dto.AirPlaneSearchDto;
import com.cgtravelokaservice.dto.FlightInFoShortDescription;
import com.cgtravelokaservice.dto.FlightInfoDtoForSearch;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Slice;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchFlightResponse {
    List<AirPlaneSearchDto> airPlaneSearchDtos;
    Slice<FlightInfoDtoForSearch> flightInfoDtoForSearches;
    List<FlightInFoShortDescription> flightInFoShortDescriptions;
}
