package com.marketplace.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "status")
@NoArgsConstructor
@Setter
@Getter
public class Status {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(name = "name")
    private Name name;

    public Status(Name name) {
        this.name = name;
    }

    public enum Name {
        ORDER_IS_BEING_PROCESSED,
        THE_ITEM_HAS_BEEN_SENT_TO_THE_CLIENT,
        ITEMS_DELIVERED,
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Status)) return false;
        Status status = (Status) o;
        return getName() == status.getName();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }

}
