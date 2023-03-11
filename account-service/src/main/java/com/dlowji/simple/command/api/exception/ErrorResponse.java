package com.dlowji.simple.command.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ErrorResponse {
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, Object>> handleValidationException(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		BindingResult bindingResult = ex.getBindingResult();
		bindingResult.getFieldErrors().forEach(error -> {
			String field = error.getField();
			String message = error.getDefaultMessage();
			errors.put(field, message);
		});
		Map<String, Object> responseBody = new HashMap<>();
		responseBody.put("status", HttpStatus.BAD_REQUEST.value());
		responseBody.put("message", "Validation error");
		responseBody.put("errors", errors);
		responseBody.put("timestamp", LocalDateTime.now());
		return ResponseEntity.badRequest().body(responseBody);
	}
}