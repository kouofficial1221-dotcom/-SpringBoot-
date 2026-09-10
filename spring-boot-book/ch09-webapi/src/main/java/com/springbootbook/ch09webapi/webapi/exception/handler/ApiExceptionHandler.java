package com.springbootbook.ch09webapi.webapi.exception.handler;

import com.springbootbook.ch09webapi.webapi.exception.IdolNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;

public class ApiExceptionHandler {

}
