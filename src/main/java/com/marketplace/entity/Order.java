package com.marketplace.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "orders")
@NoArgsConstructor
@Setter
@Getter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "status")
    private Status status;
    @Column(name = "userId")
    private Long userId;
    @OneToMany
    @Column(name = "item")
    private List<UserItem> item;

    public enum Status {
        order_is_being_processed,
        the_item_has_been_sent_to_the_client,
        items_delivered,
        status_not_selected
    }

    public Order(Status status) {
        this.status = status;
    }
}
