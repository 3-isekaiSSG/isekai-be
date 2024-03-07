package com.isekai.ssgserver.exception.common;

import com.isekai.ssgserver.exception.dto.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

import static com.isekai.ssgserver.exception.constants.ErrorCode.INTERNAL_SERVER_ERROR;

@RestControllerAdvice
public class CommonExceptionHandler {

    @ExceptionHandler({CustomException.class})
    protected ResponseEntity<?> handleCustomException(CustomException ex) {
        return new ResponseEntity<>(new ErrorDto(ex.getErrorCode().getStatus(), ex.getErrorCode().getMessage()), HttpStatus.valueOf(ex.getErrorCode().getStatus()));
    }

    @ExceptionHandler({ Exception.class })
    protected ResponseEntity<?> handleServerException(Exception ex) {
        ex.printStackTrace();  // 로깅?
        return new ResponseEntity<>(new ErrorDto(INTERNAL_SERVER_ERROR.getStatus(), INTERNAL_SERVER_ERROR.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    protected ResponseEntity<?> handleMethodException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) ->{

            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });
        return new ResponseEntity<Object>(errors, HttpStatus.BAD_REQUEST);
    }
}
