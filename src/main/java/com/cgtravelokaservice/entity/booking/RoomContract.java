package com.cgtravelokaservice.entity.booking;

import com.cgtravelokaservice.entity.room.Room;
import com.cgtravelokaservice.entity.user.Customer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RoomContract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;
    @ManyToOne
    @JoinColumn(name = "room_id", referencedColumnName = "id")
    private Room room;
    private Integer roomQuantity;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer totalMoney;
    private String status;
    private boolean enableReview;
}
