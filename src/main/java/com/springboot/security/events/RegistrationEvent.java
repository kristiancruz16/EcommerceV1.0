package com.springboot.security.events;

import com.springboot.security.models.User;
import lombok.Getter;

import java.util.Locale;

/**
 * @author KMCruz
 * 7/2/2021
 */
@Getter
public class RegistrationEvent {
    private User user;
    private String appUrl;
    private Locale locale;

    public RegistrationEvent(User user, String appUrl, Locale locale) {
        this.user = user;
        this.appUrl = appUrl;
        this.locale = locale;
    }
}
