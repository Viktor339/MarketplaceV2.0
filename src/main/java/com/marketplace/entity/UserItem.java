package com.marketplace.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "UserItem")
@Setter
@Getter
@NoArgsConstructor
public class UserItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "quantity")
    private Long quantity;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinTable(name = "UserItem_item",
            joinColumns = @JoinColumn(name = "UserItem_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id"))
    private Item item;

}
