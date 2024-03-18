package com.cgtravelokaservice.repo;

import com.cgtravelokaservice.entity.airplant.SeatInformation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface SeatInformationRepo extends JpaRepository <SeatInformation, Integer> {
    List <SeatInformation> findByFlightInformationId(Integer flightInformationId);

    Optional <SeatInformation> findByFlightInformationIdAndSeatTypeId(Integer flightInformationId, Integer seatTypeId);

    List <SeatInformation> findByFlightInformation_Id(Integer flightInformationId);

    @Query("SELECT s from  SeatInformation s " + "WHERE s.flightInformation" + ".fromAirPortLocation.id = " + ":fromAirportLocationId AND " + "s.flightInformation" + ".toAirPortLocation.id = " + ":toAirportLocationId AND " + "s.flightInformation.startTime >= " + ":startTime AND " + "s.seatType.id = :seatTypeId AND " + "s.quantity >= :seatQuantity")
    List <SeatInformation> findSeatByRequest(Integer fromAirportLocationId, Integer toAirportLocationId, LocalDateTime startTime, Integer seatTypeId, Integer seatQuantity, Pageable pageable);
}
