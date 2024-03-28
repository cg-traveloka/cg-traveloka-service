package com.cgtravelokaservice.repo;

import com.cgtravelokaservice.entity.booking.Combo;
import com.cgtravelokaservice.entity.booking.RoomContract;
import com.cgtravelokaservice.entity.booking.TicketAirPlant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ComboRepo extends JpaRepository<Combo, Integer> {
//    List<Combo> findAllByRoomContractId(Integer id);
//
//    List<Combo> findAllByTicketAirPlantId(Integer id);

    @Query("SELECT c FROM Combo c WHERE c.roomContract.customer.id = ?1 AND c.status = ?2")
    List<Combo> findAllByCustomerIdAndStatus(Integer customerId, String status);
}
