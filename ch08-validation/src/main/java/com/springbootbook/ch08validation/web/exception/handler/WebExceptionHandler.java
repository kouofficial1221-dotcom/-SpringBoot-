package com.springbootbook.ch08validation.web.exception.handler;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.springbootbook.ch08validation.web.exception.IdolNotFoundException;

@ControllerAdvice
public class WebExceptionHandler {

		@ExceptionHandler
		public String handleIdolNotFound(IdolNotFoundException e, Model model) {
			Integer idolId = e.getIdolId();
			model.addAttribute("message", "ID=" + idolId + "のアイドルは見つかりませんでした。");
			return "error";
		
	}
}
