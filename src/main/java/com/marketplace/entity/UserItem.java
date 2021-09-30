package com.marketplace.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "UserItem")
@Setter
@Getter
@NoArgsConstructor
@Builder(toBuilder = true)
@AllArgsConstructor
public class UserItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "quantity")
    private Long quantity;
    @Column(name = "item_id")
    private Long itemId;
    @Column(name = "user_id")
    private Long userId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserItem)) return false;
        UserItem userItem = (UserItem) o;
        return getItemId().equals(userItem.getItemId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getItemId());
    }

}
