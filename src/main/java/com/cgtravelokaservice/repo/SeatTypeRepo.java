package com.cgtravelokaservice.repo;

import com.cgtravelokaservice.entity.airplant.SeatType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatTypeRepo extends JpaRepository<SeatType,Integer> {
}
