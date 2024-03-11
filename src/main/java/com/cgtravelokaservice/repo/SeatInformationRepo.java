package com.cgtravelokaservice.repo;

import com.cgtravelokaservice.entity.airplant.SeatInformation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatInformationRepo extends JpaRepository<SeatInformation, Integer> {
}
