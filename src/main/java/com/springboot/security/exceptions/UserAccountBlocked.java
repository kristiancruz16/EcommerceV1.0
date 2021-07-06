package com.springboot.security.exceptions;

/**
 * @author KMCruz
 * 7/6/2021
 */
public class UserAccountBlocked extends RuntimeException{
    public UserAccountBlocked() {
        super();
    }

    public UserAccountBlocked(String message) {
        super(message);
    }

    public UserAccountBlocked(String message, Throwable cause) {
        super(message, cause);
    }

    public UserAccountBlocked(Throwable cause) {
        super(cause);
    }
}
