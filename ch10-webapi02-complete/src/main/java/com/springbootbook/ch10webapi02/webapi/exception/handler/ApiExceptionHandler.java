package com.springbootbook.ch10webapi02.webapi.exception.handler;

import com.springbootbook.ch10webapi02.webapi.exception.IdolNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;
import java.util.List;

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

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ProblemDetail problemDetail = ProblemDetail.forStatus(status);
        problemDetail.setType(URI.create("about:blank"));
        problemDetail.setTitle("Validation Error");
        problemDetail.setDetail("不正な入力値です");
        List<ErrorPair> invalidValues = ex.getFieldErrors()
                .stream()
                .map(error -> new ErrorPair(error.getField(), error.getDefaultMessage()))
                .toList();
        problemDetail.setProperty("invalidValues", invalidValues);
        return ResponseEntity.status(status).body(problemDetail);
    }

    private static record ErrorPair(String field, String message) {
    }
}
