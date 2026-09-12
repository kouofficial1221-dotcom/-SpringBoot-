package com.springbootbook.ch10webapi02.webapi.exception;

public class IdolNotFoundException extends RuntimeException {
    private final Integer idolId;

    public IdolNotFoundException(Integer idolId) {
        this.idolId = idolId;
    }

    public Integer getIdolId() {
        return idolId;
    }
}
