package com.springbootbook.ch03java;

/**
 * SampleServiceで異常が発生したことを表す例外です。
 */
public class SampleException extends RuntimeException {
    public SampleException(Throwable cause) {
        super(cause);
    }
}
