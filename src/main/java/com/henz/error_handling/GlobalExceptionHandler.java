package com.henz.error_handling;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/*
 * there is also exception handling implemented in this app by using javax.validations and @Valid annotations in the StockController
 * when making invalid input when posting new stock, like companyName > 10 chars, application throws MethodArgumentNotValidException
 * --> and spring catches the exception and returns 400 Bad Request
 * 
 * 
 * */

@ControllerAdvice //handle exceptions globally
public class GlobalExceptionHandler {
	
	// handle exception e.g. when a stock is searched with GET which does not exist
	// in the restcontroller with get, we will throw new IllegalStateException if the stock does not exist
	// IllegalStateException extends RuntimeException
	
	// as a response we return 400 bad request -> test it with postman
	@ResponseBody
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<StockError> handleException(RuntimeException exception){
		return ResponseEntity.badRequest().body(new StockError("00102", exception.getMessage()));
	}
}
