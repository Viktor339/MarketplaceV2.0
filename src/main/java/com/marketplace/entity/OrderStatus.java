package com.marketplace.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "orderStatus")
@NoArgsConstructor
@Setter
@Getter
public class OrderStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(name = "name")
    private Name name;

    public OrderStatus(Name name) {
        this.name = name;
    }

    public enum Name {
        IN_PROCESSING,
        SENT,
        DELIVERED,
        DELETED
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderStatus)) return false;
        OrderStatus orderStatus = (OrderStatus) o;
        return getName() == orderStatus.getName();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }

}
