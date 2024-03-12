package com.cgtravelokaservice.service;

import com.cgtravelokaservice.dto.DetailedFlightInformationDto;
import org.springframework.data.domain.Slice;

public interface IFlightService {
    Slice<DetailedFlightInformationDto> getAllFlightsSortedByStartDate(int page, int size);
}
