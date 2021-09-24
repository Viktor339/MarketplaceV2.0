package com.marketplace.repository;

import com.marketplace.entity.Basket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface BasketRepository extends JpaRepository<Basket,Long> {

    @Modifying
    @Query(value = "insert into Basket (item_id, user_id) VALUES (?1, ?2)", nativeQuery = true)
    @Transactional
    void insertItem(@Param("item") Long item,
                    @Param("userId") Long userId);

    Boolean existsByUserId(Long userId);

    Basket getBasketByUserId(Long id);

    List<Basket> findItemByUserId(Long id);

    void deleteAllByUserId(Long id);


}
