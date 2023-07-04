package back.server.handler;

import back.server.api.dto.ResponseDto;
import back.server.exception.ExistenceException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BadResponseHandler {
    @ExceptionHandler
    public ResponseDto ExistenceException(ExistenceException ex) {
        return new ResponseDto(ex.getMessage());
    }
}
