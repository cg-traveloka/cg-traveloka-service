package com.cgtravelokaservice.service;

import com.cgtravelokaservice.dto.FlightInformationDetailedDto;
import org.springframework.data.domain.Slice;

public interface IFlightService {
    Slice<FlightInformationDetailedDto> getAllFlightsSortedByStartDate(int page, int size);
}
