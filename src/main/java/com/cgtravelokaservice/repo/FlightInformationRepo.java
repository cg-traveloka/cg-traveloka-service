package com.cgtravelokaservice.repo;

import com.cgtravelokaservice.entity.airplant.FlightInformation;
import jakarta.annotation.Nullable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface FlightInformationRepo extends JpaRepository<FlightInformation, Integer>{
    Slice<FlightInformation> findAllByOrderByStartTimeAsc(Pageable pageable);
    @Query(value = "SELECT fi.*, (fi.end_time - fi.start_time) AS duration " +
            "FROM flight_information AS fi " +
            "INNER JOIN air_port_location AS fa ON fi.from_airport_location_id = fa.id " +
            "INNER JOIN air_plant_brand AS ap ON fi.air_plant_brand_id = ap.id " +
            "INNER JOIN seat_information AS si ON fi.id = si.flight_id " +
            "INNER JOIN seat_type AS st ON si.seat_type_id = st.id " +
            "WHERE (ap.id = :airPlantBrandId OR :airPlantBrandId IS NULL) " +
            "AND fa.id = :fromAirportLocationId " +
            "AND fi.to_airport_location_id = :toAirportLocationId " +
            "AND DATE(fi.start_time) >= DATE(:startTime) " +
            "AND si.quantity >= :seatQuantity " +
            "AND st.id = :seatTypeId " +
            "AND ((UNIX_TIMESTAMP(fi.end_time) - UNIX_TIMESTAMP(fi.start_time))/3600) BETWEEN :durationFrom AND COALESCE(:durationTo,1000)"+
            "AND si.unit_price >= :priceFrom " +
            "AND si.unit_price <=  COALESCE(:priceTo,10000000000)" +
            "ORDER BY CASE WHEN :sortBy = 'duration' THEN (fi.end_time - fi.start_time) END, " +
            "CASE WHEN :sortBy = 'unitPrice' THEN si.unit_price END ,"+
            "CASE WHEN :sortBy = 'start_time'  AND :order ='desc' THEN fi.start_time END DESC,"+
            "CASE WHEN :sortBy = 'end_time'  AND :order = 'desc' THEN fi.end_time END DESC",
            nativeQuery = true)
    Slice<FlightInformation> search(@Param("fromAirportLocationId") Integer fromAirportLocationId,
                                    @Param("toAirportLocationId") Integer toAirportLocationId,
                                    @Param("startTime") LocalDateTime startTime,
                                    @Param("airPlantBrandId") Integer airPlantBrandId,
                                    @Param("seatTypeId") Integer seatTypeId,
                                    @Param("seatQuantity") Integer seatQuantity,
                                    @Param("sortBy") @Nullable String sortBy,
                                    @Param("order")@Nullable String order,
                                    @Param("durationFrom") Integer durationFrom,
                                    @Param("durationTo") Integer durationTo,
                                    @Param("priceFrom") Integer priceFrom,
                                    @Param("priceTo") Integer priceTo,
                                    Pageable pageable);

    default List<FlightInformation> searchForList(Integer fromAirportLocationId, Integer toAirportLocationId, LocalDateTime startTime, Integer airPlantBrandId, Integer seatTypeId, Integer seatQuantity
    ) {
        return search(fromAirportLocationId,toAirportLocationId,startTime,airPlantBrandId,seatTypeId,seatQuantity,null,null,0,null,0,null,Pageable.unpaged()).getContent();
    }
}
