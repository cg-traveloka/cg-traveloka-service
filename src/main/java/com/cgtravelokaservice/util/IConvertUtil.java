package com.cgtravelokaservice.util;

import com.cgtravelokaservice.dto.AirplaneBrandDto;
import com.cgtravelokaservice.entity.airplant.AirPlantBrand;

public interface IConvertUtil {
    AirPlantBrand airplaneBrandDtoToAirplaneBrand(AirplaneBrandDto airplaneBrandDto);
}
