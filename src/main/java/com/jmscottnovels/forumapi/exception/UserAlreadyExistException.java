package com.jmscottnovels.forumapi.exception;

import java.io.Serial;

public final class UserAlreadyExistException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = -8346039195717420732L;

    public UserAlreadyExistException(String s) {
        super(s);
    }
}
