package emerson_care.emerson_care.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<String> handleMaxSizeException(MaxUploadSizeExceededException e) {
        return new ResponseEntity<>("File size exceeds the maximum limit of 2MB", HttpStatus.BAD_REQUEST);
    }

//    @ExceptionHandler(NoHandlerFoundException.class)
//    public ResponseEntity<String> handleNotFoundError(NoHandlerFoundException ex) {
//        return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                .body("Custom 404 error: The requested resource was not found.");
//    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralError(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("An unexpected error occurred: " + ex.getMessage());
    }
}
