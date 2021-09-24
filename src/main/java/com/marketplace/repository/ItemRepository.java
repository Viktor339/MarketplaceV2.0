package com.marketplace.repository;

import com.marketplace.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    Boolean existsByName(String name);

    @Modifying
    @Query(value = "insert into item(name,description,tags,quantity) values (?1,?2,?3,?4)", nativeQuery = true)
    @Transactional
    void createNewItem(@Param("name") String name,
                       @Param("description") String description,
                       @Param("tags") String tags,
                       @Param("quantity") Long quantity);


    Item findItemById(Long id);

    List<Item> findAll();


}
