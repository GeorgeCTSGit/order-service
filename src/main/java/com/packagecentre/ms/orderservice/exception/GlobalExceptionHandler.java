package com.packagecentre.ms.orderservice.exception;

import java.time.LocalDateTime;
import java.util.logging.Logger;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{
	
	static Logger logger = Logger.getLogger(GlobalExceptionHandler.class.getName());
	
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest req){
		
		logger.info("********handleAllExceptions**********");
		
		PkgCtrErrorMsg err = new PkgCtrErrorMsg(ex.getMessage(), req.getDescription(false), LocalDateTime.now());
		
		return new ResponseEntity<Object>(err, HttpStatus.INTERNAL_SERVER_ERROR);
		
	}

}
