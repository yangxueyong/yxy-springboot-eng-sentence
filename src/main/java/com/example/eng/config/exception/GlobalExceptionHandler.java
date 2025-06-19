package com.example.eng.config.exception;

import com.example.eng.entity.res.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<R> handleGenericException(Exception ex) {
        log.error(ex.getMessage(), ex);
        R r = null;
        if(ex instanceof CustomException){
            CustomException cx = (CustomException) ex;
            r = R.fail(cx);
        }else{
            r = R.fail(ex.getMessage());
        }
        ResponseEntity<R> body1 = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON)
                .body(r);
        return body1;
    }

    // 参数校验异常
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<R> handleValidationExceptions(Exception ex) {
        log.error(ex.getMessage(), ex);
        R r = null;
        // 从异常中获取字段错误信息
        FieldError fieldError = ((MethodArgumentNotValidException) ex).getBindingResult().getFieldError();
        if (fieldError != null) {
            // 获取错误提示信息
            String errorMessage = fieldError.getDefaultMessage();
            log.error(errorMessage);
            r = R.fail(errorMessage);
        } else {
            // 如果没有字段错误，返回默认错误信息
            r = R.fail(ex.getMessage());
        }
        ResponseEntity<R> body = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(r);
        return body;
    }
}
