package com.cgtravelokaservice.repo;

import com.cgtravelokaservice.dto.response.HotelRegisterResponse;
import com.cgtravelokaservice.entity.hotel.Hotel;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface HotelRepo extends JpaRepository<Hotel, Integer> {
    Slice<Hotel> findAllByOrderByHotelBookedNumbersDesc(Pageable pageable);

    @Query("SELECT DISTINCT r.hotel FROM Room " + "r" + " " + "WHERE " + "r.hotel" + ".city.id =" + " :cityId AND " + "r" + ".hotel.hotelStar IN :hotelStars " + "AND r.maxPerson >= :personQuantity" + " " + "AND r.unitPriceSell >= :minPrice " + "AND " + "r.unitPriceSell <= :maxPrice AND " + "r.quantity >= :quantity")
    Slice<Hotel> search(Integer cityId, List<Integer> hotelStars, Integer personQuantity, Integer minPrice, Integer maxPrice, Integer quantity, Pageable pageable);

    @Query("SELECT new com.cgtravelokaservice.dto.response.HotelRegisterResponse(h.id, h.hotelName, h.partner.id) FROM " +
            "Hotel h " +
            "WHERE h.partner.id = :partnerId")
    List<HotelRegisterResponse> findAllByPartner_Id(Integer partnerId, Sort sort);

}
