package com.myspring.dsgproj.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Controller
public class CommonExceptionAdvice {

	private static final Logger logger = LoggerFactory.getLogger(CommonExceptionAdvice.class);
	
	
	@ExceptionHandler(ArithmeticException.class)	// ArithmeticException에 대해서 호출됨.
	public String arithmeticException(Exception ex, Model model) {
		logger.info("Exception....."+ex.getMessage());
		model.addAttribute("exception", ex);
		logger.info(model.toString());
		return "/error/error";
	}
	
	@ExceptionHandler(NullPointerException.class)	// NullPointerException에 대해서 호출됨.
	public String nullPointerException(Exception ex, Model model) {
		logger.info("Exception....."+ex.getMessage());
		model.addAttribute("exception", ex);
		logger.info(model.toString());
		return "/error/error";
	}
	
	@ExceptionHandler(Exception.class)	// 모든 Exception에 대해서 호출됨.
	public String except(Exception ex, Model model) {
		logger.info("Exception....."+ex.getMessage());
		model.addAttribute("exception", ex);
		logger.info(model.toString());
		return "error/error";
	}
}
