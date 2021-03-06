package com.marketplace.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "orders")
@NoArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
@Builder(toBuilder = true)
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinTable(name = "order_orderStatus",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "orderStatus_id"))
    private OrderStatus orderStatus;
    @Column(name = "userId")
    private Long userId;
    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @Column(name = "item")
    private List<UserItem> userItem;

}
