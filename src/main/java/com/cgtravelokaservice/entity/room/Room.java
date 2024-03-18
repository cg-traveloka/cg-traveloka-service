package com.cgtravelokaservice.entity.room;

import com.cgtravelokaservice.entity.hotel.Hotel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "room_type_id", referencedColumnName = "id")
    private RoomType roomType;
    private Integer quantity;
    private Integer unitPriceOrigin;
    private Integer maxPerson;
    private Integer unitPriceSell;
    private Double size;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id", referencedColumnName = "id")
    private Hotel hotel;
    @ManyToOne
    @JoinColumn(name = "bed_type_id", referencedColumnName = "id")
    private BedType bedType;
}
