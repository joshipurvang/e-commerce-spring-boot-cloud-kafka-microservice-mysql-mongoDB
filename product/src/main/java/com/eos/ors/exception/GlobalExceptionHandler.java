package com.eos.ors.exception;

import java.time.LocalDateTime;

import com.eos.ors.util.AppConstant;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler  {

    @ExceptionHandler(ProductNotFoundException.class)
	public ResponseEntity<Object> handleProductNotFoundException(ProductNotFoundException ex, WebRequest request) {
		log.error(ex.getMessage());
		ExceptionResponse response = ExceptionResponse.builder().message(ex.getMessage()).timestamp(LocalDateTime.now())
				.status(HttpStatus.NOT_FOUND.toString()).details(request.getDescription(false)).build();
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	}

    @ExceptionHandler(InvalidProductException.class)
    public ResponseEntity<Object> handleInvalidProductException(InvalidProductException ex,WebRequest request) {
    	log.error(ex.getMessage());
    	ExceptionResponse response = ExceptionResponse.builder().message(ex.getMessage()).timestamp(LocalDateTime.now())
				.status(HttpStatus.BAD_REQUEST.toString()).details(request.getDescription(false)).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception ex,WebRequest request) {
    	log.error(AppConstant.EXCEPTION_ERROR_MESSAGE);
    	ExceptionResponse response = ExceptionResponse.builder().message(AppConstant.EXCEPTION_ERROR_MESSAGE).timestamp(LocalDateTime.now())
				.status(HttpStatus.INTERNAL_SERVER_ERROR.toString()).details(request.getDescription(false)).build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}

