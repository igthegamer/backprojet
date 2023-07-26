package work.example.demo.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    Logger log = LoggerFactory.getLogger(RestResponseEntityExceptionHandler.class);
    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleAccessDeniedException(
            Exception ex, WebRequest request) {
        log.error(ex.toString());
        return new ResponseEntity<Object>(
                "Problem in server", new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
