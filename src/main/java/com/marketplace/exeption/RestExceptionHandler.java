package com.marketplace.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.time.LocalDateTime;
import java.util.Map;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(EmailOrUsernameAlreadyExistsException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, Object> handleEmailOrUsernameAlreadyExistsException(EmailOrUsernameAlreadyExistsException ex) {

        ErrorMessage errorMessage = new ErrorMessage(LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                HttpStatus.CONFLICT.name(),
                ex.getMessage()
        );
        return errorMessage.getMessage();
    }

    @ExceptionHandler(ProductAlreadyExistException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, Object> handleProductAlreadyExistException(ProductAlreadyExistException ex) {

        ErrorMessage errorMessage = new ErrorMessage(LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                HttpStatus.CONFLICT.name(),
                ex.getMessage()
        );
        return errorMessage.getMessage();
    }

    @ExceptionHandler(NotEnoughItemQuantityException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, Object> handleNotEnoughItemQuantityException(NotEnoughItemQuantityException ex) {

        ErrorMessage errorMessage = new ErrorMessage(LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                HttpStatus.CONFLICT.name(),
                ex.getMessage()
        );
        return errorMessage.getMessage();
    }

    @ExceptionHandler(ItemNotExistException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, Object> handleItemNotFoundException(ItemNotExistException ex) {

        ErrorMessage errorMessage = new ErrorMessage(LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                HttpStatus.CONFLICT.name(),
                ex.getMessage()
        );
        return errorMessage.getMessage();
    }

    @ExceptionHandler(BasketEmptyException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, Object> handleBasketEmptyException(BasketEmptyException ex) {

        ErrorMessage errorMessage = new ErrorMessage(LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                HttpStatus.CONFLICT.name(),
                ex.getMessage()
        );
        return errorMessage.getMessage();
    }

    @ExceptionHandler(UserOrderAlreadyExistException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, Object> handleUsersOrderAlreadyExistException(UserOrderAlreadyExistException ex) {

        ErrorMessage errorMessage = new ErrorMessage(LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                HttpStatus.CONFLICT.name(),
                ex.getMessage()
        );
        return errorMessage.getMessage();
    }

    @ExceptionHandler(ItemNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, Object> handleItemNotFoundException(ItemNotFoundException ex) {

        ErrorMessage errorMessage = new ErrorMessage(LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                HttpStatus.CONFLICT.name(),
                ex.getMessage()
        );
        return errorMessage.getMessage();
    }

    @ExceptionHandler(UserOrderNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, Object> handleUserOrderNotFoundException(UserOrderNotFoundException ex) {

        ErrorMessage errorMessage = new ErrorMessage(LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                HttpStatus.CONFLICT.name(),
                ex.getMessage()
        );
        return errorMessage.getMessage();
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, Object> handleNoHandlerFoundException(NoHandlerFoundException ex) {

        ErrorMessage errorMessage = new ErrorMessage(LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.name(),
                ex.getMessage()
        );
        return errorMessage.getMessage();
    }

    @ExceptionHandler(UserOrderNotExistException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, Object> handleOrderNotExistException(UserOrderNotExistException ex) {

        ErrorMessage errorMessage = new ErrorMessage(LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                HttpStatus.CONFLICT.name(),
                ex.getMessage()
        );
        return errorMessage.getMessage();
    }

    @ExceptionHandler(StatusNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, Object> handleStatusNotFoundException(StatusNotFoundException ex) {

        ErrorMessage errorMessage = new ErrorMessage(LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                HttpStatus.CONFLICT.name(),
                ex.getMessage()
        );
        return errorMessage.getMessage();
    }

    @ExceptionHandler(ItemAlreadyInTheUserBasketException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, Object> handleItemAlreadyInTheUserBasket(ItemAlreadyInTheUserBasketException ex) {

        ErrorMessage errorMessage = new ErrorMessage(LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                HttpStatus.CONFLICT.name(),
                ex.getMessage()
        );
        return errorMessage.getMessage();
    }
}
