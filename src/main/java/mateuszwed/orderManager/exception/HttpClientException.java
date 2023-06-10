package mateuszwed.orderManager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class HttpClientException extends HttpClientErrorException {
    public HttpClientException(HttpStatus httpStatus, String message){
        super(httpStatus, message);
    }
}
