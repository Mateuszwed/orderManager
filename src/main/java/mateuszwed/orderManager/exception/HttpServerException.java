package mateuszwed.orderManager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpServerErrorException;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class HttpServerException extends HttpServerErrorException {
    public HttpServerException(HttpStatus httpStatus, String message){
        super(httpStatus, message);
    }
}
