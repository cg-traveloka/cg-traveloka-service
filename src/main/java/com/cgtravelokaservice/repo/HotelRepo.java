package com.cgtravelokaservice.repo;

import com.cgtravelokaservice.entity.hotel.Hotel;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRepo extends JpaRepository<Hotel, Integer> {
    @Query("SELECT DISTINCT r.hotel FROM Room " + "r" + " " + "WHERE " + "r.hotel" + ".city.id =" + " :cityId AND " + "r" + ".hotel.hotelStar IN :hotelStars " + "AND r.maxPerson >= :personQuantity" + " " + "AND r.unitPriceSell >= :minPrice " + "AND " + "r.unitPriceSell <= :maxPrice AND " + "r.quantity >= :quantity")
    Slice<Hotel> search(Integer cityId, List<Integer> hotelStars, Integer personQuantity, Integer minPrice,
                        Integer maxPrice, Integer quantity, Pageable pageable);
}
