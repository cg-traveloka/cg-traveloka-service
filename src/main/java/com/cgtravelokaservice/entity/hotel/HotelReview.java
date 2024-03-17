package com.cgtravelokaservice.entity.hotel;

import com.cgtravelokaservice.entity.booking.RoomContract;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id", referencedColumnName = "id")
    private Hotel hotel;
    @ManyToOne
    @JoinColumn(name = "contract_id", referencedColumnName = "id")
    private RoomContract roomContract;
    private Double ratingPoint;
    private String comment;
}
