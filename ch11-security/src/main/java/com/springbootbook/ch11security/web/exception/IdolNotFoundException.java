package com.springbootbook.ch11security.web.exception;

public class IdolNotFoundException extends RuntimeException {
    private final Integer idolId;

    public IdolNotFoundException(Integer idolId) {
        super();
        this.idolId = idolId;
    }

    public Integer getIdolId() {
        return idolId;
    }
}
