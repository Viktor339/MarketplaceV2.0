package com.marketplace.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Item",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "name")})
@NoArgsConstructor
@Getter
@Setter
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "tags")
    private String tags;
    @Column(name = "quantity")
    private Long availableQuantity;

    public Item(Long id, String name, String description, String tags, Long availableQuantity) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.tags = tags;
        this.availableQuantity=availableQuantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;
        Item item = (Item) o;
        return id.equals(item.id) && getName().equals(item.getName()) && getDescription().equals(item.getDescription()) && getTags().equals(item.getTags());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, getName(), getDescription(), getTags());
    }

}
