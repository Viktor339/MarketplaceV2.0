package com.marketplace.repository;

import com.marketplace.entity.UserItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserItemRepository extends JpaRepository<UserItem, Long> {
    UserItem findAllById(Long id);

    UserItem findUserItemByItemIdAndUserId(Long itemId,Long userId);

    void deleteAllByItemIdAndUserId(Long itemId,Long userId);
}
