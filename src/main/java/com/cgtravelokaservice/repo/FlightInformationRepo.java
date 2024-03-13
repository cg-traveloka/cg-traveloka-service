package com.cgtravelokaservice.repo;

import com.cgtravelokaservice.entity.airplant.FlightInformation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;
import java.util.List;

public interface FlightInformationRepo extends JpaRepository<FlightInformation, Integer> {
    @Query(value = "SELECT fi.* " +
            "FROM flight_information AS fi " +
            "INNER JOIN air_port_location AS fa ON fi.from_airport_location_id = fa.id " +
            "INNER JOIN seat_information AS si ON fi.id = si.flight_id " +
            "WHERE (fi.air_plant_brand_id = :airplaneId OR :airplaneId IS NULL) " +
            "AND fa.id = :fromLoId " +
            "AND fi.to_airport_location_id = :toLoId " +
            "AND DATE(fi.start_time) >= DATE(:dateStart) " +
            "AND si.quantity >= :quantity " +
            "AND si.seat_type_id = :seatTypeId " +
            "AND ((UNIX_TIMESTAMP(end_time) - UNIX_TIMESTAMP(start_time)) / 3600) BETWEEN COALESCE(0,:durationFrom) " +
            "AND COALESCE" +
            "(:durationTo, 1000) " +
            "AND si.unit_price BETWEEN COALESCE(0,:priceFrom) AND COALESCE(:priceTo, 1000000000) " +
            "ORDER BY " +
            "CASE WHEN :sortBy = 'duration' THEN (DATE(fi.end_time) - DATE(fi.start_time)) END," +
            "CASE WHEN :sortBy = 'unit_price' THEN si.unit_price END ," +
            "CASE WHEN :sortBy = 'start_time' AND :order = 'desc' THEN fi.start_time END DESC ," +
            "CASE WHEN :sortBy = 'end_time' AND :order = 'desc' THEN fi.end_time END DESC", nativeQuery = true)
    Slice<FlightInformation> search(
            @Param("fromLoId") Integer fromLoId,
            @Param("toLoId") Integer toLoId,
            @Param("dateStart") LocalDateTime dateStart,
            @Param("quantity") Integer quantity,
            @Param("seatTypeId") Integer seatTypeId,
            @Param("airplaneId") @Nullable Integer airplaneId,
            @Param("sortBy") @Nullable String sortBy,
            @Param("order") @Nullable String order,
            @Param("durationFrom") Integer durationFrom,
            @Param("durationTo") @Nullable Integer durationTo,
            @Param("priceFrom") @Nullable Integer priceFrom,
            @Param("priceTo") @Nullable Integer priceTo,
            Pageable pageable);



    default List<FlightInformation> searchToList(
            Integer fromLoId,
            Integer toLoId,
            LocalDateTime dateStart,
            Integer quantity,
            Integer seatTypeId,
            Integer airplaneId) {
        return search(fromLoId, toLoId, dateStart, quantity, seatTypeId, airplaneId, null, null,
                0, null, 0,null, Pageable.unpaged()).getContent();
    }
}
