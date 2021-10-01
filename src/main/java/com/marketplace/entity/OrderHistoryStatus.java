package com.marketplace.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "historyStatus")
@NoArgsConstructor
@Setter
@Getter
public class OrderHistoryStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(name = "name")
    private Name name;

    public OrderHistoryStatus(Name name) {
        this.name = name;
    }

    public enum Name {
        CREATED,
        CHANGED,
        DELETED
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderHistoryStatus)) return false;
        OrderHistoryStatus that = (OrderHistoryStatus) o;
        return getName() == that.getName();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }
}

