package com.marketplace.service;

import com.marketplace.entity.Status;
import com.marketplace.exeption.StatusNotFoundException;
import com.marketplace.repository.StatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatusService {
    private final StatusRepository statusRepository;

    public Status returnStatus(String status) {

        Status orderWithStatus = statusRepository.findByName(Status.Name.ORDER_IS_BEING_PROCESSED);
        switch (status) {
            case "order_is_being_processed":
                orderWithStatus = statusRepository.findByName(Status.Name.ORDER_IS_BEING_PROCESSED);
                break;
            case "the_item_has_been_sent_to_the_client":
                orderWithStatus = statusRepository.findByName(Status.Name.THE_ITEM_HAS_BEEN_SENT_TO_THE_CLIENT);
                break;
            case "items_delivered":
                orderWithStatus = statusRepository.findByName(Status.Name.ITEMS_DELIVERED);
                break;
        }
        if (!status.equals(orderWithStatus.getName().name())) {
            throw new StatusNotFoundException("Entered status not found");
        }

        return orderWithStatus;
    }
}
