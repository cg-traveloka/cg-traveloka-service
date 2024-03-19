package com.cgtravelokaservice.entity.hotel;

import com.cgtravelokaservice.entity.booking.RoomContract;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "contract_id", referencedColumnName = "id")
    private RoomContract roomContract;
    private Double ratingPoint;
    private String comment;
    private LocalDateTime commentTime;
}
