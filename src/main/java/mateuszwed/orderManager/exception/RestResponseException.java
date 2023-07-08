package mateuszwed.orderManager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.RestClientException;
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class RestResponseException extends RestClientException {
    public RestResponseException(String message){
        super(message);
    }
}
