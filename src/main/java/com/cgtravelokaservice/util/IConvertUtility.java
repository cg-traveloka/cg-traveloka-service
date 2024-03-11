package com.cgtravelokaservice.util;

import com.cgtravelokaservice.dto.HotelRegisterFormDTO;
import com.cgtravelokaservice.entity.hotel.Hotel;

public interface IConvertUtility {
    public Hotel hotelRegisterFormToHotel(HotelRegisterFormDTO hotelRegisterForm);
}
