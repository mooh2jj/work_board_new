package com.myspring.dsgproj.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

// 버전 4로 맞춰야 @ControllerAdvice, NoHandlerFoundException
@ControllerAdvice
public class CommonExceptionAdvice {

	private static final Logger logger = LoggerFactory.getLogger(CommonExceptionAdvice.class);
	
	@ExceptionHandler(Exception.class)	// 모든 Exception에 대해서 호출됨.
	public String except(Exception ex, Model model) {
		logger.info("Exception....."+ex.getMessage());
		model.addAttribute("exception", ex);
		logger.info(model.toString());
		return "error/error";
	}
	
//	@ExceptionHandler(ArithmeticException.class)	// ArithmeticException에 대해서 호출됨.
//	public String arithmeticException(Exception ex, Model model) {
//		logger.info("Exception....."+ex.getMessage());
//		model.addAttribute("exception", ex);
//		logger.info(model.toString());
//		return "error/error";
//	}
//	
//	@ExceptionHandler(NullPointerException.class)	// NullPointerException에 대해서 호출됨.
//	public String nullPointerException(Exception ex, Model model) {
//		logger.info("Exception....."+ex.getMessage());
//		model.addAttribute("exception", ex);
//		logger.info(model.toString());
//		return "error/error";
//	}
	

//	404 에러 페이지, 버전 4로 맞춰야
	@ExceptionHandler(NoHandlerFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String handle404(NoHandlerFoundException ex) {

		return "error/custom404";
	}
}
