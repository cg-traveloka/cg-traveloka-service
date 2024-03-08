package com.cgtravelokaservice.repo;

import com.cgtravelokaservice.entity.hotel.HotelUtility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelUtilityRepo extends JpaRepository <HotelUtility, Integer> {
}
