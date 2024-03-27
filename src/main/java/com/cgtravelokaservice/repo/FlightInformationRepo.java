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
    Slice<FlightInformation> findAllByOrderByStartTimeAsc(Pageable pageable);

    @Query("SELECT fi " + "FROM FlightInformation fi " + "INNER JOIN fi.seatInformations si " + "WHERE " +
            "(:airPlantBrandId IS NULL OR" + " fi.airPlantBrand.id in " + ":airPlantBrandId) " + "AND fi" +
            ".fromAirPortLocation.id = :fromAirportLocationId " + "AND fi.toAirPortLocation.id = :toAirportLocationId" +
            " " + "AND  fi.startTime >=  :startTime " + "AND si.quantity >= :seatQuantity " + "AND si.seatType.id = " +
            ":seatTypeId " + "AND si.unitPrice >= :priceFrom " + "AND si.unitPrice <= COALESCE(:priceTo, 10000000000)" +
            " " + "ORDER BY " + "CASE WHEN :sortBy = 'unitPrice' THEN si.unitPrice END, " + "CASE WHEN :sortBy = " +
            "'startTime' AND :order = 'desc' THEN fi.startTime END DESC, " + "CASE WHEN :sortBy = 'endTime' AND " +
            ":order = 'desc' THEN fi.endTime END DESC, " + "CASE WHEN :sortBy = 'startTime' THEN fi.startTime END, " + "CASE WHEN :sortBy = 'endTime' THEN fi.endTime END ")
    Slice<FlightInformation> search(@Param("fromAirportLocationId") Integer fromAirportLocationId, @Param(
            "toAirportLocationId") Integer toAirportLocationId, @Param("startTime") LocalDateTime startTime, @Param(
            "airPlantBrandId") List<Integer> airPlantBrandId, @Param("seatTypeId") Integer seatTypeId,
                                    @Param("seatQuantity") Integer seatQuantity,
                                    @Param("sortBy") @Nullable String sortBy,
                                    @Param("order") @Nullable String order,
                                    @Param("priceFrom") Integer priceFrom,
                                    @Param("priceTo") @Nullable Integer priceTo, Pageable pageable);


    default List<FlightInformation> searchForList(Integer fromAirportLocationId, Integer toAirportLocationId,
                                                  LocalDateTime startTime, List<Integer> airPlantBrandId,
                                                  Integer seatTypeId, Integer seatQuantity) {
        return search(fromAirportLocationId, toAirportLocationId, startTime, airPlantBrandId, seatTypeId,
                seatQuantity, null, null, 0, null, Pageable.unpaged()).getContent();

    }


}
