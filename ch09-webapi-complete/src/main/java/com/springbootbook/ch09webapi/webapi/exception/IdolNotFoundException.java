package com.springbootbook.ch09webapi.webapi.exception;

public class IdolNotFoundException extends RuntimeException {
    private final Integer idolId;

    public IdolNotFoundException(Integer idolId) {
        this.idolId = idolId;
    }

    public Integer getIdolId() {
        return idolId;
    }
}
