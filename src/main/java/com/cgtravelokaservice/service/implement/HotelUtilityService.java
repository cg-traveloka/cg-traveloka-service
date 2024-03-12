package com.cgtravelokaservice.service.implement;

import com.cgtravelokaservice.dto.HotelRegisterFormDTO;
import com.cgtravelokaservice.entity.hotel.Hotel;
import com.cgtravelokaservice.entity.hotel.HotelHotelUtility;
import com.cgtravelokaservice.repo.HotelUtilityRepo;
import com.cgtravelokaservice.service.IHotelUtilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HotelUtilityService implements IHotelUtilityService {
    @Autowired
    HotelUtilityRepo hotelUtilityRepo;

    public List <HotelHotelUtility> createUtilitiesForNewHotel(Hotel hotel, HotelRegisterFormDTO hotelRegisterForm) {
        List <HotelHotelUtility>
                hotelHotelUtilities =
                new ArrayList <>();
        List <Integer> utilitiesId =
                hotelRegisterForm.getUtilitiesId();
        for (Integer utilityId : utilitiesId) {
            HotelHotelUtility hotelHotelUtility =
                    new HotelHotelUtility();
            hotelHotelUtility.setHotel(hotel);
            hotelHotelUtility.setHotelUtility(hotelUtilityRepo.getReferenceById(utilityId));
            hotelHotelUtilities.add(hotelHotelUtility);
        }
        return hotelHotelUtilities;
    }
}
