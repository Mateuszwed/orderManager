package mateuszwed.orderManager.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class JsonProcessingFailureException extends JsonProcessingException {
    public JsonProcessingFailureException(String message){
        super(message);
    }
}
