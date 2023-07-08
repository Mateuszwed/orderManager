package mateuszwed.orderManager.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class OrderManagerHandler {
    @ExceptionHandler(value = JsonProcessingFailureException.class)
    public ResponseEntity<String> handleJsonProcessingFailureException(JsonProcessingFailureException e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.badRequest().body("Object Mapper cannot convert value as string");
    }

    @ExceptionHandler(value = HttpClientException.class)
    public ResponseEntity<String> handleHttpClientException(HttpClientException e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.badRequest().body("Object Mapper cannot convert value as string");
    }

    @ExceptionHandler(value = HttpServerException.class)
    public ResponseEntity<String> handleHttpServerException(HttpServerException e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.badRequest().body("Object Mapper cannot convert value as string");
    }
}
