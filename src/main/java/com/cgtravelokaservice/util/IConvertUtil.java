package com.cgtravelokaservice.util;

import com.cgtravelokaservice.dto.AirplaneBrandDto;
import com.cgtravelokaservice.dto.FlightInformationDetailedDto;
import com.cgtravelokaservice.dto.FlightInfoSearchDTO;
import com.cgtravelokaservice.dto.FlightInformationRegisterDto;
import com.cgtravelokaservice.dto.HotelRegisterFormDTO;
import com.cgtravelokaservice.dto.RoomRegisterFormDTO;
import com.cgtravelokaservice.dto.SeatDetailsDto;


import com.cgtravelokaservice.dto.TicketAirPlaneDTO;
import com.cgtravelokaservice.dto.request.HotelSearchDTO;
import com.cgtravelokaservice.dto.request.RoomContractRegisterFormDTO;
import com.cgtravelokaservice.entity.airplant.AirPlantBrand;
import com.cgtravelokaservice.entity.airplant.FlightInformation;
import com.cgtravelokaservice.entity.airplant.SeatInformation;
import com.cgtravelokaservice.entity.booking.RoomContract;
import com.cgtravelokaservice.entity.booking.TicketAirPlant;
import com.cgtravelokaservice.entity.hotel.Hotel;
import com.cgtravelokaservice.entity.room.Room;

import java.util.List;

public interface IConvertUtil {
    AirPlantBrand airplaneBrandDtoToAirplaneBrand(AirplaneBrandDto airplaneBrandDto);

    Hotel hotelRegisterFormToHotel(HotelRegisterFormDTO hotelRegisterForm);

    Room roomRegisterFormToRoom(RoomRegisterFormDTO roomRegisterFormDTO);

    FlightInformation convertToNewFlightInformation(FlightInformationRegisterDto flightInformationRegisterDto);
    FlightInfoSearchDTO convertToFlightDetailsDTO(FlightInformation flightInfo, Integer seatTypeId);
    RoomContract roomContractFormDTOToRoomContract(RoomContractRegisterFormDTO roomContractRegisterFormDTO);
    List<SeatDetailsDto> convertSeatInformationToDto(Integer flightId);
    FlightInformationDetailedDto convertToDetailedDto(FlightInformation flightInformation);

    RoomContract convertToRoomContract(Room room, HotelSearchDTO hotelSearchDTO);
     TicketAirPlant convertToTicketAirPlant(TicketAirPlaneDTO ticketDTO, SeatInformation seatInformation);
}
