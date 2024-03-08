package com.cgtravelokaservice.repo;

import com.cgtravelokaservice.entity.hotel.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelRepo extends JpaRepository <Hotel, Integer> {
}
