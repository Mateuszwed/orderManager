package mateuszwed.orderManager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ShippingMethodNoExistsException extends IllegalArgumentException {
    public ShippingMethodNoExistsException(String message){
        super(message);
    }
}
