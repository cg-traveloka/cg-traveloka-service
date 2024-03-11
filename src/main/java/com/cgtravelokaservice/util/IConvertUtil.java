package com.cgtravelokaservice.util;

import com.cgtravelokaservice.dto.AirplaneBrandDto;
import com.cgtravelokaservice.dto.FlightInformationDto;
import com.cgtravelokaservice.entity.airplant.AirPlantBrand;
import com.cgtravelokaservice.entity.airplant.FlightInformation;

public interface IConvertUtil {
    AirPlantBrand airplaneBrandDtoToAirplaneBrand(AirplaneBrandDto airplaneBrandDto);

    FlightInformation convertToNewFlightInformation(FlightInformationDto flightInformationDto);
}
