package com.cgtravelokaservice.repo;

import com.cgtravelokaservice.entity.airplant.SeatInformation;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;

public interface SeatInformationRepo extends JpaRepository<SeatInformation, Integer> {
    List<SeatInformation> findByFlightInformationId(Integer flightInformationId);

    Optional<SeatInformation> findByFlightInformationIdAndSeatTypeId(Integer flightInformationId, Integer seatTypeId);

    List<SeatInformation> findByFlightInformation_Id(Integer flightInformationId);

}
