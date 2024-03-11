package com.cgtravelokaservice.repo;

import com.cgtravelokaservice.entity.airplant.AirPortLocation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AirportLocationRepo extends JpaRepository<AirPortLocation, Integer> {
}
