package com.gmail.at.kotamadeo.exceptions;

import org.springframework.dao.DataIntegrityViolationException;

public class UserNotFoundException extends DataIntegrityViolationException {
    public UserNotFoundException(String msg) {
        super(msg);
    }
}
