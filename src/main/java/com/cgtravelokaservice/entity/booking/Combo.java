package com.cgtravelokaservice.entity.booking;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Combo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToOne
    @JoinColumn(name = "room_contract_id", referencedColumnName = "id")
    private RoomContract roomContract;
    @OneToOne
    @JoinColumn(name = "ticket_air_plant_id", referencedColumnName = "id")
    private TicketAirPlant ticketAirPlant;
    private String status;
    private Integer totalMoney;
}
