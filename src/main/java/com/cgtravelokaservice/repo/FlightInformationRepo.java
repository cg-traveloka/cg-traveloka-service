package com.cgtravelokaservice.repo;

import com.cgtravelokaservice.entity.airplant.FlightInformation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;
import org.springframework.security.access.method.P;

import java.time.LocalDateTime;
import java.util.List;

public interface FlightInformationRepo extends JpaRepository<FlightInformation, Integer> {
    @Query(value = "SELECT fi.* " +
            "FROM flight_information AS fi " +
            "INNER JOIN air_port_location AS fa ON fi.from_airport_location_id = fa.id " +
            "INNER JOIN air_plant_brand AS ap ON fi.air_plant_brand_id = ap.id " +
            "INNER JOIN seat_information AS si ON fi.id = si.flight_id " +
            "INNER JOIN seat_type AS st ON si.seat_type_id = st.id " +
            "WHERE (ap.id = :airplaneId OR :airplaneId IS NULL) " +
            "AND fa.id = :fromLoId " +
            "AND fi.to_airport_location_id = :toLoId " +
            "AND DATE(fi.start_time) >= DATE(:dateStart) " +
            "AND si.quantity >= :quantity " +
            "AND st.id = :seatTypeId ", nativeQuery = true)
    Slice<FlightInformation> search(
            @Param("fromLoId") Integer fromLoId,
            @Param("toLoId") Integer toLoId,
            @Param("dateStart") LocalDateTime dateStart,
            @Param("quantity") Integer quantity,
            @Param("seatTypeId") Integer seatTypeId,
            @Param("airplaneId") @Nullable Integer airplaneId,
            Pageable pageable);
}
