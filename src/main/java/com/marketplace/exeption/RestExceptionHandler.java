package com.marketplace.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;
import java.util.Map;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(EmailOrUsernameAlreadyExistsException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String,Object> handleEmailOrUsernameAlreadyExistsException(EmailOrUsernameAlreadyExistsException ex){

        ErrorMessage errorMessage = new ErrorMessage(LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                HttpStatus.CONFLICT.name(),
                ex.getMessage()
                );
        return  errorMessage.getMessage();
    }

    @ExceptionHandler(ProductAlreadyExistException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String,Object> handleProductAlreadyExistException(ProductAlreadyExistException ex){

        ErrorMessage errorMessage = new ErrorMessage(LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                HttpStatus.CONFLICT.name(),
                ex.getMessage()
        );
        return  errorMessage.getMessage();
    }

    @ExceptionHandler(NotEnoughItemQuantityException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String,Object> handleNotEnoughItemQuantityException(NotEnoughItemQuantityException ex){

        ErrorMessage errorMessage = new ErrorMessage(LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                HttpStatus.CONFLICT.name(),
                ex.getMessage()
        );
        return  errorMessage.getMessage();
    }

    @ExceptionHandler(ItemNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String,Object> handleItemNotFoundException(ItemNotFoundException ex){

        ErrorMessage errorMessage = new ErrorMessage(LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                HttpStatus.CONFLICT.name(),
                ex.getMessage()
        );
        return  errorMessage.getMessage();
    }

    @ExceptionHandler(BasketEmptyException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String,Object> handleBasketEmptyException(BasketEmptyException ex){

        ErrorMessage errorMessage = new ErrorMessage(LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                HttpStatus.CONFLICT.name(),
                ex.getMessage()
        );
        return  errorMessage.getMessage();
    }

}
