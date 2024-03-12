package com.cgtravelokaservice.repo;

import com.cgtravelokaservice.entity.airplant.FlightInformation;
import com.cgtravelokaservice.entity.airplant.SeatInformation;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface SeatInformationRepo extends JpaRepository<SeatInformation, Integer> {
    Optional<SeatInformation> findByFlightInformation_IdAndSeatType_Id(Integer flightInfoId,
                                                                               Integer seatTypeId);
//
//    Page<SeatInformation> findByFlightInformation_StartTimeAndSeatType_Id(LocalDateTime startTime,
//                                                                          Integer seatTypeId);
    List<SeatInformation> findAllByFlightInformation(FlightInformation flightInformation);
}
