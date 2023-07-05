package back.server.handler;

import back.server.api.dto.ResponseDto;
import back.server.exception.ExistenceException;
import back.server.exception.MethodArgumentNotValidException;
import back.server.exception.VerifyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BadResponseHandler {
    @ExceptionHandler(ExistenceException.class)
    public ResponseDto ExistenceException(ExistenceException ex) {
        return new ResponseDto(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseDto MethodArgumentNotValidException(MethodArgumentNotValidException mx) {
        return new ResponseDto(mx.getMessage());
    }

    @ExceptionHandler(VerifyException.class)
    public ResponseDto VerifyException(VerifyException vx) { return new ResponseDto(vx.getMessage()); }
}
