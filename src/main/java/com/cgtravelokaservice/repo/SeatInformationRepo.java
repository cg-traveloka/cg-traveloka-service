package com.cgtravelokaservice.repo;

import com.cgtravelokaservice.entity.airplant.SeatInformation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SeatInformationRepo extends JpaRepository<SeatInformation, Integer> {
    Optional<SeatInformation> findByFlightInformation_IdAndSeatType_Id(Integer flightInfoId,
                                                                               Integer seatTypeId);
}
