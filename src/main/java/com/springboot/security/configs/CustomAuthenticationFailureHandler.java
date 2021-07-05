package com.springboot.security.configs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

/**
 * @author KMCruz
 * 7/5/2021
 */
@Component
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private final MessageSource messageSource;

    public CustomAuthenticationFailureHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        LOGGER.info(exception.getMessage());
        Locale locale = request.getLocale();
        String error = messageSource.getMessage("error.badCredentials",null,locale);
        if(exception.getMessage().equalsIgnoreCase("User is disabled")){
            error = messageSource.getMessage("error.userDisabled",null,locale);
        }

        setDefaultFailureUrl("/login?error="+error);
        super.onAuthenticationFailure(request, response, exception);


    }
}
