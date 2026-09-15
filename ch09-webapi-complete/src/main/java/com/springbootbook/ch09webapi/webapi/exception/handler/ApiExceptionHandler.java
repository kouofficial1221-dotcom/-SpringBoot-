package com.springbootbook.ch09webapi.webapi.exception.handler;

import com.springbootbook.ch09webapi.webapi.exception.IdolNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;

@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<ProblemDetail> handleIdolNotFound(IdolNotFoundException ex) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ProblemDetail problemDetail = ProblemDetail.forStatus(status);
        problemDetail.setType(URI.create("about:blank"));
        problemDetail.setTitle("Not Found");
        problemDetail.setDetail("該当するアイドルが存在しません");
        problemDetail.setProperty("idolId", ex.getIdolId());
        return ResponseEntity.status(status).body(problemDetail);
    }
}
