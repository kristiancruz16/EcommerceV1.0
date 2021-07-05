package com.springboot.security.events;

import com.springboot.security.models.User;
import lombok.Getter;

import java.util.Locale;

/**
 * @author KMCruz
 * 7/4/2021
 */
@Getter
public class PasswordResetEvent {
    private User user;
    private String appUrl;
    private Locale locale;

    public PasswordResetEvent(User user, String appUrl, Locale locale) {
        this.user = user;
        this.appUrl = appUrl;
        this.locale = locale;
    }
}
