package com.cgtravelokaservice.util;

import com.cgtravelokaservice.dto.AirplaneBrandDto;
import com.cgtravelokaservice.dto.FlightInformationDto;
import com.cgtravelokaservice.dto.HotelRegisterFormDTO;
import com.cgtravelokaservice.dto.RoomRegisterFormDTO;
import com.cgtravelokaservice.entity.airplant.AirPlantBrand;
import com.cgtravelokaservice.entity.airplant.FlightInformation;
import com.cgtravelokaservice.entity.hotel.Hotel;
import com.cgtravelokaservice.entity.room.Room;

public interface IConvertUtil {
    AirPlantBrand airplaneBrandDtoToAirplaneBrand(AirplaneBrandDto airplaneBrandDto);

    Hotel hotelRegisterFormToHotel(HotelRegisterFormDTO hotelRegisterForm);

    Room roomRegisterFormToRoom(RoomRegisterFormDTO roomRegisterFormDTO);

    FlightInformation convertToNewFlightInformation(FlightInformationDto flightInformationDto);
}
