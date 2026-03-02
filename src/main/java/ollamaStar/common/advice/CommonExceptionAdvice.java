package ollamaStar.common.advice;

import ollamaStar.common.exception.CustonException;
import ollamaStar.pojo.dto.common.Result;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CommonExceptionAdvice {
    @ExceptionHandler({CustonException.class})
    public ResponseEntity commonException(CustonException e) {
        return
                ResponseEntity.status(HttpStatus.OK.value()).body(Result.error(e.getLocalizedMessage()));
    }

}
