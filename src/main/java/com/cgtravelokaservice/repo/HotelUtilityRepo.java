package com.cgtravelokaservice.repo;

import com.cgtravelokaservice.entity.hotel.HotelUtility;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelUtilityRepo extends JpaRepository <HotelUtility, Integer> {
    List<HotelUtility> findAllByHotelUtilityType_Id(Integer hotelUtilityTypeId, Sort sort);
}
