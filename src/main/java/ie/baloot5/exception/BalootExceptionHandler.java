package ie.baloot5.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class BalootExceptionHandler {

    @ExceptionHandler(InvalidIdException.class)
    public ResponseEntity<ErrorResponse> handleInvalidIdException(InvalidIdException exception) {
        return new ResponseEntity<ErrorResponse>(new ErrorResponse(404, exception.getMessage(),"Not Found"),
                HttpStatus.NOT_FOUND);
//        return new ResponseEntity(404, exception.getMessage(),"Not Found");
    }

    @ExceptionHandler(InvalidValueException.class)
    public ResponseEntity<ErrorResponse> handleInvalidValueException(InvalidValueException exception) {
        return new ResponseEntity<>(new ErrorResponse(400, exception.getMessage(), "Not Acceptable"),
                HttpStatus.NOT_ACCEPTABLE);
//        return new ErrorResponse(400, exception.getMessage(), "Bad Request");
    }

    @ExceptionHandler(NotEnoughAmountException.class)
    public ResponseEntity<ErrorResponse> handleNotEnoughException(NotEnoughAmountException exception) {
        return new ResponseEntity<>(new ErrorResponse(406, exception.getMessage(), "Not Acceptable")
                , HttpStatus.NOT_ACCEPTABLE);
//        return new ErrorResponse(406, exception.getMessage(), "Not Acceptable");
    }

    @ExceptionHandler(InvalidRequestParamsException.class)
    public ResponseEntity<ErrorResponse> handleInvalidRequestParamsException(InvalidRequestParamsException exception) {
        return new ResponseEntity<>(new ErrorResponse(400, exception.getMessage(), "Bad Request"),
                HttpStatus.BAD_REQUEST);
//        return new ErrorResponse(400, exception.getMessage(), "Bad Request");
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorizedException(UnauthorizedException exception) {
        return new ResponseEntity<>(new ErrorResponse(401, exception.getMessage(), "Unauthorized"),
                HttpStatus.UNAUTHORIZED);
//        return new ErrorResponse(401, exception.getMessage(), "Unauthorized");
    }
}

