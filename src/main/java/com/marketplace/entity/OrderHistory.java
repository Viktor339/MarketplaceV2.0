package com.marketplace.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "orderHistory")
@Getter
@Setter
@NoArgsConstructor
@Builder(toBuilder = true)
@AllArgsConstructor
public class OrderHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "order_id")
    private Long orderId;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "date")
    private LocalDateTime date;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinTable(name = "orderHistory_historyStatus",
            joinColumns = @JoinColumn(name = "orderHistory_id"),
            inverseJoinColumns = @JoinColumn(name = "historyStatus_id"))
    private OrderHistoryStatus orderHistoryStatus;


}
