package mateuszwed.orderManager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidCourierException extends RuntimeException{
    public InvalidCourierException(String message) {
        super(message);
    }
}
